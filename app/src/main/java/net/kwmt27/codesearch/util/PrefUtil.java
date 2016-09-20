package net.kwmt27.codesearch.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.kwmt27.codesearch.App;

/**
 * プレファレンスユーティリティ
 */
public class PrefUtil {

    /** アクセストークンのプレファレンスキー */
    private static final String PREF_ACCESS_TOKEN = "pref_access_token";
    private static final String PREF_GITHUB_LOGIN_ID = "pref_github_login_id";

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
     * Github Login Idをプレファレンスから取得する。
     * @param context
     * @return
     */
    public static String getAccessToken(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ACCESS_TOKEN, null);
    }


    public static void setGithubLoginId(final Context context, final String loginId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_GITHUB_LOGIN_ID, loginId).apply();
    }

    /**
     * Github Login Idをプレファレンスから取得する。
     */
    public static String getGithubLoginId() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getInstance().getApplicationContext());
        return sp.getString(PREF_GITHUB_LOGIN_ID, "");
    }

}