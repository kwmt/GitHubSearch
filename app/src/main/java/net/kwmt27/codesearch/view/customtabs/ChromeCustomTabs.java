package net.kwmt27.codesearch.view.customtabs;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.libs.customtabsclient.CustomTabsHelper;
import net.kwmt27.codesearch.presenter.search.ISearchPresenter;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.view.search.SearchActivity;


public class ChromeCustomTabs {

    private static CustomTabsSession sCustomTabsSession;
    private static CustomTabsClient sClient;
    private static CustomTabsServiceConnection sConnection;

    public static void open(Activity activity, String url, GithubRepoEntity githubRepoEntity, boolean canSearchCode) {
        Context context = activity.getApplicationContext();
        final PendingIntent pendingIntentSearch = createSearchPendingIntent(activity, githubRepoEntity, canSearchCode, context);
        final PendingIntent pendingIntentTwitter = createTwitterPendingIntent(url, context);

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(sCustomTabsSession)
                .setToolbarColor(ContextCompat.getColor(context.getApplicationContext(), android.R.color.white))
                .setShowTitle(true)
                .addMenuItem("Share...", pendingIntentTwitter);
        if(canSearchCode) {
            final Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_search_black_24dp);
            builder.setActionButton(icon, context.getString(R.string.search), pendingIntentSearch);
        }
        builder.build().launchUrl(activity, (Uri.parse(url)));
    }

    private static PendingIntent createSearchPendingIntent(Activity activity, GithubRepoEntity githubRepoEntity, boolean canSearchCode, Context context) {
        Intent intentSearch = new Intent(activity, SearchActivity.class);
        intentSearch.putExtra(ISearchPresenter.CAN_SEARCH_CODE, canSearchCode);
        if (githubRepoEntity != null) {
            intentSearch.putExtra(ISearchPresenter.REPO_ENTITY_KEY, githubRepoEntity);
        }
        return PendingIntent.getActivity(context, 0, intentSearch, 0);
    }

    private static PendingIntent createTwitterPendingIntent(String url, Context context) {
        final Intent intentTwitter = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, url);
        return PendingIntent.getActivity(context, 0, intentTwitter, 0);
    }

    public static boolean bindService(Activity activity) {
        if (sClient != null) return false;

        // 接続先はChromeのバージョンによって異なる
        // "com.android.chrome", "com.chrome.beta",
        // "com.chrome.dev",  "com.google.android.apps.chrome"
        String packageNameToBind = CustomTabsHelper.getPackageNameToUse(activity);
        if (packageNameToBind == null) {
            return false;
        }

        // ブラウザ側と接続したときの処理
        sConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name,
                                                     CustomTabsClient client) {
                // セッションを確立する
                sClient = client;
                sClient.warmup(0L);
                sCustomTabsSession = sClient.newSession(new CustomTabsCallback() {
                    @Override
                    public void onNavigationEvent(int navigationEvent, Bundle extras) {
                        Logger.w("onNavigationEvent: Code = " + navigationEvent);
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                sClient = null;
            }
        };

        return CustomTabsClient.bindCustomTabsService(activity,
                packageNameToBind,
                sConnection);
    }

    public static void unbind(Activity activity) {
        if (sConnection == null) return;
        activity.unbindService(sConnection);
        sClient = null;
        sCustomTabsSession = null;
    }
}
