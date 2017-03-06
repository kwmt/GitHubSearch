package net.kwmt27.codesearch.presenter.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import net.kwmt27.codesearch.App;
import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.util.Logger;

import java.util.List;

public class MainPresenter implements IMainPresenter {


    private IMainView mMainView;
    private GithubRepoEntity mGithubRepoEntity;

    public MainPresenter(IMainView mainView) {
        mMainView = mainView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = mMainView.getIntent();
        if(intent == null) {
            return;
        }

        // 検索メニューを非表示にする。
        // TODO

        if (TextUtils.equals(intent.getAction(), Intent.ACTION_SEND)) {
            Bundle extras = intent.getExtras();
            CharSequence extraText = extras.getCharSequence(Intent.EXTRA_TEXT);
            if (!TextUtils.isEmpty(extraText)) {
                String url = (String) extraText;
                Logger.d("url:" + url);
                if(Uri.parse(url).getHost() == null){
                    mMainView.showError(App.getInstance().getString(R.string.select_share_from_right_menu));
                    mMainView.finish();
                    return;
                }
                if(!Uri.parse(url).getHost().equals("github.com")) {
                    mMainView.showError(App.getInstance().getString(R.string.failed_not_from_github_page));
                    mMainView.finish();
                    return;
                }
                List<String> segments = Uri.parse(url).getPathSegments();
                if (segments.size() >= 2) {
                    mGithubRepoEntity = new GithubRepoEntity(segments.get(1), segments.get(0));
                }
                mMainView.setupComponents(url, mGithubRepoEntity);
            } else {
                Logger.e("url is empty.");
                mMainView.showError(App.getInstance().getString(R.string.failed_display));
            }
        } else {
            mMainView.setupComponents(null, null);
        }

    }

    @Override
    public void onStop() {
        ModelLocator.getSearchCodeModel().unsubscribe();
    }


    private GithubRepoEntity getGitHubRepoEntityFromIntent() {
            return mGithubRepoEntity;
    }

    public interface IMainView {
        void setupComponents(String url, GithubRepoEntity githubRepoEntity);

        Intent getIntent();

        void showError(String message);

        void finish();

    }

}
