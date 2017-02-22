package net.kwmt27.codesearch.entity;

import com.google.gson.annotations.SerializedName;

public class PullRequestEntity {
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("body")
    private String mBody;
    @SerializedName("number")
    private int mNumber;
    @SerializedName("title")
    private String mTitle;


    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getBody() {
        return mBody;
    }

    public int getNumber() {
        return mNumber;
    }

    public String getTitle() {
        return mTitle;
    }
}
