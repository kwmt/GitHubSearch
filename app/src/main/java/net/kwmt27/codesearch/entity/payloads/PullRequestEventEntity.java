package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;
import net.kwmt27.codesearch.entity.PullRequestEntity;

public class PullRequestEventEntity {
    // TODO
    @SerializedName("action")
    private String mAction;
    @SerializedName("number")
    private int mNumber;
    @SerializedName("pull_request")
    private PullRequestEntity mPullRequestEntity;

    public String getAction() {
        return mAction;
    }

    public int getNumber() {
        return mNumber;
    }

    public PullRequestEntity getPullRequestEntity() {
        return mPullRequestEntity;
    }
}
