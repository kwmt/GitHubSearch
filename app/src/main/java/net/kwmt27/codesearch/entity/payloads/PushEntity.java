package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

public class PushEntity {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mFullName;
    @SerializedName("url")
    private String mUrl;

    public int getId() {
        return mId;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getUrl() {
        return mUrl;
    }
}
