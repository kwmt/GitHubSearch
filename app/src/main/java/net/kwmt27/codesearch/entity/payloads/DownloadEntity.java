package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

/**
 * @deprecated Downloads API is Deprecated https://developer.github.com/v3/repos/downloads/
 */
@Deprecated
public class DownloadEntity {

    @SerializedName("name")
    private String mName;
    @SerializedName("html_url")
    private String mHtmlUrl;

    public String getName() {
        return mName;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }
}
