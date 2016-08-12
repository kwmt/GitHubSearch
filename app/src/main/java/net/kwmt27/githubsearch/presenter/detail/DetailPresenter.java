package net.kwmt27.githubsearch.presenter.detail;

import android.content.Intent;
import android.os.Bundle;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.view.DetailActivity;
import net.kwmt27.githubsearch.view.search.SearchActivity;

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
        ModelLocator.getSearchModel().unsubscribe();
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
