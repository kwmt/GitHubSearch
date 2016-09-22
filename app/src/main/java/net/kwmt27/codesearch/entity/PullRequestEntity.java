package net.kwmt27.codesearch.entity;

import android.text.format.DateFormat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class PullRequestEntity implements Serializable{
    @SerializedName("id")
    private int mId;
    @SerializedName("user")
    private UserEntity mUser;
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("state")
    private String mState;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("body")
    private String mBody;
    @SerializedName("created_at")
    private Date mCratedAt;
    @SerializedName("updated_at")
    private Date mUpdatedAt;


//       public PullRequestEntity(String name, String ownerLogin) {
//        mUser = new UserEntity(ownerLogin);
//        mFullName = ownerLogin + "/" + name;
//        mHtmlUrl = BuildConfig.BASE_WEBVIEW_URL + "/" + mFullName;
//        mUrl = BuildConfig.BASE_API_URL + "/repos/" + mFullName;
//    }


    public int getId() {
        return mId;
    }

    public UserEntity getUser() {
        return mUser;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getState() {
        return mState;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public Date getCratedAt() {
        return mCratedAt;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public String getFormattedCratedAt() {
        if(mCratedAt == null) { return "not yet pushed"; }
        return DateFormat.format("yyyy/MM/dd", mCratedAt).toString();
    }


}
