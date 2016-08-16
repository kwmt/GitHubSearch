package net.kwmt27.codesearch.entity;

import com.google.gson.annotations.SerializedName;

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
