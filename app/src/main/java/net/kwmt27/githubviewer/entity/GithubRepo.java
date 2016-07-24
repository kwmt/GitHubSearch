package net.kwmt27.githubviewer.entity;

import com.google.gson.annotations.SerializedName;

public class GithubRepo {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;

    public String getName() {
        return mName;
    }
}
