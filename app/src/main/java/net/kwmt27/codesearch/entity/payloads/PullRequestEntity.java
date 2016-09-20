package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

public class PullRequestEntity {
    // TODO
    @SerializedName("action")
    private String mAction;
    @SerializedName("number")
    private int mNumber;

    public String getAction() {
        return mAction;
    }

    public int getNumber() {
        return mNumber;
    }
}
