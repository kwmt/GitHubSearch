package net.kwmt27.githubsearch.presenter.search;

import android.os.Bundle;
import android.view.View;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.entity.ItemEntity;
import net.kwmt27.githubsearch.model.rx.ApiSubscriber;
import net.kwmt27.githubsearch.util.Logger;
import net.kwmt27.githubsearch.view.search.SearchCodeResultListFragment;

import java.util.List;

public class SearchCodeResultListPresenter implements ISearchResultListPresenter {


    private ISearchResultListView mSearchResultListView;

    public SearchCodeResultListPresenter(ISearchResultListView searchResultListView) {
        mSearchResultListView = searchResultListView;
    }


    @Override
    public void onStop() {
        ModelLocator.getSearchCodeModel().unsubscribe();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSearchResultListView.setupComponents(view, savedInstanceState);
    }

    @Override
    public void onScrollToBottom() {
        if(ModelLocator.getSearchCodeModel().hasNextPage()) {
            searchCodeOnScroll(ModelLocator.getSearchCodeModel().getNextPage());
        }
    }

    @Override
    public void onEditorActionSearch(String keyword, GithubRepoEntity entity) {
        ModelLocator.getSearchCodeModel().clear();
        searchCode(keyword, entity, null);
    }

    @Override
    public void onClickReloadButton() {
        searchCodeOnScroll(null);
    }


    private void searchCode(String keyword, GithubRepoEntity entity, Integer page) {
        mSearchResultListView.showProgress();
        String repo = entity.getFullName();
        ModelLocator.getSearchCodeModel().searchCode(keyword, repo, page, new ApiSubscriber<List<ItemEntity>>(((SearchCodeResultListFragment) mSearchResultListView).getActivity().getApplicationContext()) {
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
            public void onNext(List<ItemEntity> itemEntityList) {
                mSearchResultListView.updateSearchResultListView(itemEntityList);
            }
        });
    }

    private void searchCodeOnScroll(Integer page) {
        mSearchResultListView.showProgressOnScroll();
        String keyword = ModelLocator.getSearchCodeModel().getKeyword();
        String repo = ModelLocator.getSearchCodeModel().getRepository();
        ModelLocator.getSearchCodeModel().searchCode(keyword, repo, page, new ApiSubscriber<List<ItemEntity>>(((SearchCodeResultListFragment) mSearchResultListView).getActivity().getApplicationContext()) {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted is called.");
                mSearchResultListView.hideProgressOnScroll();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.e("onError is called. " + e);
                mSearchResultListView.hideProgressOnScroll();
                mSearchResultListView.showErrorOnScroll();
            }

            @Override
            public void onNext(List<ItemEntity> itemEntityList) {
                mSearchResultListView.updateSearchResultListView(itemEntityList);
            }
        });
    }


    public interface ISearchResultListView {
        void setupComponents(View view, Bundle savedInstanceState);

        void updateSearchResultListView(List<ItemEntity> itemEntityList);

        void onEditorActionSearch(String keyword, GithubRepoEntity entity);

        void showProgress();

        void hideProgress();

        void showError();

        void showProgressOnScroll();

        void hideProgressOnScroll();

        void showErrorOnScroll();

    }

}
