package net.kwmt27.githubviewer.presenter;

import android.os.Bundle;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.model.rx.ApiSubscriber;
import net.kwmt27.githubviewer.view.MainActivity;

import java.util.List;

public class MainPresenter implements IMainPresenter {

    private IMainView mMainView;

    public MainPresenter(IMainView mainView) {
        mMainView = mainView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mMainView.setupComponents();
        fetchGitHubRepoList(null);
    }

    @Override
    public void onStop() {
        ModelLocator.getGithubService().unsubscribe();
        ModelLocator.getGithubService().clear();
    }


    @Override
    public void onClickReloadButton() {
        fetchGitHubRepoList(null);
    }

    @Override
    public void onScrollToBottom() {
        int page = ModelLocator.getGithubService().getNextPage();
        if(page > 0) {
            fetchGitHubRepoList(page);
        }
    }

    private void fetchGitHubRepoList(Integer page) {
        mMainView.showProgress();
        ModelLocator.getGithubService().fetchListReposByUser(page, new ApiSubscriber<List<GithubRepoEntity>>((MainActivity)mMainView) {
            @Override
            public void onCompleted() {
                mMainView.hideProgress();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                mMainView.hideProgress();
                mMainView.showError();
            }

            @Override
            public void onNext(List<GithubRepoEntity> githubRepoEntities) {
                mMainView.updateGitHubRepoListView(githubRepoEntities);
            }
        });
    }

    public interface IMainView {
        void setupComponents();
        void updateGitHubRepoListView(List<GithubRepoEntity> githubRepoEntities);

        void showProgress();

        void hideProgress();

        void showError();
    }

}
