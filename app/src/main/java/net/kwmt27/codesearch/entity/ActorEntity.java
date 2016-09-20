package net.kwmt27.codesearch.entity;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.BuildConfig;

import java.io.Serializable;

public class ActorEntity implements Serializable {
    @SerializedName("id")
    private int mId;
    @SerializedName("login")
    private String mLogin;
    @SerializedName("display_login")
    private String mDisplayLogin;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("avatar_url")
    private String mAvatarUrl;

    private String mHtmlUrl;

    public ActorEntity(String name) {
        mLogin = name;
        mDisplayLogin = name;
        mUrl = BuildConfig.BASE_API_URL + "/users/" + mLogin;
        mHtmlUrl = BuildConfig.BASE_WEBVIEW_URL + "/" + mLogin;;
    }

    public String getLogin() {
        return mLogin;
    }

    public String getDisplayLogin() {
        return mDisplayLogin;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

}
