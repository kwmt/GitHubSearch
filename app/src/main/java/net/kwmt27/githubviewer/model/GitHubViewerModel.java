package net.kwmt27.githubviewer.model;

import android.text.TextUtils;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.entity.SearchResultEntity;
import net.kwmt27.githubviewer.util.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GitHubViewerModel {

    private static final String TAG = GitHubViewerModel.class.getSimpleName();
    private List<GithubRepoEntity> mGitHubRepoEntityList = new ArrayList<>();
    private GithubRepoEntity mGitHubRepo;
    private SearchResultEntity mSearchResultEntity;

    private ReusableCompositeSubscription mCompositeSubscription = new ReusableCompositeSubscription();

    public void unsubscribe() {
        if(mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public Subscription fetchListReposByUser(final Subscriber<List<GithubRepoEntity>> subscriber) {
        Subscription listReposSubscription = ModelLocator.getApiClient().api.listRepos("kwmt")
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<List<GithubRepoEntity>, Observable<List<GithubRepoEntity>>>() {
                    @Override
                    public Observable<List<GithubRepoEntity>> call(List<GithubRepoEntity> githubRepos) {
                        mGitHubRepoEntityList = githubRepos;
                        return Observable.just(githubRepos);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(listReposSubscription);
        return listReposSubscription;
    }

    public Subscription fetchRepo(String owner, String repo, final Subscriber<GithubRepoEntity> subscriber) {
        if(TextUtils.isEmpty(owner)) {
            Logger.e("owner is not specified.");
            return null;
        }
        if(TextUtils.isEmpty(repo)) {
            Logger.e("repo is not specified.");
            return null;
        }
        Subscription subscription = ModelLocator.getApiClient().api.fetchRepo(owner, repo)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<GithubRepoEntity, Observable<GithubRepoEntity>>() {
                    @Override
                    public Observable<GithubRepoEntity> call(GithubRepoEntity githubRepos) {
                        mGitHubRepo = githubRepos;
                        return Observable.just(githubRepos);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        return subscription;
    }

    public Subscription searchCode(String keyword, final Subscriber<SearchResultEntity> subscriber) {
        Subscription subscription = ModelLocator.getApiClient().api.searchCode("kwmt")
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<SearchResultEntity, Observable<SearchResultEntity>>() {
                    @Override
                    public Observable<SearchResultEntity> call(SearchResultEntity searchResultEntity) {
                        mSearchResultEntity = searchResultEntity;
                        return Observable.just(searchResultEntity);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        return subscription;
    }


}
