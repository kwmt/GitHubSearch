package net.kwmt27.githubviewer.presenter.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.entity.SearchCodeResultEntity;
import net.kwmt27.githubviewer.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubviewer.util.Logger;
import net.kwmt27.githubviewer.view.search.SearchActivity;
import net.kwmt27.githubviewer.view.search.SearchCodeResultListFragment;
import net.kwmt27.githubviewer.view.search.SearchRepositoryResultListFragment;

import rx.Subscriber;

public class SearchPresenter implements ISearchPresenter {


    private ISearchView mSearchView;
    private boolean mCanSearchCode = false;

    public SearchPresenter(ISearchView searchView) {
        mSearchView = searchView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSearchView.setupComponents();
        Intent intent = mSearchView.getIntent();
        mCanSearchCode = intent.getBooleanExtra(CAN_SEARCH_CODE, false);


        if (savedInstanceState == null) {
            FragmentManager manager = ((SearchActivity) mSearchView).getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (mCanSearchCode) {
                transaction.replace(R.id.container, SearchCodeResultListFragment.newInstance(), SearchCodeResultListFragment.TAG);
            } else {
                transaction.replace(R.id.container, SearchRepositoryResultListFragment.newInstance(), SearchRepositoryResultListFragment.TAG);
            }
            transaction.commit();
        }
    }

    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
    }


    @Override
    public void onEditorActionSearch(String keyword) {
        search(keyword);
    }

    private void search(String keyword) {
        if (mCanSearchCode) {
            searchCode(keyword);
        } else {
            searchRepository(keyword);
        }
    }

    private void searchCode(String keyword) {
        String repo = getGitHubRepoEntityFromIntent().getFullName();
        ModelLocator.getGithubService().searchCode(keyword, repo, new Subscriber<SearchCodeResultEntity>() {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted is called.");
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("onError is called. " + e);
            }

            @Override
            public void onNext(SearchCodeResultEntity entity) {
                mSearchView.updateSearchCodeResultView(entity);
            }
        });
    }

    private void searchRepository(String keyword) {
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
                mSearchView.updateSearchRepositoryResultView(searchRepositoryResultEntity);
            }
        });
    }


    private GithubRepoEntity getGitHubRepoEntityFromIntent() {
        Intent intent = mSearchView.getIntent();
        return (GithubRepoEntity) intent.getSerializableExtra(REPO_ENTITY_KEY);
    }

    public interface ISearchView {
        void setupComponents();

        void updateSearchRepositoryResultView(SearchRepositoryResultEntity searchRepositoryResultEntity);

        void updateSearchCodeResultView(SearchCodeResultEntity entity);

        Intent getIntent();
    }

}
