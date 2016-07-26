package net.kwmt27.githubviewer.entity;

import com.google.gson.annotations.SerializedName;

public class GithubResponse {
    @SerializedName("current_user_url")
    private String mCurrentUserUrl;

    public String getCurrentUserUrl() {
        return mCurrentUserUrl;
    }
}
