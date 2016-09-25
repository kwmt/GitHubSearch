package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

public class CommentEntity {
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("body")
    private String mBody;
    @SerializedName("path")
    private String mPath;

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getBody() {
        return mBody;
    }

    public String getPath() {
        return mPath;
    }
}
