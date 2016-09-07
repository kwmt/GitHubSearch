package net.kwmt27.codesearch.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;

import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.view.events.EventListFragment;
import net.kwmt27.codesearch.view.top.TopFragment;

public class MainActivity extends BaseActivity {

    private boolean mSignedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActionBar(false);

        switchScreen();

    }

    private void switchScreen() {
        String token = ModelLocator.getLoginModel().getAccessToken();
        if (!TextUtils.isEmpty(token)) {
            Logger.d("signed_in:" +token);
            replaceFragment(EventListFragment.newInstance(), true, R.string.title_repository_list);
            mSignedIn = true;
        } else {
            Logger.d("signed_out");
            replaceFragment(TopFragment.newInstance(), false, 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume");
        if(mSignedIn) {
            return;
        }
        switchScreen();
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
