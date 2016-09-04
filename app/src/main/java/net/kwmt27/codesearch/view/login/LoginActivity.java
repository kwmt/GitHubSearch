package net.kwmt27.codesearch.view.login;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;

import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.TokenEntity;
import net.kwmt27.codesearch.model.rx.ApiSubscriber;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.util.PrefUtil;
import net.kwmt27.codesearch.view.GitHubSearchWebViewClient;

import rx.Subscription;

public class LoginActivity extends Activity {

    private Subscription mSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebView webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new GitHubSearchWebViewClient(this, webview) {

            boolean authComplete = false;

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
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
                            Logger.d("onNext:" + tokenEntity.getAccessToken());
                            PrefUtil.setAccessToken(getApplicationContext(), tokenEntity.getAccessToken());

                            finish();
                        }
                    });
                }
            }

            @Override
            protected void startActivityOnTouch(String url) {
                // no-op
            }
        });
        webview.loadUrl(ModelLocator.getLoginModel().getAuthorizeUrl());


    }

    @Override
    protected void onStop() {
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }
        super.onStop();
    }
}
