package net.kwmt27.githubsearch.model.rx;

import android.support.annotation.NonNull;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 *
 * http://gfx.hatenablog.com/entry/2015/06/08/091656
 */
public class ReusableCompositeSubscription {
    CompositeSubscription compositeSubscription;

    public void add(@NonNull Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    public void unsubscribe() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
    }

}
