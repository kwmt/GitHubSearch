package net.kwmt27.codesearch.model;

import android.content.Context;

import net.kwmt27.codesearch.BuildConfig;
import net.kwmt27.codesearch.entity.TokenEntity;
import net.kwmt27.codesearch.model.rx.ReusableCompositeSubscription;
import net.kwmt27.codesearch.util.PrefUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginModel extends BaseModel {

    private static final String OAUTH_URL = "https://github.com/login/oauth/authorize";
    private static final String SCOPE = "read:org";

    private ReusableCompositeSubscription mCompositeSubscription = new ReusableCompositeSubscription();
    private Context mContext;

    public LoginModel(Context context) {
        mContext = context.getApplicationContext();
    }

    public Subscription fetchAccessToken(String code, Subscriber<TokenEntity> subscriber) {
        Subscription subscription = mApiClient.login.fetchAccessToken(code, BuildConfig.GITHUB_CLIENT_ID, BuildConfig.GITHUB_CLIENT_SECRET)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        return subscription;
    }

    public String getAccessToken() {
        String accessToken = PrefUtil.getAccessToken(mContext);
        if (accessToken == null) {
            return "";
        }
        return accessToken;
    }

    public String getAuthorizeUrl() {
        return OAUTH_URL  + "?client_id=" + BuildConfig.GITHUB_CLIENT_ID + "&scope=" + SCOPE;
    }
}
