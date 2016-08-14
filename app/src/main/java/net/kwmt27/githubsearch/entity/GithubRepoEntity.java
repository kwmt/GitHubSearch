package net.kwmt27.githubsearch.entity;

import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class GithubRepoEntity implements Serializable{
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("full_name")
    private String mFullName;
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
    @SerializedName("pushed_at")
    private Date mPushedAt;
    @SerializedName("stargazers_count")
    private int mStargazersCount;

    private ItemType mItemType;

    public GithubRepoEntity(ItemType type) {
        mItemType = type;
    }

    public String getName() {
        return mName;
    }

    public String getFullName() {
        return mFullName;
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

    public Date getPushedAt() {
        return mPushedAt;
    }

    public String getFormattedPushedAt() {
        if(mPushedAt == null) { return "not yet pushed"; }
        return DateFormat.format("yyyy/MM/dd", mPushedAt).toString();
    }

    public String getStargazersCount() {
        return String.valueOf(mStargazersCount);
    }

    @Nullable
    public ItemType getItemType() {
        return mItemType;
    }
}
