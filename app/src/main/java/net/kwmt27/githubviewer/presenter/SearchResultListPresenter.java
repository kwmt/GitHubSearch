package net.kwmt27.githubviewer.presenter;

import android.os.Bundle;
import android.view.View;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;

import rx.Subscriber;

public class SearchResultListPresenter implements ISearchResultListPresenter {


    private ISearchResultListView mSearchResultListView;

    public SearchResultListPresenter(ISearchResultListView searchResultListView) {
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
//        fetchGitHubRepo(owner, repo);

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
                mSearchResultListView.updateDetailView(githubRepoEntity);
            }
        });
    }
    public interface ISearchResultListView {
        void setupComponents(View view, Bundle savedInstanceState);

        void updateDetailView(GithubRepoEntity githubRepoEntity);

    }

}
