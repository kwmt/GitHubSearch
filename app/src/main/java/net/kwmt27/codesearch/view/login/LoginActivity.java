package net.kwmt27.codesearch.view.login;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;

import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.TokenEntity;
import net.kwmt27.codesearch.model.rx.ApiSubscriber;
import net.kwmt27.codesearch.util.CookieUtil;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.view.GitHubSearchWebViewClient;

import rx.Subscription;

public class LoginActivity extends Activity {

    private Subscription mSubscription;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        setupAuthListener();


        WebView webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new GitHubSearchWebViewClient(this, webview) {

            boolean authComplete = false;

            @Override
            protected void startActivityOnTouch(String url) {
                // no-op
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                CookieUtil.log();

                // TODO move to Presenter
                final String accessCodeFragment = "code=";

                if (url.contains(accessCodeFragment) && !authComplete) {
                    // the GET request contains an authorization code
                    String accessCode = url.substring(url.indexOf(accessCodeFragment) + accessCodeFragment.length());
                    authComplete = true;

                    Logger.d("accessCode=" + accessCode);
                    mSubscription = ModelLocator.getLoginModel().fetchAccessToken(accessCode, new ApiSubscriber<TokenEntity>(getApplicationContext()) {
                        @Override
                        public void onCompleted() {
                            Logger.d("onCompleted");
                        }

                        @Override
                        public void onNext(TokenEntity tokenEntity) {
                            ModelLocator.getLoginModel().setAccessToken(tokenEntity.getAccessToken());
                            signIn(tokenEntity.getAccessToken());
                            ModelLocator.getLoginModel().setGithubLoginId(CookieUtil.getCookieValue("dotcom_user"));
                            finish();
                        }
                    });
                }
                return super.shouldOverrideUrlLoading(view, url);

            }
        });
        webview.loadUrl(ModelLocator.getLoginModel().getAuthorizeUrl());


    }

    /**
     * https://firebase.google.com/docs/auth/android/github-auth#authenticate_with_firebase
     */
    private void signIn(String token) {
        AuthCredential credential = GithubAuthProvider.getCredential(token);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    Logger.d("signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Logger.w("signInWithCredential", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        addAuthListener();
    }



    @Override
    protected void onStop() {
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }
        removeAuthListener();

        super.onStop();
    }

    // FIXME 
    private void setupAuthListener() {
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Logger.d("onAuthStateChanged:signed_in:" + user.getUid());
                Logger.d("displayName:" +user.getDisplayName());
            } else {
                // User is signed out
                Logger.d("onAuthStateChanged:signed_out");
            }
            // ...
        };
    }

    private void addAuthListener() {
        mAuth.addAuthStateListener(mAuthListener);
    }
    private void removeAuthListener() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
