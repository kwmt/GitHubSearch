package net.kwmt27.githubsearch.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import net.kwmt27.githubsearch.R;


/**
 * ネットワークに関するユーティリティ
 */
public class NetworkUtil {

    // 参考
    // (Android)Networkの通信状況をしっかり見る - Qiita
    // http://bit.ly/1EEG2m5

    /**
     * @return オンラインかどうかを返す
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * オフラインならメッセージを表示する
     *
     * @param context
     * @return オフラインならtrue
     */
    public static boolean showAlertIfOffline(Context context) {
        if(!NetworkUtil.isOnline(context)) {
            ToastUtil.show(context, context.getString(R.string.offline_message));
            Logger.i(context.getString(R.string.offline_message));
            return true;
        }
        return false;
    }
}
