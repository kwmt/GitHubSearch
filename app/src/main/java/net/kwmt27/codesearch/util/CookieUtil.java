package net.kwmt27.codesearch.util;

import android.text.TextUtils;
import android.webkit.CookieManager;

import net.kwmt27.codesearch.BuildConfig;

import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    public static void log() {
        String cookieStr = getCookie();
        if (cookieStr == null) {
            Logger.d("cookieはありません");
            return;
        }
        String[] cookies = cookieStr.split(";");
        for (String cookie : cookies) {
            Logger.d(cookie);
        }
    }


    /**
     * 指定したキーのクッキー値を返す。キーが無ければ、空文字列を返す。
     */
    public static String getCookieValue(String key) {
        Map<String, String> map = getCookieMap();
        return map.containsKey(key) ? map.get(key) : "";

    }

    /**
     * クッキーのマップ(key,value)を返す
     */
    private static Map<String, String> getCookieMap() {
        String cookieStr = getCookie();

        HashMap<String, String> cookieMap = new HashMap<>();
        if (TextUtils.isEmpty(cookieStr)) {
            return cookieMap;
        }

        String[] cookies = cookieStr.split(";");

        for (String cookie : cookies) {
            String[] keyValue = cookie.split("=");
            cookieMap.put(keyValue[0].trim(), keyValue[1].trim());
        }
        return cookieMap;
    }

    private static String getCookie() {
        return CookieManager.getInstance().getCookie(BuildConfig.BASE_WEBVIEW_URL);
    }


}
