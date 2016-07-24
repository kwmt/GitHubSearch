package net.kwmt27.githubviewer.model;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GitHubViewerModel {

    private static final String TAG = GitHubViewerModel.class.getSimpleName();
    private List<GithubRepo> mGitHubRepoList;


    public Subscription fetchListReposByUser(final Subscriber<List<GithubRepo>> subscriber) {
        return ModelLocator.getApiClient().api.listRepos("kwmt")
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<List<GithubRepo>, Observable<List<GithubRepo>>>() {
                    @Override
                    public Observable<List<GithubRepo>> call(List<GithubRepo> githubRepos) {
                        mGitHubRepoList = githubRepos;
                        return Observable.just(githubRepos);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
