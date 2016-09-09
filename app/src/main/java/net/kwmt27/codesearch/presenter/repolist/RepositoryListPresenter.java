package net.kwmt27.codesearch.presenter.repolist;

import android.os.Bundle;
import android.view.View;

import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.model.SortType;
import net.kwmt27.codesearch.model.rx.ApiSubscriber;
import net.kwmt27.codesearch.view.repolist.RepositoryListFragment;

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
        ModelLocator.getSearchRepositoryModel().unsubscribe();
        //ModelLocator.getSearchRepositoryModel().clear();
    }


    @Override
    public void onClickReloadButton() {
        fetchRepositoryList(null);
    }

    @Override
    public void onScrollToBottom() {
        if(ModelLocator.getSearchRepositoryModel().hasNextPage()) {
            fetchRepositoryListOnScroll(ModelLocator.getSearchRepositoryModel().getNextPage());
        }
    }

    private void fetchRepositoryList(Integer page) {
        if(ModelLocator.getSearchRepositoryModel().hasGitHubRepoEntityList()){
            mMainView.updateGitHubRepoListView(ModelLocator.getSearchRepositoryModel().getGitHubRepoEntityList());
            return;
        }

        mMainView.showProgress();
        ModelLocator.getSearchRepositoryModel().fetchUserRepository(page, SortType.Pushed, new ApiSubscriber<List<GithubRepoEntity>>(((RepositoryListFragment)mMainView).getActivity()) {
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
        ModelLocator.getSearchRepositoryModel().fetchUserRepository(page, SortType.Pushed, new ApiSubscriber<List<GithubRepoEntity>>(((RepositoryListFragment)mMainView).getActivity()) {
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
