package net.kwmt27.githubviewer.presenter;

import android.os.Bundle;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;

import java.util.List;

import rx.Subscriber;

public class DetailPresenter implements IDetailPresenter {

    private IDetailView mDetailView;

    public DetailPresenter(IDetailView detailView) {
        mDetailView = detailView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mDetailView.setupComponents();
        fetchGitHubRepoList();

    }

    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
    }





    private void fetchGitHubRepoList() {
        ModelLocator.getGithubService().fetchListReposByUser(new Subscriber<List<GithubRepoEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<GithubRepoEntity> githubRepoEntities) {
                mDetailView.updateDetailView(githubRepoEntities);
            }
        });
    }


    public interface IDetailView {
        void setupComponents();
        void updateDetailView(List<GithubRepoEntity> githubRepoEntities);
    }

}
