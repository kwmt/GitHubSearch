package net.kwmt27.githubviewer.presenter;

import android.content.Intent;
import android.os.Bundle;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;

import rx.Subscriber;

public class DetailPresenter implements IDetailPresenter {


    private IDetailView mDetailView;

    public DetailPresenter(IDetailView detailView) {
        mDetailView = detailView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = mDetailView.getIntent();
        GithubRepoEntity entity = (GithubRepoEntity) intent.getSerializableExtra(REPO_ENTITY_KET);
        mDetailView.setupComponents(entity);
        //fetchGitHubRepo(owner, repo);
    }

    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
    }


    private void fetchGitHubRepo(String user, String repo) {
        ModelLocator.getGithubService().fetchRepo(user, repo, new Subscriber<GithubRepoEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(GithubRepoEntity githubRepoEntity) {
                mDetailView.updateDetailView(githubRepoEntity);
            }
        });
    }


    public interface IDetailView {
        void setupComponents();

        void setupComponents(GithubRepoEntity githubRepoEntity);

        void updateDetailView(GithubRepoEntity githubRepoEntity);

        void updateDetailView(String url);

        Intent getIntent();
    }

}
