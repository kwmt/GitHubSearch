package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

public class PageEntity {

    @SerializedName("page_name")
    private String mPageName;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("action")
    private String mAction;
    @SerializedName("sha")
    private String mSha;
    @SerializedName("html_url")
    private String mHtmlUrl;

    public String getPageName() {
        return mPageName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAction() {
        return mAction;
    }

    public String getSha() {
        return mSha;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }
}
