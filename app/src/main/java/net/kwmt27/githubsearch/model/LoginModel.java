package net.kwmt27.githubsearch.model;

import net.kwmt27.githubsearch.BuildConfig;
import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.entity.TokenEntity;
import net.kwmt27.githubsearch.model.rx.ReusableCompositeSubscription;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginModel {

    private static final String OAUTH_URL = "https://github.com/login/oauth/authorize";
    private static final String SCOPE = "repo";

    private ReusableCompositeSubscription mCompositeSubscription = new ReusableCompositeSubscription();
    private TokenEntity mTokenEntity;

    public Subscription fetchAccessToken(String code, Subscriber<TokenEntity> subscriber) {
        Subscription subscription = ModelLocator.getApiClient().login.fetchAccessToken(code, BuildConfig.GITHUB_CLIENT_ID, BuildConfig.GITHUB_CLIENT_SECRET)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<Response<ResponseBody>, Observable<TokenEntity>>() {
                    @Override
                    public Observable<TokenEntity> call(Response<ResponseBody> response) {
                        try {
                            Map<String, String> map = keyValueMap(response.body().string());
                            mTokenEntity = new TokenEntity()
                                    .setAccessToken(map.get("access_token"))
                                    .setScope(map.get("scope"))
                                    .setTokenType(map.get("token_type"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.just(mTokenEntity);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        return subscription;
    }

    public String getAccessToken() {
        if (mTokenEntity == null || ModelLocator.getDbModel().getToken() == null) {
            return "";
        }
        if (mTokenEntity == null) {
            mTokenEntity = ModelLocator.getDbModel().getToken();
        }
        return mTokenEntity.getAccessToken();
    }
    // TODO: レスポンスをJSONに変更できるので変更する
    private Map<String, String> keyValueMap(String str) {
        Map<String, String> result = new HashMap<>();
        String[] array = str.split("&");
        for (String arr : array) {
            String[] kv = arr.split("=");
            if (kv.length == 2) {
                result.put(kv[0], kv[1]);
            }
        }
        return result;
    }


    public String getAuthorizeUrl() {
        return OAUTH_URL  + "?client_id=" + BuildConfig.GITHUB_CLIENT_ID + "&scope=" + SCOPE;
    }
}
