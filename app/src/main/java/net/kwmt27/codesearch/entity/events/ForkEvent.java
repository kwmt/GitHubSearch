package net.kwmt27.codesearch.entity.events;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.ForkEntity;

public class ForkEvent extends EventEntity {

    @SerializedName("payload")
    private ForkEntity mForkEntity;

}
