package net.kwmt27.codesearch.entity;

import android.text.format.DateFormat;
import android.text.style.ClickableSpan;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class EventEntity {
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


    public boolean isPublic() {
        return mIsPublic;
    }

    public ActorEntity getActor() {
        return mActor;
    }

    public GithubRepoEntity getRepo() {
        return mRepo;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public String getFormattedCreatedAt() {
        if(mCreatedAt == null) { return "not yet created"; }
        return DateFormat.format("yyyy/MM/dd HH:mm:ss", mCreatedAt).toString();
    }

    public String getId() {
        return mId;
    }

    public void action(TextView view, ClickableSpan clickableSpan){
        throw new RuntimeException("Stub!");
    }
}
