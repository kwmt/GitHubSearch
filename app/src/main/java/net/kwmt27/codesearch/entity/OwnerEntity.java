package net.kwmt27.codesearch.entity;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.BuildConfig;

import java.io.Serializable;

public class OwnerEntity implements Serializable {
    @SerializedName("id")
    private int mId;
    @SerializedName("login")
    private String mLogin;
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("url")
    private String mUrl;

    public OwnerEntity(String name) {
        mLogin = name;
        mHtmlUrl = BuildConfig.BASE_WEBVIEW_URL + "/" + mLogin;
        mUrl = BuildConfig.BASE_API_URL + "/users/" + mLogin;
    }

    public String getLogin() {
        return mLogin;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public String getUrl() {
        return mUrl;
    }

}
