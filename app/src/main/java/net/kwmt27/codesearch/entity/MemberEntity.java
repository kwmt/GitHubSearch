package net.kwmt27.codesearch.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberEntity implements Serializable {
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

    public int getId() {
        return mId;
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
