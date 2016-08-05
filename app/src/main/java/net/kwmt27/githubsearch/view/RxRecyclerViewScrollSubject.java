package net.kwmt27.githubsearch.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

public class RxRecyclerViewScrollSubject {
    Subject<RecyclerViewScrollEvent, RecyclerViewScrollEvent> mSubject = PublishSubject.create();
    Subscription mSubscription = Subscriptions.empty();

    public Observable<RecyclerViewScrollEvent> observable() {
        return mSubject;
    }

    public void start(RecyclerView recyclerView, final LinearLayoutManager linearLayoutManager) {
        mSubscription.unsubscribe();
        mSubscription = RxRecyclerView.scrollEvents(recyclerView).subscribe(new Action1<RecyclerViewScrollEvent>() {
            @Override
            public void call(RecyclerViewScrollEvent event) {
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (totalItemCount - 1 <= lastVisibleItemPosition) {
                    mSubject.onNext(event);
                }
            }
        });
    }

    public void stop() {
        mSubscription.unsubscribe();
    }
}
