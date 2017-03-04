package net.kwmt27.codesearch.presenter.search;

import android.content.Intent;
import android.os.Bundle;

import net.kwmt27.codesearch.ModelLocator;

public class SearchPresenter implements ISearchPresenter {


    private ISearchView mSearchView;

    public SearchPresenter(ISearchView searchView) {
        mSearchView = searchView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSearchView.setupComponents(savedInstanceState);

    }

    @Override
    public void onStop() {
        ModelLocator.getSearchCodeModel().unsubscribe();
    }


    public interface ISearchView {
        void setupComponents(Bundle savedInstanceState);

        Intent getIntent();

        void showProgress();

        void hideProgress();
    }

}
