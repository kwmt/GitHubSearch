package net.kwmt27.codesearch.presenter.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.entity.ItemEntity;
import net.kwmt27.codesearch.model.rx.ApiSubscriber;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.view.search.SearchCodeResultListFragment;

import java.util.List;

public class SearchCodeResultListPresenter implements ISearchResultListPresenter {


    private ISearchResultListView mSearchResultListView;

    public SearchCodeResultListPresenter(ISearchResultListView searchResultListView) {
        mSearchResultListView = searchResultListView;
    }


    @Override
    public void onDestroyView() {
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
    public void onEditorActionSearch(String keyword, String repositoryFullName, GithubRepoEntity entity) {
        ModelLocator.getSearchCodeModel().clear();
        searchCode(keyword, repositoryFullName, null);
    }

    @Override
    public void onClickReloadButton() {
        searchCodeOnScroll(null);
    }


    private void searchCode(String keyword, String repositoryFullName, Integer page) {
        searchCodeImpl(false, keyword, repositoryFullName, page);
    }

    private void searchCodeOnScroll(Integer page) {
        searchCodeImpl(true, null, null, page);
    }

    private void searchCodeImpl(boolean onScroll, String keyword, String repositoryFullName, Integer page){
        if(onScroll){
            mSearchResultListView.showProgressOnScroll();
            keyword = ModelLocator.getSearchCodeModel().getKeyword();
            repositoryFullName = ModelLocator.getSearchCodeModel().getRepositoryFullName();
        } else {
            mSearchResultListView.showProgress();
        }
        ModelLocator.getSearchCodeModel().searchCode(keyword, repositoryFullName, page, new ApiSubscriber<List<ItemEntity>>(((SearchCodeResultListFragment) mSearchResultListView).getActivity().getApplicationContext()) {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted is called.");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.e("onError is called. " + e);
                if(onScroll){
                    mSearchResultListView.hideProgressOnScroll();
                    mSearchResultListView.showErrorOnScroll();
                    return;
                }
                mSearchResultListView.hideProgress();
                mSearchResultListView.showError();
            }

            @Override
            public void onNext(List<ItemEntity> itemEntityList) {
                if(onScroll){
                    mSearchResultListView.hideProgressOnScroll();
                }else {
                    mSearchResultListView.hideProgress();
                }
                mSearchResultListView.updateSearchResultListView(itemEntityList);
            }
        });
    }


    public interface ISearchResultListView {
        void setupComponents(View view, Bundle savedInstanceState);

        void updateSearchResultListView(List<ItemEntity> itemEntityList);

        void onEditorActionSearch(String keyword, String repositoryFullName, @Nullable GithubRepoEntity entity);

        void showProgress();

        void hideProgress();

        void showError();

        void showProgressOnScroll();

        void hideProgressOnScroll();

        void showErrorOnScroll();

    }

}
