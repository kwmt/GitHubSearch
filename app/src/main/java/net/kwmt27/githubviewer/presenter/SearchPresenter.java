package net.kwmt27.githubviewer.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubviewer.util.Logger;
import net.kwmt27.githubviewer.view.SearchActivity;
import net.kwmt27.githubviewer.view.SearchRepositoryResultListFragment;

import rx.Subscriber;

public class SearchPresenter implements ISearchPresenter {


    private ISearchView mSearchView;

    public SearchPresenter(ISearchView searchView) {
        mSearchView = searchView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSearchView.setupComponents();
        Intent intent = mSearchView.getIntent();

        if (savedInstanceState == null) {
            FragmentManager manager = ((SearchActivity)mSearchView).getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.container, SearchRepositoryResultListFragment.newInstance(), SearchRepositoryResultListFragment.TAG);
            transaction.commit();
        }


//        String owner = intent.getStringExtra(OWENER_KEY);
//        String repo = intent.getStringExtra(REPO_KEY);
//        fetchGitHubRepo(owner, repo);

    }

    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
    }


    @Override
    public void onEditorActionSearch(String keyword) {
        searchCode(keyword);
    }

    private void searchCode(String keyword) {
        ModelLocator.getGithubService().searchRepositories(keyword, new Subscriber<SearchRepositoryResultEntity>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted is called.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("onError is called. " + e);
                    }

                    @Override
                    public void onNext(SearchRepositoryResultEntity searchRepositoryResultEntity) {
                        mSearchView.updateSearchResultView(searchRepositoryResultEntity);
                    }
                });
    }


    public interface ISearchView {
        void setupComponents();

        void updateSearchResultView(SearchRepositoryResultEntity searchRepositoryResultEntity);

        Intent getIntent();
    }

}
