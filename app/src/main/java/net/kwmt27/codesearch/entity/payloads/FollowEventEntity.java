package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.UserEntity;

public class FollowEventEntity {
    @SerializedName("target")
    private UserEntity mTarget;

    public UserEntity getTargetUser() {
        if(mTarget == null) { return new UserEntity(""); }
        return mTarget;
    }
}
