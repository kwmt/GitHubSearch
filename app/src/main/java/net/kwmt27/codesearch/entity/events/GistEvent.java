package net.kwmt27.codesearch.entity.events;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.ForkApplyEntity;
import net.kwmt27.codesearch.entity.payloads.GistEntity;

public class GistEvent extends EventEntity {

    @SerializedName("payload")
    private GistEntity mGistEntity;

}
