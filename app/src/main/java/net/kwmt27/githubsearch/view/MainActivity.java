package net.kwmt27.githubsearch.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.R;
import net.kwmt27.githubsearch.util.Logger;

public class MainActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean signedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActionBar(false);

        String token = ModelLocator.getLoginModel().getAccessToken();
        if (!TextUtils.isEmpty(token)) {
            // User is signed in
            Logger.d("onAuthStateChanged:signed_in:" +token);
            replaceFragment(RepositoryListFragment.newInstance(), true, R.string.title_repository_list);
        } else {
            // User is signed out
            Logger.d("onAuthStateChanged:signed_out");
            replaceFragment(TopFragment.newInstance(), false, 0);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume");
        if(signedIn) {
            return;
        }
        String token = ModelLocator.getLoginModel().getAccessToken();
        if (!TextUtils.isEmpty(token)) {
            // User is signed in
            Logger.d("onAuthStateChanged:signed_in:" +token);
            replaceFragment(RepositoryListFragment.newInstance(), true, R.string.title_repository_list);
            signedIn = true;
        } else {
            // User is signed out
            Logger.d("onAuthStateChanged:signed_out");
            replaceFragment(TopFragment.newInstance(), false, 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("onStart");
        //mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        Logger.d("onStop");
        //mAuth.removeAuthStateListener(mAuthListener);
        super.onStop();
    }

    private void replaceFragment(Fragment fragment, boolean showToolBar, int titleResId) {
        int visibility = showToolBar ? View.VISIBLE : View.GONE;
        findViewById(R.id.toolbar).setVisibility(visibility);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            if(titleResId != 0) {
                actionBar.setTitle(titleResId);
            }
        }


        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


}
