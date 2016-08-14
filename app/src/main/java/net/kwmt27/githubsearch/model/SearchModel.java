package net.kwmt27.githubsearch.model;

import android.net.Uri;
import android.text.TextUtils;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.entity.ItemEntity;
import net.kwmt27.githubsearch.entity.SearchCodeResultEntity;
import net.kwmt27.githubsearch.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubsearch.model.rx.RetrofitException;
import net.kwmt27.githubsearch.model.rx.ReusableCompositeSubscription;
import net.kwmt27.githubsearch.util.Logger;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SearchModel {

    private static final String TAG = SearchModel.class.getSimpleName();
    private List<GithubRepoEntity> mGitHubRepoEntityList = new ArrayList<>();
    private Map<String, List<String>> mHeadersMapOfRepoList;
    private SearchCodeResultEntity mSearchCodeResultEntity;
    private List<ItemEntity> mItemEntityList = new ArrayList<>();
    private Map<String, List<String>> mHeadersMapOfSearchCode;

    private GithubRepoEntity mGitHubRepo;
    private SearchRepositoryResultEntity mSearchRepositoryResultEntity;
    private String mKeyword;
    private String mRepository;

    private ReusableCompositeSubscription mCompositeSubscription = new ReusableCompositeSubscription();
    private List<GithubRepoEntity> mRepositoryResultList;

    public void unsubscribe() {
        if(mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void clear() {
        mGitHubRepoEntityList = null;
        mSearchCodeResultEntity = null;
        mItemEntityList = null;
        mRepositoryResultList = null;
    }


    public Subscription fetchUserRepository(Integer page, final Subscriber<List<GithubRepoEntity>> subscriber) {
        Subscription listReposSubscription = ModelLocator.getApiClient().api.fetchUserRepository(page)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<Response<List<GithubRepoEntity>>, Observable<List<GithubRepoEntity>>>() {
                    @Override
                    public Observable<List<GithubRepoEntity>> call(Response<List<GithubRepoEntity>> response) {
                        // TODO: 他にいい方法がありそう RxErrorHandlingCallAdapterFactory 側でなんとかしたい
                        if (!response.isSuccessful()) {
                            if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                                throw RetrofitException.unauthorizedError("", response, new HttpException(response), ModelLocator.getApiClient().mRetrofit);
                            }
                            // TODO: 401以外のエラー対応
                        }

                        mHeadersMapOfRepoList = response.headers().toMultimap();
                        if(mGitHubRepoEntityList != null && mGitHubRepoEntityList.size()> 0){
                            List<GithubRepoEntity> newList = new ArrayList<>(mGitHubRepoEntityList);
                            newList.addAll(response.body());
                            mGitHubRepoEntityList = newList;
                        } else {
                            mGitHubRepoEntityList = response.body();
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

    public Subscription searchCode(String keyword, String repo, Integer page, final Subscriber<List<ItemEntity>> subscriber) {
        if(!getKeyword().equals(keyword)) {
            clear();
        }

        mKeyword = keyword;
        // FIXME: least one repo or user
        mRepository = repo;
        keyword += "+repo:" + repo;

        Subscription subscription = ModelLocator.getApiClient().api.searchCode(keyword, page)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<Response<SearchCodeResultEntity>, Observable<List<ItemEntity>>>() {
                    @Override
                    public Observable<List<ItemEntity>> call(Response<SearchCodeResultEntity> searchResultEntity) {
                        mHeadersMapOfSearchCode = searchResultEntity.headers().toMultimap();
                        mSearchCodeResultEntity = searchResultEntity.body();
                        if(mItemEntityList != null && mItemEntityList.size() > 0) {
                            List<ItemEntity> newList = new ArrayList<>(mItemEntityList);
                            newList.addAll(mSearchCodeResultEntity.getItemEntityList());
                            mItemEntityList = newList;
                        } else {
                            mItemEntityList = searchResultEntity.body().getItemEntityList();
                        }
                        return Observable.just(mItemEntityList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        return subscription;
    }

    public Subscription searchRepositories(String keyword, Integer page, final Subscriber<List<GithubRepoEntity>> subscriber) {
        if(!getKeyword().equals(keyword)) {
            clear();
        }

        mKeyword = keyword;
        Subscription subscription = ModelLocator.getApiClient().api.searchRepositories(keyword, page)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<Response<SearchRepositoryResultEntity>, Observable<List<GithubRepoEntity>>>() {
                    @Override
                    public Observable<List<GithubRepoEntity>> call(Response<SearchRepositoryResultEntity> response) {
                        mHeadersMapOfSearchCode = response.headers().toMultimap();
                        mSearchRepositoryResultEntity = response.body();
                        if(mRepositoryResultList != null && mRepositoryResultList.size() > 0) {
                            List<GithubRepoEntity> newList = new ArrayList<>(mRepositoryResultList);
                            newList.addAll(mSearchRepositoryResultEntity.getGithubRepoEntityList());
                            mRepositoryResultList = newList;
                        } else {
                            mRepositoryResultList = response.body().getGithubRepoEntityList();
                        }
                        return Observable.just(mRepositoryResultList);
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

    public String getRepository() {
        if(mRepository == null) { return ""; }
        return mRepository;
    }

    public int getLastPage() {
        return extractLink(mHeadersMapOfRepoList, "last");
    }
    public int getNextPageOfRepoList() {
        return extractLink(mHeadersMapOfRepoList ,"next");
    }

    public int getNextPageOfSearchCode() {
        return extractLink(mHeadersMapOfSearchCode ,"next");
    }

    private int extractLink(Map<String, List<String>> headerMap, String rel) {
        if (headerMap == null || !headerMap.containsKey("link")) {
            return 0;
        }
        List<String> values = headerMap.get("link");
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

    public boolean hasNextPageOfRepoList() {
        return  getNextPageOfRepoList() > 0;
    }
    public boolean hasNextPageOfSearchCode() {
        return  getNextPageOfSearchCode() > 0;
    }
}
