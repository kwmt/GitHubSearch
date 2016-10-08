package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.CommentEntity;
import net.kwmt27.codesearch.entity.PullRequestEntity;

public class PullRequestReviewCommentEntity {
    @SerializedName("action")
    private String mAction;
    @SerializedName("pull_request")
    private PullRequestEntity mPullRequestEntity;
    @SerializedName("comment")
    private CommentEntity mCommentEntity;

    public String getAction() {
        return mAction;
    }

    public PullRequestEntity getPullRequestEntity() {
        return mPullRequestEntity;
    }

    public CommentEntity getCommentEntity() {
        return mCommentEntity;
    }
}
