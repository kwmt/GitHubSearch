package net.kwmt27.githubsearch;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class App extends Application {

    private static App sInstance;
    private static ModelLocator sModelLocator;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        MobileAds.initialize(getApplicationContext(), BuildConfig.ADMOB_APP_ID);


        sModelLocator = new ModelLocator();
    }

    public static App getInstance() {
        return sInstance;
    }

}
