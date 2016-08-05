package net.kwmt27.githubsearch.presenter;

import android.os.Bundle;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.model.rx.ApiSubscriber;
import net.kwmt27.githubsearch.view.MainActivity;

import java.util.List;

public class MainPresenter implements IMainPresenter {

    private IMainView mMainView;

    public MainPresenter(IMainView mainView) {
        mMainView = mainView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mMainView.setupComponents();
        fetchRepositoryList(null);
    }

    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
        ModelLocator.getGithubService().clear();
    }


    @Override
    public void onClickReloadButton() {
        fetchRepositoryList(null);
    }

    @Override
    public void onScrollToBottom() {
        if(ModelLocator.getGithubService().hasNextPage()) {
            fetchRepositoryListOnScroll(ModelLocator.getGithubService().getNextPage());
        }
    }

    private void fetchRepositoryList(Integer page) {
        mMainView.showProgress();
        ModelLocator.getGithubService().fetchListReposByUser(page, new ApiSubscriber<List<GithubRepoEntity>>((MainActivity)mMainView) {
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

    private void fetchRepositoryListOnScroll(Integer page) {
        mMainView.showProgressOnScroll();
        ModelLocator.getGithubService().fetchListReposByUser(page, new ApiSubscriber<List<GithubRepoEntity>>((MainActivity)mMainView) {
            @Override
            public void onCompleted() {
                mMainView.hideProgressOnScroll();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                mMainView.hideProgressOnScroll();
                mMainView.showErrorOnScroll();
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

        void showProgress();

        void hideProgress();

        void showError();

        void showProgressOnScroll();

        void hideProgressOnScroll();

        void showErrorOnScroll();

    }

}
