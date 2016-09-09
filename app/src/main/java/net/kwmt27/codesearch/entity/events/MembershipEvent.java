package net.kwmt27.codesearch.entity.events;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.MembershipEntity;

public class MembershipEvent extends EventEntity {

    @SerializedName("payload")
    private MembershipEntity mMembershipEntity;

}
