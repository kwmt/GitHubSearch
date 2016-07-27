package net.kwmt27.githubviewer.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.SearchResultEntity;
import net.kwmt27.githubviewer.view.SearchActivity;
import net.kwmt27.githubviewer.view.SearchResultListFragment;

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
            transaction.add(R.id.container, new SearchResultListFragment());
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

    }

    private void searchCode(String keyword) {
        ModelLocator.getGithubService().searchCode(keyword, new Subscriber<SearchResultEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchResultEntity searchResultEntity) {
                        mSearchView.updateSearchResultView(SearchResultEntity);
                    }
                });
    }


    public interface ISearchView {
        void setupComponents();

        void updateSearchResultView(SearchResultEntity searchResultEntity);

        Intent getIntent();
    }

}
