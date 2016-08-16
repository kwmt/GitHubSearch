package net.kwmt27.codesearch.presenter.search;

import android.os.Bundle;
import android.view.View;

import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.model.rx.ApiSubscriber;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.view.search.SearchRepositoryResultListFragment;

import java.util.List;

public class SearchRepositoryResultListPresenter implements ISearchResultListPresenter {


    private ISearchResultListView mSearchResultListView;

    public SearchRepositoryResultListPresenter(ISearchResultListView searchResultListView) {
        mSearchResultListView = searchResultListView;
    }


    @Override
    public void onStop() {
        ModelLocator.getSearchRepositoryModel().unsubscribe();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSearchResultListView.setupComponents(view, savedInstanceState);
    }

    @Override
    public void onScrollToBottom() {
        if(ModelLocator.getSearchRepositoryModel().hasNextPage()) {
            searchRepository(ModelLocator.getSearchRepositoryModel().getKeyword(), ModelLocator.getSearchRepositoryModel().getNextPage());
        }

    }

    @Override
    public void onEditorActionSearch(String keyword, GithubRepoEntity entity) {
        ModelLocator.getSearchRepositoryModel().clear();
        searchRepository(keyword, null);
    }

    @Override
    public void onClickReloadButton() {
        searchRepository(ModelLocator.getSearchRepositoryModel().getKeyword(), null);
    }

    private void searchRepository(String keyword, final Integer page) {
        if(page == null) {
            mSearchResultListView.showProgress();
        } else {
            mSearchResultListView.showProgressOnScroll();
        }
        ModelLocator.getSearchRepositoryModel().searchRepositories(keyword, page, new ApiSubscriber<List<GithubRepoEntity>>(((SearchRepositoryResultListFragment) mSearchResultListView).getActivity().getApplicationContext()) {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted is called.");
                if(page == null) {
                    mSearchResultListView.hideProgress();
                } else {
                    mSearchResultListView.hideProgressOnScroll();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.e("onError is called. " + e);
                if(page == null) {
                    mSearchResultListView.hideProgress();
                    mSearchResultListView.showError();
                } else {
                    mSearchResultListView.hideProgressOnScroll();
                    mSearchResultListView.showErrorOnScroll();
                }
            }

            @Override
            public void onNext(List<GithubRepoEntity> entities) {
                mSearchResultListView.updateSearchResultListView(entities);
            }
        });
    }

    public interface ISearchResultListView {
        void setupComponents(View view, Bundle savedInstanceState);

        void updateSearchResultListView(List<GithubRepoEntity> entities);

        void onEditorActionSearch(String keyword, GithubRepoEntity entity);

        void showProgress();

        void hideProgress();

        void showError();

        void showProgressOnScroll();

        void hideProgressOnScroll();

        void showErrorOnScroll();
    }

}
