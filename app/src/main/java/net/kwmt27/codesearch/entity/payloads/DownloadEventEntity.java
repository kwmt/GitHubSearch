package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

/**
 * @deprecated Downloads API is Deprecated https://developer.github.com/v3/repos/downloads/
 */
@Deprecated
public class DownloadEventEntity {
    @SerializedName("download")
    private DownloadEntity mDownload;


    public DownloadEntity getDownload() {
        if(mDownload == null) { return new DownloadEntity(); }
        return mDownload;
    }
}
