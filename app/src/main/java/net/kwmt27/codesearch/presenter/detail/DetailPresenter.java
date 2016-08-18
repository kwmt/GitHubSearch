package net.kwmt27.codesearch.presenter.detail;

import android.content.Intent;
import android.os.Bundle;

import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.view.detail.DetailActivity;
import net.kwmt27.codesearch.view.search.SearchActivity;

public class DetailPresenter implements IDetailPresenter {


    private IDetailView mDetailView;

    public DetailPresenter(IDetailView detailView) {
        mDetailView = detailView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = mDetailView.getIntent();
        mDetailView.setupComponents(intent.getStringExtra(URL_KEY));
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
        Intent intent = mDetailView.getIntent();
        return (GithubRepoEntity) intent.getSerializableExtra(REPO_ENTITY_KEY);
    }

    public interface IDetailView {
        void setupComponents();

        void setupComponents(String url);

        Intent getIntent();
    }

}
