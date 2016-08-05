package net.kwmt27.githubsearch;

import android.app.Application;

public class App extends Application {

    private static App sInstance;
    private static ModelLocator sModelLocator;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        sModelLocator = new ModelLocator();
    }

    public static App getInstance() {
        return sInstance;
    }

}
