package net.kwmt27.githubsearch.presenter.repolist;

import android.os.Bundle;
import android.view.View;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.model.rx.ApiSubscriber;
import net.kwmt27.githubsearch.view.RepositoryListFragment;

import java.util.List;

public class RepositoryListPresenter implements IRepositoryListPresenter {

    private IRepositoryListView mMainView;

    public RepositoryListPresenter(IRepositoryListView mainView) {
        mMainView = mainView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mMainView.setupComponents(view, savedInstanceState);
        fetchRepositoryList(null);
    }

    @Override
    public void onStop() {
        ModelLocator.getSearchModel().unsubscribe();
        ModelLocator.getSearchModel().clear();
    }


    @Override
    public void onClickReloadButton() {
        fetchRepositoryList(null);
    }

    @Override
    public void onScrollToBottom() {
        if(ModelLocator.getSearchModel().hasNextPageOfRepoList()) {
            fetchRepositoryListOnScroll(ModelLocator.getSearchModel().getNextPageOfRepoList());
        }
    }

    private void fetchRepositoryList(Integer page) {
        mMainView.showProgress();
        ModelLocator.getSearchModel().fetchUserRepository(page, new ApiSubscriber<List<GithubRepoEntity>>(((RepositoryListFragment)mMainView).getActivity()) {
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
        ModelLocator.getSearchModel().fetchUserRepository(page, new ApiSubscriber<List<GithubRepoEntity>>(((RepositoryListFragment)mMainView).getActivity()) {
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

    public interface IRepositoryListView {
        void setupComponents(View view, Bundle savedInstanceState);

        void updateGitHubRepoListView(List<GithubRepoEntity> githubRepoEntities);

        void showProgress();

        void hideProgress();

        void showError();

        void showProgressOnScroll();

        void hideProgressOnScroll();

        void showErrorOnScroll();

    }

}
