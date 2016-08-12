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
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");


        sModelLocator = new ModelLocator();
    }

    public static App getInstance() {
        return sInstance;
    }

}
