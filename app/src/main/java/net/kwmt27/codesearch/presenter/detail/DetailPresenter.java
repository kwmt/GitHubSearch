package net.kwmt27.codesearch.presenter.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import net.kwmt27.codesearch.App;
import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.view.detail.DetailActivity;
import net.kwmt27.codesearch.view.search.SearchActivity;

import java.io.Serializable;
import java.util.List;

public class DetailPresenter implements IDetailPresenter {


    private IDetailView mDetailView;
    private GithubRepoEntity mGithubRepoEntity;

    public DetailPresenter(IDetailView detailView) {
        mDetailView = detailView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = mDetailView.getIntent();

        Serializable serializableRepoEntity = intent.getSerializableExtra(REPO_ENTITY_KEY);
        if(serializableRepoEntity != null) {
            mGithubRepoEntity = (GithubRepoEntity)serializableRepoEntity;
        } else {
            // 検索メニューを非表示にする
            mDetailView.hideSearchMenu();
        }

        if (TextUtils.equals(intent.getAction(), Intent.ACTION_SEND)) {
            Bundle extras = intent.getExtras();
            CharSequence extraText = extras.getCharSequence(Intent.EXTRA_TEXT);
            if (!TextUtils.isEmpty(extraText)) {
                String url = (String) extraText;
                Logger.d("url:" + url);
                if(Uri.parse(url).getHost() == null){
                    mDetailView.showError(App.getInstance().getString(R.string.select_share_from_right_menu));
                    mDetailView.finish();
                    return;
                }
                if(!Uri.parse(url).getHost().equals("github.com")) {
                    mDetailView.showError(App.getInstance().getString(R.string.failed_not_from_github_page));
                    mDetailView.finish();
                    return;
                }
                List<String> segments = Uri.parse(url).getPathSegments();
                String title = "GitHub";
                if (segments.size() >= 2) {
                    mGithubRepoEntity = new GithubRepoEntity(segments.get(1), segments.get(0));
                    title = mGithubRepoEntity.getName();
                }
                mDetailView.setupComponents(url, title);
            } else {
                Logger.e("url is empty.");
                mDetailView.showError(App.getInstance().getString(R.string.failed_display));
            }
        } else {
            String url = intent.getStringExtra(URL_KEY);
            mDetailView.setupComponents(url, null);
        }

    }

    @Override
    public void onStop() {
        ModelLocator.getSearchCodeModel().unsubscribe();
    }

    @Override
    public void onActionSearchSelected() {
        SearchActivity.startActivity((DetailActivity)mDetailView, true, getGitHubRepoEntityFromIntent());

    }

    private GithubRepoEntity getGitHubRepoEntityFromIntent() {
            return mGithubRepoEntity;
    }

    public interface IDetailView {
        void setupComponents();

        void setupComponents(String url, String title);

        Intent getIntent();

        void showError(String message);

        void finish();

        void hideSearchMenu();
    }

}
