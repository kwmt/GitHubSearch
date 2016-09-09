package net.kwmt27.codesearch.entity.events;


import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.CommentEntity;

// https://developer.github.com/v3/repos/comments/#list-commit-comments-for-a-repository
public class CommentEvent extends EventEntity {
    @SerializedName("payload")
    private CommentEntity mCommentEntity;

}
