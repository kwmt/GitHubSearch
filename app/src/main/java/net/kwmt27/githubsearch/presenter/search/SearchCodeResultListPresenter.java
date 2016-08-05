package net.kwmt27.githubsearch.presenter.search;

import android.os.Bundle;
import android.view.View;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.entity.SearchCodeResultEntity;
import net.kwmt27.githubsearch.model.rx.ApiSubscriber;
import net.kwmt27.githubsearch.util.Logger;
import net.kwmt27.githubsearch.view.search.SearchCodeResultListFragment;

public class SearchCodeResultListPresenter implements ISearchResultListPresenter {


    private ISearchResultListView mSearchResultListView;

    public SearchCodeResultListPresenter(ISearchResultListView searchResultListView) {
        mSearchResultListView = searchResultListView;
    }


    @Override
    public void onStop() {
        ModelLocator.getSearchModel().unsubscribe();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSearchResultListView.setupComponents(view, savedInstanceState);
    }

    @Override
    public void onScrollToBottom() {
//        searchCode();
    }

    @Override
    public void onEditorActionSearch(String keyword, GithubRepoEntity entity) {
        searchCode(keyword, entity);
    }


    private void searchCode(String keyword, GithubRepoEntity entity) {
        mSearchResultListView.showProgress();
        String repo = entity.getFullName();
        ModelLocator.getSearchModel().searchCode(keyword, repo, new ApiSubscriber<SearchCodeResultEntity>(((SearchCodeResultListFragment) mSearchResultListView).getActivity().getApplicationContext()) {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted is called.");
                mSearchResultListView.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.e("onError is called. " + e);
                mSearchResultListView.hideProgress();
                mSearchResultListView.showError();
            }

            @Override
            public void onNext(SearchCodeResultEntity entity) {
                mSearchResultListView.updateSearchResultListView(entity);
            }
        });
    }


    public interface ISearchResultListView {
        void setupComponents(View view, Bundle savedInstanceState);

        void updateSearchResultListView(SearchCodeResultEntity searchCodeResultEntity);

        void onEditorActionSearch(String keyword, GithubRepoEntity entity);

        void showProgress();

        void hideProgress();

        void showError();

        void showProgressOnScroll();

        void hideProgressOnScroll();

        void showErrorOnScroll();

    }

}
