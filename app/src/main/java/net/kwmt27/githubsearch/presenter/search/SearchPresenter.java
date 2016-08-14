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
        ModelLocator.getSearchCodeModel().unsubscribe();
    }


    public interface ISearchView {
        void setupComponents();

        Intent getIntent();

        void showProgress();

        void hideProgress();
    }

}
