package net.kwmt27.githubsearch.model;

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.entity.ItemEntity;
import net.kwmt27.githubsearch.entity.SearchCodeResultEntity;
import net.kwmt27.githubsearch.model.rx.ReusableCompositeSubscription;

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

import static net.kwmt27.githubsearch.util.GitHubHeaderUtil.extractLink;

public class SearchCodeModel implements ISearchModel {

    private List<ItemEntity> mItemEntityList = new ArrayList<>();
    private Map<String, List<String>> mHeadersMap;

    private String mKeyword;
    private String mRepository;

    private ReusableCompositeSubscription mCompositeSubscription = new ReusableCompositeSubscription();

    public void unsubscribe() {
        if(mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void clear() {
        mItemEntityList = new ArrayList<>();
    }



    public Subscription searchCode(String keyword, String repo, Integer page, final Subscriber<List<ItemEntity>> subscriber) {
        mKeyword = keyword;
        mRepository = repo;
        keyword += "+repo:" + repo;

        Subscription subscription = ModelLocator.getApiClient().api.searchCode(keyword, page)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<Response<SearchCodeResultEntity>, Observable<List<ItemEntity>>>() {
                    @Override
                    public Observable<List<ItemEntity>> call(Response<SearchCodeResultEntity> response) {
                        mHeadersMap = response.headers().toMultimap();
                        if(mItemEntityList != null && mItemEntityList.size() > 0) {
                            List<ItemEntity> newList = new ArrayList<>(mItemEntityList);
                            newList.addAll(response.body().getItemEntityList());
                            mItemEntityList = newList;
                        } else {
                            mItemEntityList = response.body().getItemEntityList();
                        }
                        return Observable.just(mItemEntityList);
                    }
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

    public String getRepository() {
        if(mRepository == null) { return ""; }
        return mRepository;
    }

    public int getNextPage() {
        return extractLink(mHeadersMap,"next");
    }

    public boolean hasNextPage() {
        return  getNextPage() > 0;
    }
}
