package net.kwmt27.githubsearch.presenter.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.R;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.entity.SearchCodeResultEntity;
import net.kwmt27.githubsearch.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubsearch.model.rx.ApiSubscriber;
import net.kwmt27.githubsearch.util.Logger;
import net.kwmt27.githubsearch.view.search.SearchActivity;
import net.kwmt27.githubsearch.view.search.SearchCodeResultListFragment;
import net.kwmt27.githubsearch.view.search.SearchRepositoryResultListFragment;

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
        ModelLocator.getSearchModel().unsubscribe();
    }


    @Override
    public void onEditorActionSearch(String keyword) {
        search(keyword);
    }

    @Override
    public void onClickReloadButton() {
        String keyword = ModelLocator.getSearchModel().getKeyword();
        searchRepository(keyword);
    }

    private void search(String keyword) {
        if (mCanSearchCode) {
            searchCode(keyword);
        } else {
            searchRepository(keyword);
        }
    }

    private void searchCode(String keyword) {
        mSearchView.showProgress();
        String repo = getGitHubRepoEntityFromIntent().getFullName();
        ModelLocator.getSearchModel().searchCode(keyword, repo, new ApiSubscriber<SearchCodeResultEntity>((SearchActivity) mSearchView) {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted is called.");
                mSearchView.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.e("onError is called. " + e);
                mSearchView.hideProgress();
                mSearchView.showError();
            }

            @Override
            public void onNext(SearchCodeResultEntity entity) {
                mSearchView.updateSearchCodeResultView(entity);
            }
        });
    }

    private void searchRepository(String keyword) {
        mSearchView.showProgress();
        ModelLocator.getSearchModel().searchRepositories(keyword, new ApiSubscriber<SearchRepositoryResultEntity>((SearchActivity) mSearchView) {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted is called.");
                mSearchView.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.e("onError is called. " + e);
                mSearchView.hideProgress();
                mSearchView.showError();
            }

            @Override
            public void onNext(SearchRepositoryResultEntity entity) {
                mSearchView.updateSearchRepositoryResultView(entity);
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

        void showProgress();

        void hideProgress();

        void showError();
    }

}
