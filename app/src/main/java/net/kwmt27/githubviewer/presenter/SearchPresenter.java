package net.kwmt27.githubviewer.presenter;

import android.content.Intent;
import android.os.Bundle;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;

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
//        String owner = intent.getStringExtra(OWENER_KEY);
//        String repo = intent.getStringExtra(REPO_KEY);
//        fetchGitHubRepo(owner, repo);

    }

    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
    }


    private void fetchGitHubRepo(String user, String repo) {
        ModelLocator.getGithubService().fetchRepo(user, repo, new Subscriber<GithubRepoEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GithubRepoEntity githubRepoEntity) {
                mSearchView.updateDetailView(githubRepoEntity);
            }
        });
    }


    public interface ISearchView {
        void setupComponents();

        void updateDetailView(GithubRepoEntity githubRepoEntity);

        Intent getIntent();
    }

}
