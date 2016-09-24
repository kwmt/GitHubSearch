package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

public class GistEntity {

    @SerializedName("html_url")
    private String mHtmlUrl;

    public String getHtmlUrl() {
        return mHtmlUrl;
    }
}
