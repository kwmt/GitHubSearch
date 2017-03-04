package net.kwmt27.codesearch.model.rx;

import android.content.Context;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.error.ApiErrorEntity;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.util.NetworkUtil;
import net.kwmt27.codesearch.util.ToastUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

public abstract class ApiSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    public ApiSubscriber(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void onError(Throwable throwable) {
        if(throwable == null) {
            Logger.e("NullPointerException: throwable is null.");
            ToastUtil.show(mContext, mContext.getString(R.string.load_failed));
            return;
        }

        RetrofitException error = (RetrofitException)throwable;
        Throwable cause = error.getCause();
        if (NetworkUtil.showAlertIfOffline(mContext)) {
            return;
        }
        if (error.getKind() == RetrofitException.Kind.UNAUTHORIZED) {
            Logger.e("UNAUTHORIZED:" + throwable);
            ToastUtil.show(mContext, mContext.getString(R.string.auth_error));
            return;
        }
        if (cause instanceof UnknownHostException) {
            Logger.e("UnknownHostException:" + throwable);
            ToastUtil.show(mContext, mContext.getString(R.string.host_not_found));
            return;
        }
        if (cause instanceof SocketTimeoutException) {
            Logger.e("SocketTimeoutException:" + throwable);
            ToastUtil.show(mContext, mContext.getString(R.string.network_connection_timed_out));
            return;
        }
        if (cause instanceof IOException) {
            Logger.e("IOException:" + throwable);
            ToastUtil.show(mContext, mContext.getString(R.string.could_not_connect_to_the_network));
            return;
        }
        if (cause instanceof JSONException) {
            Logger.e("JSONException:" + throwable);
            ToastUtil.show(mContext, mContext.getString(R.string.load_failed));
            return;
        }
        try {
            ApiErrorEntity apiErrorEntity = error.getErrorBodyAs(ApiErrorEntity.class);
            if (apiErrorEntity != null){
                ToastUtil.show(mContext,apiErrorEntity.getMessage());
            }
        } catch (IOException e) {
            Logger.e(e);
        }

        Logger.e("Exception:" + throwable);
        //ToastUtil.show(mContext, "Exception:" + e);
    }

}
