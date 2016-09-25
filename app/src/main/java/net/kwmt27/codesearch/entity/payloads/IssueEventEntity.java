package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.IssueEntity;

public class IssueEventEntity {
    @SerializedName("action")
    private int mAction;
    @SerializedName("issue")
    private IssueEntity mIssueEntity;

}
