package net.kwmt27.codesearch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class EventEntity {
    // TODO: enumにしたい
    @SerializedName("type")
    private String mType;
    @SerializedName("public")
    private boolean mIsPublic;
    @SerializedName("actor")
    private ActorEntity mActor;

    @SerializedName("repo")
    private GithubRepoEntity mRepo;
    @SerializedName("created_at")
    private Date mCreatedAt;
    @SerializedName("id")
    private String mId;


    private ItemType mItemType;

    public ItemType getItemType() {
        return mItemType;
    }

    public EventEntity() {
    }

    public EventEntity(ItemType itemType) {
        mItemType = itemType;
    }
}
