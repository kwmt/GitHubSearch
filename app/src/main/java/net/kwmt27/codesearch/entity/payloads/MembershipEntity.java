package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.UserEntity;

public class MembershipEntity {
    @SerializedName("action")
    private String mAction;
    @SerializedName("member")
    private UserEntity mMember;

    public String getAction() {
        return mAction;
    }

    public UserEntity getMember() {
        return mMember;
    }
}
