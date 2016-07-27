package net.kwmt27.githubviewer.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GithubRepoEntity implements Serializable{
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("owner")
    private OwnerEntity mOwner;
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("language")
    private String mLanguage;
    @SerializedName("stargazers_count")
    private int mStargazersCount;

    public GithubRepoEntity(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public OwnerEntity getOwner() {
        return mOwner;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getStargazersCount() {
        return String.valueOf(mStargazersCount);
    }
}
