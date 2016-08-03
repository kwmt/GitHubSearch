package net.kwmt27.githubviewer.model.rx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
    public static Gson create() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
    }
}
