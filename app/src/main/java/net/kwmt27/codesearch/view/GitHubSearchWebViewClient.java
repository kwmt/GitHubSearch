package net.kwmt27.codesearch.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.util.Logger;

public class GitHubSearchWebViewClient extends WebViewClient {
    private final Activity mActivity;
    private final WebView mWebView;
    private boolean mIsFailure = false;


    public GitHubSearchWebViewClient(Activity activity, WebView webView) {
        mActivity = activity;
        mWebView = webView;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Logger.d("onPageStarted is called. url:" + url);
        showProgress(true);
    }

    private void showProgress(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        View progressLayout = mActivity.findViewById(R.id.progress_layout);
        if(progressLayout == null) {
            Logger.w("@+id/progress_layout is not found");
            return;
        }
        progressLayout.setVisibility(visibility);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Logger.d("onPageFinished is called. mIsFailure:" + mIsFailure + ", url:" + url);
        showProgress(false);
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
        final View errorLayout = mActivity.findViewById(R.id.error_layout);
        if(errorLayout == null) {
            Logger.w("@+id/error_layout is not found.");
            return;
        }
        errorLayout.setVisibility(errorVisibility);
        Button reloadButton = (Button)errorLayout.findViewById(R.id.reload_button);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsFailure = false;
                errorLayout.setVisibility(View.GONE);
                mWebView.reload();
            }
        });
        mWebView.setVisibility(webViewVisibility);
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Logger.d("shouldOverrideUrlLoading url:"+url);
        url = url.trim();
        // String#matchesは、完全にマッチするかの判定
        // http://d.hatena.ne.jp/kakkun61/20100104/1262605869
        // http://docs.oracle.com/javase/jp/7/api/java/lang/String.html#matches(java.lang.String)
        String pattern = "https?://(|.+\\.)github\\.com([/?#:].*|)";
        if (url.matches(pattern)) {
            // アプリ内のWebViewで表示する
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mActivity.startActivity(intent);

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
}
