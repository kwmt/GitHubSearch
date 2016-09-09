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

    public ActorEntity(String name) {
        mLogin = name;
        mDisplayLogin = BuildConfig.BASE_WEBVIEW_URL + "/" + mLogin;
        mUrl = BuildConfig.BASE_API_URL + "/users/" + mLogin;
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
}
