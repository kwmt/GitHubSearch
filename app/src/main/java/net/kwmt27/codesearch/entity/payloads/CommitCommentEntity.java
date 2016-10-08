package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.CommentEntity;
import net.kwmt27.codesearch.entity.GithubRepoEntity;

public class CommitCommentEntity {

    @SerializedName("action")
    private String mAction;
    @SerializedName("comment")
    private CommentEntity mComment;
    @SerializedName("repository")
    private GithubRepoEntity mRepository;

    // TODO sender


    public String getAction() {
        if(mAction == null) { return ""; }
        return mAction;
    }

    public CommentEntity getComment() {
        return mComment;
    }

    public GithubRepoEntity getRepository() {
        return mRepository;
    }
}
