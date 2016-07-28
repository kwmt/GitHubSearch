package net.kwmt27.githubviewer.presenter;

import android.os.Bundle;
import android.view.View;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.SearchCodeResultEntity;
import net.kwmt27.githubviewer.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubviewer.util.Logger;

import rx.Subscriber;

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
//        Intent intent = mSearchResultListView.getIntent();
//        String owner = intent.getStringExtra(OWENER_KEY);
//        String repo = intent.getStringExtra(REPO_KEY);
//        searchResult(owner, repo);

    }

    private void searchResult(String user, String repo) {
        ModelLocator.getGithubService().searchRepositories(user, new Subscriber<SearchRepositoryResultEntity>() {
            @Override
            public void onCompleted() {
                Logger.e("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("onError:" + e);
            }

            @Override
            public void onNext(SearchRepositoryResultEntity searchRepositoryResultEntity) {
                mSearchResultListView.updateSearchResultListView(searchRepositoryResultEntity);
            }
        });
    }

    public interface ISearchResultListView {
        void setupComponents(View view, Bundle savedInstanceState);

        void updateSearchResultListView(SearchRepositoryResultEntity searchRepositoryResultEntity);

    }

}
