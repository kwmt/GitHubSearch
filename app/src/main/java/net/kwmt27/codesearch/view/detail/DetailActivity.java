package net.kwmt27.codesearch.view.detail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.presenter.detail.DetailPresenter;
import net.kwmt27.codesearch.presenter.detail.IDetailPresenter;
import net.kwmt27.codesearch.util.ToastUtil;
import net.kwmt27.codesearch.view.BaseActivity;
import net.kwmt27.codesearch.view.GitHubSearchWebViewClient;

public class DetailActivity extends BaseActivity implements DetailPresenter.IDetailView {


    private boolean mIsFailure;
    private boolean mHideSearchMenu;

    public static void startActivity(Activity activity, String title, String url,  GithubRepoEntity repo) {
        Intent intent = new Intent(activity.getApplicationContext(), DetailActivity.class);
        intent.putExtra(TITLE_KEY, title);
        intent.putExtra(IDetailPresenter.URL_KEY, url);
        intent.putExtra(IDetailPresenter.REPO_ENTITY_KEY, repo);
        activity.startActivity(intent);
    }

    private IDetailPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPresenter = new DetailPresenter(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        if(mHideSearchMenu) {
            searchItem.setVisible(false);
            mHideSearchMenu = true;
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return goBackIfNeeded() || super.onOptionsItemSelected(item);
            case R.id.action_search:
                mPresenter.onActionSearchSelected();
                return true;
            case R.id.action_close:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return goBackIfNeeded() || super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean goBackIfNeeded() {
        WebView webView = (WebView)findViewById(R.id.webview);
        if(webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }


    @Override
    public void setupComponents() {
        // noop
    }

    @Override
    public void setupComponents(String url, String title) {
        setUpActionBar();

        setTitle(title);
        final WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new GitHubSearchWebViewClient(this, webView){
            @Override
            protected void startActivityOnTouch(String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(url);
    }

    @Override
    public void showError(String message) {
        ToastUtil.show(this, message);
    }

    @Override
    public void hideSearchMenu() {
        mHideSearchMenu = true;
        invalidateOptionsMenu();
    }

}
