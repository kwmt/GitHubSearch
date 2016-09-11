package net.kwmt27.codesearch.entity;

import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.BuildConfig;

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

    public GithubRepoEntity(String name, String ownerLogin) {
        mName = name;
        mOwner = new OwnerEntity(ownerLogin);
        mFullName = ownerLogin + "/" + name;
        mHtmlUrl = BuildConfig.BASE_WEBVIEW_URL + "/" + mFullName;
        mUrl = BuildConfig.BASE_API_URL + "/repos/" + mFullName;
    }

    public String getName() {
        return mName;
    }

    public String getFullName() {
        if(mFullName == null) {
            if(mName != null && mName.contains("/")) {
                return mName;
            }
        }
        return mFullName;
    }

    public OwnerEntity getOwner() {
        return mOwner;
    }

    public String getHtmlUrl() {
        if(mHtmlUrl == null) {
            if(mFullName != null) {
                return BuildConfig.BASE_WEBVIEW_URL + "/" + mFullName;
            }
            if(mName != null && mName.contains("/")) {
                return BuildConfig.BASE_WEBVIEW_URL + "/" + mName;
            }
        }
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
