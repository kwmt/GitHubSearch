package net.kwmt27.codesearch.entity.events;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.ForkApplyEntity;

/**
 * https://developer.github.com/v3/activity/events/types/#forkapplyevent
 *
 * @deprecated Events of this type are no longer created, but it's possible that they exist in timelines of some users.
 */
@Deprecated
public class ForkApplyEvent extends EventEntity {

    @SerializedName("payload")
    private ForkApplyEntity mForkApplyEntity;


}
