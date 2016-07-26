package net.kwmt27.githubviewer.presenter;

import android.os.Bundle;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;

import java.util.List;

import rx.Subscriber;

public class MainPresenter implements IMainPresenter {

    private IMainView mMainView;

    public MainPresenter(IMainView mainView) {
        mMainView = mainView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mMainView.setupComponents();
         fetchGitHubRepoList();

//        List<GithubRepoEntity> datasource
//                = Arrays.asList(new GithubRepoEntity("data1"), new GithubRepoEntity("data2"));
//        mMainView.updateDetailView(datasource);
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
                mMainView.updateGitHubRepoListView(githubRepoEntities);
            }
        });
    }


    public interface IMainView {
        void setupComponents();
        void updateGitHubRepoListView(List<GithubRepoEntity> githubRepoEntities);
    }

}
