package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.CommentEntity;
import net.kwmt27.codesearch.entity.IssueEntity;

public class IssueCommentEntity {
    @SerializedName("action")
    private String mAction;
    @SerializedName("issue")
    private IssueEntity mIssueEntity;
    @SerializedName("comment")
    private CommentEntity mCommentEntity;

    public String getAction() {
        return mAction;
    }

    public IssueEntity getIssueEntity() {
        return mIssueEntity;
    }

    public CommentEntity getCommentEntity() {
        return mCommentEntity;
    }
}
