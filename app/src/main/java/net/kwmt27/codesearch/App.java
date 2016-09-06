package net.kwmt27.codesearch;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.squareup.leakcanary.LeakCanary;

public class App extends Application {

    private static App sInstance;
    private static ModelLocator sModelLocator;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        sInstance = this;
        MobileAds.initialize(getApplicationContext(), BuildConfig.ADMOB_APP_ID);


        sModelLocator = new ModelLocator();
    }

    public static App getInstance() {
        return sInstance;
    }

    public static ModelLocator getModelLocator() {
        return sModelLocator;
    }
}
