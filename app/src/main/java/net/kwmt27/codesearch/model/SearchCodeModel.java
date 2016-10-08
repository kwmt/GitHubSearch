package net.kwmt27.codesearch.model;

import net.kwmt27.codesearch.entity.ItemEntity;
import net.kwmt27.codesearch.model.rx.ReusableCompositeSubscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static net.kwmt27.codesearch.util.GitHubHeaderUtil.extractLink;

public class SearchCodeModel extends BaseModel implements ISearchModel {

    private List<ItemEntity> mItemEntityList = new ArrayList<>();
    private Map<String, List<String>> mHeadersMap;

    private String mKeyword;
    /** account-name/repository-nameの形式のこと */
    private String mRepositoryFullName;

    private ReusableCompositeSubscription mCompositeSubscription = new ReusableCompositeSubscription();


    public SearchCodeModel() {
        super();
    }

    public SearchCodeModel(ApiClient apiClient) {
        super(apiClient);
    }


    public void unsubscribe() {
        if(mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void clear() {
        mItemEntityList = new ArrayList<>();
    }



    public Subscription searchCode(String keyword, String repositoryFullName, Integer page, final Subscriber<List<ItemEntity>> subscriber) {
        mKeyword = keyword;
        mRepositoryFullName = repositoryFullName;
        keyword += "+repo:" + repositoryFullName;

        Subscription subscription = mApiClient.api.searchCode(keyword, page)
                .subscribeOn(Schedulers.newThread())
                .flatMap(response -> {
                    mHeadersMap = response.headers().toMultimap();
                    if (mItemEntityList != null && mItemEntityList.size() > 0) {
                        List<ItemEntity> newList = new ArrayList<>(mItemEntityList);
                        newList.addAll(response.body().getItemEntityList());
                        mItemEntityList = newList;
                    } else {
                        mItemEntityList = response.body().getItemEntityList();
                    }
                    return Observable.just(mItemEntityList);
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

    public String getRepositoryFullName() {
        if(mRepositoryFullName == null) { return ""; }
        return mRepositoryFullName;
    }

    @Override
    public int getNextPage() {
        return extractLink(mHeadersMap,"next");
    }

    @Override
    public boolean hasNextPage() {
        return  getNextPage() > 0;
    }
}
