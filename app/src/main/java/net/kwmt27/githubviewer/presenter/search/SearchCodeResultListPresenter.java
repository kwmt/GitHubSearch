package net.kwmt27.githubviewer.presenter.search;

import android.os.Bundle;
import android.view.View;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.SearchCodeResultEntity;

public class SearchCodeResultListPresenter implements ISearchResultListPresenter {


    private ISearchResultListView mSearchResultListView;

    public SearchCodeResultListPresenter(ISearchResultListView searchResultListView) {
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

        void updateSearchResultListView(SearchCodeResultEntity searchCodeResultEntity);

    }

}
