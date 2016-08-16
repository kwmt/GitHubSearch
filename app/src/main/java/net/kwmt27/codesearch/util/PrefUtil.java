package net.kwmt27.codesearch.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * プレファレンスユーティリティ
 */
public class PrefUtil {

    /** アクセストークンのプレファレンスキー */
    private static final String PREF_ACCESS_TOKEN = "pref_access_token";

    /**
     * アクセストークンをプレファレンスにセットする。
     * @param context
     * @param accessToken
     */
    public static void setAccessToken(final Context context, final String accessToken) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_ACCESS_TOKEN, accessToken).apply();
    }

    /**
     * アクセストークンをプレファレンスから取得する。
     * @param context
     * @return
     */
    public static String getAccessToken(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ACCESS_TOKEN, null);
    }

}