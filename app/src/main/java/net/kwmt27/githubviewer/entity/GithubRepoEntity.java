package net.kwmt27.githubviewer.entity;

import com.google.gson.annotations.SerializedName;

public class GithubRepoEntity {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;

    public GithubRepoEntity(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
