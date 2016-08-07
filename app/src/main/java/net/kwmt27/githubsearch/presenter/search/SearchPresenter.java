package net.kwmt27.githubsearch.presenter.search;

import android.content.Intent;
import android.os.Bundle;

import net.kwmt27.githubsearch.ModelLocator;

public class SearchPresenter implements ISearchPresenter {


    private ISearchView mSearchView;

    public SearchPresenter(ISearchView searchView) {
        mSearchView = searchView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSearchView.setupComponents();

    }

    @Override
    public void onStop() {
        ModelLocator.getSearchModel().unsubscribe();
    }


    @Override
    public void onClickReloadButton() {
        String keyword = ModelLocator.getSearchModel().getKeyword();
        //searchRepository(keyword);
    }



    public interface ISearchView {
        void setupComponents();

//        void updateSearchRepositoryResultView(SearchRepositoryResultEntity searchRepositoryResultEntity);
//
//        void updateSearchCodeResultView(SearchCodeResultEntity entity);

        Intent getIntent();

        void showProgress();

        void hideProgress();

        void showError();
    }

}
