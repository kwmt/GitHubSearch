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

    private IRepositoryListView mRepositoryListView;

    public RepositoryListPresenter(IRepositoryListView repositoryListView) {
        mRepositoryListView = repositoryListView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRepositoryListView.setupComponents(view, savedInstanceState);
        fetchRepositoryList(null, true);
    }

    @Override
    public void onDestroyView() {
        ModelLocator.getSearchRepositoryModel().unsubscribe();
        //ModelLocator.getSearchRepositoryModel().clear();
    }

    @Override
    public void onClickReloadButton() {
        fetchRepositoryList(null, true);
    }

    @Override
    public void onScrollToBottom() {
        if(ModelLocator.getSearchRepositoryModel().hasNextPage()) {
            fetchRepositoryListOnScroll(ModelLocator.getSearchRepositoryModel().getNextPage());
        }
    }

    @Override
    public void onRefresh() {
        ModelLocator.getSearchRepositoryModel().clear();
        fetchRepositoryList(null, false);
    }

    private void fetchRepositoryList(Integer page, boolean show) {
        if(ModelLocator.getSearchRepositoryModel().hasGitHubRepoEntityList()){
            mRepositoryListView.updateGitHubRepoListView(ModelLocator.getSearchRepositoryModel().getGitHubRepoEntityList());
            return;
        }
        fetchRepositoryListImpl(false, page, show);
    }

    private void fetchRepositoryListOnScroll(Integer page) {
        fetchRepositoryListImpl(true, page, false);
    }

    private void fetchRepositoryListImpl(boolean onScroll, Integer page, boolean show) {
        if (onScroll) {
            mRepositoryListView.showProgressOnScroll();
        }
        if (show) {
            mRepositoryListView.showProgress();
        }

        ModelLocator.getSearchRepositoryModel().fetchUserRepository(page, SortType.Pushed, new ApiSubscriber<List<GithubRepoEntity>>(((RepositoryListFragment) mRepositoryListView).getActivity()) {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                if (onScroll) {
                    mRepositoryListView.hideProgressOnScroll();
                    mRepositoryListView.showErrorOnScroll();
                    return;
                }
                mRepositoryListView.hideProgress();
                mRepositoryListView.hideSwipeRefreshLayout();
                mRepositoryListView.showError();
            }

            @Override
            public void onNext(List<GithubRepoEntity> githubRepoEntities) {
                if (onScroll) {
                    mRepositoryListView.hideProgressOnScroll();
                } else {
                    mRepositoryListView.hideProgress();
                    mRepositoryListView.hideSwipeRefreshLayout();
                }
                mRepositoryListView.updateGitHubRepoListView(githubRepoEntities);
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

        void hideSwipeRefreshLayout();
    }

}
