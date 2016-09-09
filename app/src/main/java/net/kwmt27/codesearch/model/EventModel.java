package net.kwmt27.codesearch.model;

import net.kwmt27.codesearch.entity.EventEntity;
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

public class EventModel extends BaseModel implements Listable {

    private Map<String, List<String>> mHeadersMapOfRepoList;

    private ReusableCompositeSubscription mCompositeSubscription = new ReusableCompositeSubscription();

    private List<EventEntity> mEventList = new ArrayList<>();


    public void unsubscribe() {
        if(mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void clear() {
        mEventList = new ArrayList<>();
    }

    public Subscription fetchEvent(String user, Integer page, final Subscriber<List<EventEntity>> subscriber) {
        Subscription subscription = mApiClient.api.fetchEvent(user, page)
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
                    if(mEventList != null && mEventList.size()> 0){
                        List<EventEntity> newList = new ArrayList<>(mEventList);
                        newList.addAll(response.body());
                        mEventList = newList;
                    } else {
                        mEventList = response.body();
                    }
                    return Observable.just(mEventList);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        return subscription;
    }



    @Override
    public int getNextPage() {
        return extractLink(mHeadersMapOfRepoList ,"next");
    }

    @Override
    public boolean hasNextPage() {
        return  getNextPage() > 0;
    }


    public List<EventEntity> getEventList() {
        return mEventList;
    }

    public boolean hasEventList() {
        return mEventList.size() > 0;
    }
}
