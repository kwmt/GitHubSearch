package net.kwmt27.codesearch.entity.events;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.PageBuildEntity;

/**
 * https://developer.github.com/v3/activity/events/types/#pagebuildevent
 *
 * Events of this type are not visible in timelines. These events are only used to trigger hooks.
 */
public class PageBuildEvent extends EventEntity {

    @SerializedName("payload")
    private PageBuildEntity mPageBuildEntity;

}
