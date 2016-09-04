package net.kwmt27.codesearch.model;

import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.model.rx.RetrofitException;
import net.kwmt27.codesearch.model.rx.ReusableCompositeSubscription;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static net.kwmt27.codesearch.util.GitHubHeaderUtil.extractLink;

public class SearchRepositoryModel extends BaseModel implements ISearchModel {

    private Map<String, List<String>> mHeadersMapOfRepoList;

    private String mKeyword;

    private ReusableCompositeSubscription mCompositeSubscription = new ReusableCompositeSubscription();
    private List<GithubRepoEntity> mRepositoryResultList;

    private List<GithubRepoEntity> mGitHubRepoEntityList = new ArrayList<>();


    public void unsubscribe() {
        if(mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void clear() {
        mRepositoryResultList = new ArrayList<>();
        mGitHubRepoEntityList = new ArrayList<>();
    }

    public Subscription fetchUserRepository(Integer page, final Subscriber<List<GithubRepoEntity>> subscriber) {
        Subscription listReposSubscription = mApiClient.api.fetchUserRepository(page)
                .subscribeOn(Schedulers.newThread())
                .flatMap(response -> {
                    // TODO: 他にいい方法がありそう RxErrorHandlingCallAdapterFactory 側でなんとかしたい
                    if (!response.isSuccessful()) {
                        if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            throw RetrofitException.unauthorizedError("", response, new HttpException(response), mApiClient.mRetrofit);
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
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(listReposSubscription);
        return listReposSubscription;
    }




    public Subscription searchRepositories(String keyword, Integer page, final Subscriber<List<GithubRepoEntity>> subscriber) {
        mKeyword = keyword;
        Subscription subscription = mApiClient.api.searchRepositories(keyword, page)
                .subscribeOn(Schedulers.newThread())
                .flatMap(response -> {
                    mHeadersMapOfRepoList = response.headers().toMultimap();
                    if(mRepositoryResultList != null && mRepositoryResultList.size() > 0) {
                        List<GithubRepoEntity> newList = new ArrayList<>(mRepositoryResultList);
                        newList.addAll(response.body().getGithubRepoEntityList());
                        mRepositoryResultList = newList;
                    } else {
                        mRepositoryResultList = response.body().getGithubRepoEntityList();
                    }
                    return Observable.just(mRepositoryResultList);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        return subscription;
    }

    @Override
    public String getKeyword() {
        if(mKeyword == null) { return ""; }
        return mKeyword;
    }

    @Override
    public int getNextPage() {
        return extractLink(mHeadersMapOfRepoList ,"next");
    }

    @Override
    public boolean hasNextPage() {
        return  getNextPage() > 0;
    }
}
