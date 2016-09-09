package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.events.CommentEvent;

public class CommitCommentEntity {

    @SerializedName("comment")
    private CommentEvent mComment;
}
