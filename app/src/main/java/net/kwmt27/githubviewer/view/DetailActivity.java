package net.kwmt27.githubviewer.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.presenter.DetailPresenter;
import net.kwmt27.githubviewer.presenter.IDetailPresenter;
import net.kwmt27.githubviewer.util.Logger;
import net.kwmt27.githubviewer.view.search.SearchActivity;

public class DetailActivity extends BaseActivity implements DetailPresenter.IDetailView {


    private boolean mIsFailure;

    public static void startActivity(AppCompatActivity activity, String title, GithubRepoEntity repo) {
        Intent intent = new Intent(activity.getApplicationContext(), DetailActivity.class);
        intent.putExtra(TITLE_KEY, title);
        intent.putExtra(IDetailPresenter.REPO_ENTITY_KEY,  repo);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return goBackIfNeeded() || super.onOptionsItemSelected(item);
            case R.id.action_search:
                SearchActivity.startActivity(this, true);
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
    public void setupComponents(GithubRepoEntity entity) {
        setUpActionBar();
        final WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Logger.d("onPageStarted is called. url:" + url);
//                mLastLoadUrl = url;
//                mIsLoading = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.d("onPageFinished is called. mIsFailure:" + mIsFailure + ", url:" + url);

//                mHomeLoading.setVisibility(View.GONE);
//                mIsLoading = false;
                if (mIsFailure) {
                    switchErrorPage(true);
                    return;
                }
                switchErrorPage(false);
            }

            private void switchErrorPage(boolean showError) {
                int errorVisibility = View.GONE;
                int webViewVisibility = View.VISIBLE;
                if(showError) {
                    errorVisibility = View.VISIBLE;
                    webViewVisibility = View.GONE;
                }
                findViewById(R.id.error_layout).setVisibility(errorVisibility);
                webView.setVisibility(webViewVisibility);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Logger.d("shouldOverrideUrlLoading url:"+url);
                url = url.trim();
//                if (url.startsWith("tel:")) {
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
//                    startActivity(intent);
//                    return true;
//                }

                // String#matchesは、完全にマッチするかの判定
                // http://d.hatena.ne.jp/kakkun61/20100104/1262605869
                // http://docs.oracle.com/javase/jp/7/api/java/lang/String.html#matches(java.lang.String)
                String pattern = "https?://(|.+\\.)github\\.com([/?#:].*|)";
                if (url.matches(pattern)) {
                    // アプリ内のWebViewで表示する
                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;

            }


            // http://stackoverflow.com/a/33419123/2520998
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());

            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if(errorCode < 0) {
                    mIsFailure = true;
                }
            }

        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(entity.getHtmlUrl());
    }

}
