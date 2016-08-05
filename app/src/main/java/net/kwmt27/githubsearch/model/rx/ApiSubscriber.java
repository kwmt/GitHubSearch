package net.kwmt27.githubsearch.model.rx;

import android.content.Context;

import net.kwmt27.githubsearch.entity.error.ApiErrorEntity;
import net.kwmt27.githubsearch.util.Logger;
import net.kwmt27.githubsearch.util.NetworkUtil;
import net.kwmt27.githubsearch.util.ToastUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

public abstract class ApiSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    public ApiSubscriber(Context context) {
        mContext = context;
    }

    @Override
    public void onError(Throwable throwable) {

        RetrofitException error = (RetrofitException)throwable;
        Throwable cause = error.getCause();

        if (NetworkUtil.showAlertIfOffline(mContext)) {
            return;
        }
        if (cause instanceof UnknownHostException) {
            Logger.e("UnknownHostException:" + throwable);
            ToastUtil.show(mContext, "ホストが見つかりません。");
            return;
        }
        if (cause instanceof SocketTimeoutException) {
            Logger.e("SocketTimeoutException:" + throwable);
            ToastUtil.show(mContext, "ネットワーク接続がタイムアウトしました。");
            return;
        }
        if (cause instanceof IOException) {
            Logger.e("IOException:" + throwable);
            ToastUtil.show(mContext, "ネットワークに接続できませんでした。");
            return;
        }
        if (cause instanceof JSONException) {
            Logger.e("JSONException:" + throwable);
            ToastUtil.show(mContext, "データの取得に失敗しました。");
            return;
        }
        try {
            ApiErrorEntity apiErrorEntity = error.getErrorBodyAs(ApiErrorEntity.class);
            if (apiErrorEntity != null){
                Logger.d(apiErrorEntity.getMessage());
            }
        } catch (IOException e) {
            Logger.e(e);
        }

        Logger.e("Exception:" + throwable);
        //ToastUtil.show(mContext, "Exception:" + e);
    }

}
