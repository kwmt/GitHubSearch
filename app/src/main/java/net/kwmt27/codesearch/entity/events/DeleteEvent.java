package net.kwmt27.codesearch.entity.events;


import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.DeleteEntity;

// https://developer.github.com/v3/activity/events/types/#deleteevent
public class DeleteEvent extends EventEntity {
    @SerializedName("payload")
    private DeleteEntity mDeleteEntity;

}
