package net.kwmt27.codesearch.entity.events;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.PageBuildEntity;

public class PageBuildEvent extends EventEntity {

    @SerializedName("payload")
    private PageBuildEntity mPageBuildEntity;

}
