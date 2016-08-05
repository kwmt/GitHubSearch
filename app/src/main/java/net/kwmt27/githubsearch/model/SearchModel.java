package net.kwmt27.githubsearch.model;

import android.net.Uri;
import android.text.TextUtils;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.entity.SearchCodeResultEntity;
import net.kwmt27.githubsearch.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubsearch.model.rx.ReusableCompositeSubscription;
import net.kwmt27.githubsearch.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SearchModel {

    private static final String TAG = SearchModel.class.getSimpleName();
    private List<GithubRepoEntity> mGitHubRepoEntityList = new ArrayList<>();
    private GithubRepoEntity mGitHubRepo;
    private SearchCodeResultEntity mSearchCodeResultEntity;
    private SearchRepositoryResultEntity mSearchRepositoryResultEntity;
    private String mKeyword;

    private ReusableCompositeSubscription mCompositeSubscription = new ReusableCompositeSubscription();
    private Map<String, List<String>> mHeadaersMap;

    public void unsubscribe() {
        if(mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void clear() {
        mGitHubRepoEntityList = null;
    }


    public Subscription fetchListReposByUser(Integer page, final Subscriber<List<GithubRepoEntity>> subscriber) {
        Subscription listReposSubscription = ModelLocator.getApiClient().api.listRepos("kwmt", page)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<Response<List<GithubRepoEntity>>, Observable<List<GithubRepoEntity>>>() {
                    @Override
                    public Observable<List<GithubRepoEntity>> call(Response<List<GithubRepoEntity>> listResponse) {
                        mHeadaersMap = listResponse.headers().toMultimap();
                        if(mGitHubRepoEntityList != null && mGitHubRepoEntityList.size()> 0){
                            List<GithubRepoEntity> newList = new ArrayList<>(mGitHubRepoEntityList);
                            newList.addAll(listResponse.body());
                            mGitHubRepoEntityList = newList;
                        } else {
                            mGitHubRepoEntityList = listResponse.body();
                        }
                        return Observable.just(mGitHubRepoEntityList);
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

    public Subscription searchCode(String keyword,  String repo, final Subscriber<SearchCodeResultEntity> subscriber) {
        mKeyword = keyword;
        // FIXME: least one repo or user
        keyword += "+repo:" + repo;

        Subscription subscription = ModelLocator.getApiClient().api.searchCode(keyword)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<SearchCodeResultEntity, Observable<SearchCodeResultEntity>>() {
                    @Override
                    public Observable<SearchCodeResultEntity> call(SearchCodeResultEntity searchResultEntity) {
                        mSearchCodeResultEntity = searchResultEntity;
                        return Observable.just(searchResultEntity);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        return subscription;
    }
    public Subscription searchRepositories(String keyword, final Subscriber<SearchRepositoryResultEntity> subscriber) {
        mKeyword = keyword;
        Subscription subscription = ModelLocator.getApiClient().api.searchRepositories(keyword)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<SearchRepositoryResultEntity, Observable<SearchRepositoryResultEntity>>() {
                    @Override
                    public Observable<SearchRepositoryResultEntity> call(SearchRepositoryResultEntity searchResultEntity) {
                        mSearchRepositoryResultEntity = searchResultEntity;
                        return Observable.just(searchResultEntity);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        return subscription;
    }


    public GithubRepoEntity getGitHubRepo() {
        return mGitHubRepo;
    }

    public String getKeyword() {
        if(mKeyword == null) { return ""; }
        return mKeyword;
    }

    public int getLastPage() {
        return extractLink("last");
    }
    public int getNextPage() {
        return extractLink("next");
    }

    private int extractLink(String rel) {
        if (mHeadaersMap == null) {
            return 0;
        }
        if (!mHeadaersMap.containsKey("link")) {
            return 1;
        }
        List<String> values = mHeadaersMap.get("link");
        if (values.size() > 0) {
            String linkValue = values.get(0);
            String[] splitLinkValue = linkValue.split(",");
            for (String v : splitLinkValue) {
                if (v.contains(rel)) {
                    v = v.trim();
                    int startIndex = v.indexOf("<");
                    int endIndex = v.indexOf(">");
                    String url = v.substring(startIndex, endIndex);
                    Uri uri = Uri.parse(url);
                    String pageString = uri.getQueryParameter("page");
                    return Integer.valueOf(pageString);
                }
            }
        }
        // ここまでこないはず
        return 0;
    }

    public boolean hasNextPage() {
        return  getNextPage() > 0;
    }
}
