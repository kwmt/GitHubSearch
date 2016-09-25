package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.UserEntity;

public class MemberEventEntity {
   @SerializedName("action")
    private String mAction;
    @SerializedName("member")
    private UserEntity mUser;
//    @SerializedName("repository")
//    private UserEntity mRepository;
//    @SerializedName("sender")
//    private UserEntity mSender;

    public String getAction() {
        if(mAction == null) { return ""; }
        return mAction;
    }

    public UserEntity getUser() {
        return mUser;
    }

//    public UserEntity getRepository() {
//        return mRepository;
//    }
//
//    public UserEntity getSender() {
//        return mSender;
//    }
}
