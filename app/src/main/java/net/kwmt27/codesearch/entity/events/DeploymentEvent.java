package net.kwmt27.codesearch.entity.events;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.DeploymentStatusEntity;

/**
 * https://developer.github.com/v3/activity/events/types/#deploymentevent
 *
 * Events of this type are not visible in timelines. These events are only used to trigger hooks.
 */
public class DeploymentEvent extends EventEntity {

    @SerializedName("payload")
    private DeploymentStatusEntity mDeploymentStatusEntity;


}
