package net.kwmt27.codesearch.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import net.kwmt27.codesearch.R;

public class AnalyticsManager {

    private static AnalyticsManager sAnalyticsManager;

    private FirebaseAnalytics mFirebaseAnalytics;
    private static Tracker tracker;

    public static class Param {
        public static class Screen {
            public static final String MAIN = "main";
            public static final String TOP = "top";
            public static final String REPOSITORY_LIST = "repository_list";
            public static final String EVENT_LIST = "event_list";
            public static final String DETAIL = "detail";
            public static final String SEARCH_REPOSITORY_RESULT_LIST = "search_repository_result_list";
            public static final String SEARCH_CODE_RESULT_LIST = "search_code_result_list";
            public static final String SEARCH = "search";
            public static final String NOT_FOUND_PAGE = "not_found_page";
            public static final String ERROR_PAGE = "error_page";
        }

        public static class Widget {
            public static final String SEARCH_BUTTON = "search_button";
            public static final String LOGIN_WITH_GITHUB_BUTTON = "login_with_github_button";
            public static final String CLEAR = "clear";
            public static final String SEARCH = "search";
            public static final String RELOAD_BUTTON = "reload_button";
            public static final String CLOSE = "close";
            public static final String BACK = "back";
            public static final String TIMELINE_TAB = "timeline_tab";
            public static final String REPOSITORY_TAB = "repository_tab";
        }

        public static class Category {
            public static final String Ads = "Ads";
            public static final String REPOSITORY = "repository";
            public static final String CODE = "code";
            public static final String ERROR_PAGE = "error";
        }

        public static class Action {
            public static final String CLICK_LINK = "click link";
        }

    }


    public static AnalyticsManager getInstance(Context context) {
        if (sAnalyticsManager == null) {
            sAnalyticsManager = new AnalyticsManager(context);
        }
        return sAnalyticsManager;
    }

    public AnalyticsManager(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        googleAnalyticsSetup(context);
    }

    public void sendScreen(String screenName) {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "screen");
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, screenName);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, params);

        sendScreenName(screenName);
    }

    public void sendClickButton(String screenName, String target) {
        Bundle params = new Bundle();
        params.putString("click", screenName);
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, target);
        mFirebaseAnalytics.logEvent("click", params);

        sendEvent(FirebaseAnalytics.Param.ITEM_NAME, "click", target);
    }

    public void sendClickItem(String screenName, String category) {
        sendClickItem(screenName, category, null);
    }

    public void sendClickItem(String screenName, String category, String itemName) {
        Bundle params = new Bundle();
        params.putString("click", screenName);
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, category);
        if (itemName != null) {
            params.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        }
        mFirebaseAnalytics.logEvent("click", params);

        sendEvent(category, "click");

    }

    public void sendSearch(String screenName, String keyword) {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.SEARCH_TERM, keyword);
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, screenName);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, params);

        sendEvent(FirebaseAnalytics.Param.SEARCH_TERM, FirebaseAnalytics.Event.SEARCH, keyword);
    }

    private void googleAnalyticsSetup(Context context) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(context.getApplicationContext());
        tracker = analytics.newTracker(R.xml.tracker_config);
    }


    /**
     * スクリーン名を送信
     *
     * @param screenName
     */
    public static void sendScreenName(String screenName) {
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    public static void sendEvent(String category, String action) {
        sendEvent(category, action, null);
    }

    /**
     * イベントを送信
     *
     * @param category カテゴリ
     * @param action   アクション
     * @param label    ラベル(null可)
     */
    public static void sendEvent(String category, String action, String label) {
        HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder();
        builder.setCategory(category);
        builder.setAction(action);
        if (label != null) {
            builder.setLabel(label);
        }
        tracker.send(builder.build());
    }


}
