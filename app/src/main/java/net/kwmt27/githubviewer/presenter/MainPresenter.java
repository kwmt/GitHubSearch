package net.kwmt27.githubviewer.presenter;

import android.os.Bundle;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.model.rx.ApiSubscriber;
import net.kwmt27.githubviewer.view.MainActivity;

import java.util.List;

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
//        mMainView.updateSearchRepositoryResultView(datasource);
    }

    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
    }





    private void fetchGitHubRepoList() {
        mMainView.showProgress();
        ModelLocator.getGithubService().fetchListReposByUser(new ApiSubscriber<List<GithubRepoEntity>>((MainActivity)mMainView) {
            @Override
            public void onCompleted() {
                mMainView.hideProgress();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                mMainView.hideProgress();
                mMainView.showError();
            }

            @Override
            public void onNext(List<GithubRepoEntity> githubRepoEntities) {
                mMainView.updateGitHubRepoListView(githubRepoEntities);
            }
        });
    }

    @Override
    public void onClickReloadButton() {
        fetchGitHubRepoList();
    }

    public interface IMainView {
        void setupComponents();
        void updateGitHubRepoListView(List<GithubRepoEntity> githubRepoEntities);

        void showProgress();

        void hideProgress();

        void showError();
    }

}
