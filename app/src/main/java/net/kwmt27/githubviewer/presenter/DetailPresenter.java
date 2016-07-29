package net.kwmt27.githubviewer.presenter;

import android.content.Intent;
import android.os.Bundle;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;

public class DetailPresenter implements IDetailPresenter {


    private IDetailView mDetailView;

    public DetailPresenter(IDetailView detailView) {
        mDetailView = detailView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = mDetailView.getIntent();
        GithubRepoEntity entity = (GithubRepoEntity) intent.getSerializableExtra(REPO_ENTITY_KEY);
        mDetailView.setupComponents(entity);
    }

    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
    }

    public interface IDetailView {
        void setupComponents();

        void setupComponents(GithubRepoEntity githubRepoEntity);

        Intent getIntent();
    }

}
