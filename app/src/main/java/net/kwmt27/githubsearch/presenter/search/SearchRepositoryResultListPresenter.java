package net.kwmt27.githubsearch.presenter.search;

import android.os.Bundle;
import android.view.View;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.entity.SearchRepositoryResultEntity;

public class SearchRepositoryResultListPresenter implements ISearchResultListPresenter {


    private ISearchResultListView mSearchResultListView;

    public SearchRepositoryResultListPresenter(ISearchResultListView searchResultListView) {
        mSearchResultListView = searchResultListView;
    }


    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSearchResultListView.setupComponents(view, savedInstanceState);
    }

    public interface ISearchResultListView {
        void setupComponents(View view, Bundle savedInstanceState);

        void updateSearchResultListView(SearchRepositoryResultEntity searchRepositoryResultEntity);

    }

}
