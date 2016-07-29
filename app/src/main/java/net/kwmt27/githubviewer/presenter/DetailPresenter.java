package net.kwmt27.githubviewer.presenter;

import android.content.Intent;
import android.os.Bundle;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.view.DetailActivity;
import net.kwmt27.githubviewer.view.search.SearchActivity;

public class DetailPresenter implements IDetailPresenter {


    private IDetailView mDetailView;

    public DetailPresenter(IDetailView detailView) {
        mDetailView = detailView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mDetailView.setupComponents(getGitHubRepoEntityFromIntent());
    }

    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
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

        void setupComponents(GithubRepoEntity githubRepoEntity);

        Intent getIntent();
    }

}
