package net.kwmt27.codesearch.entity.events;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.TeamAddEntity;

public class TeamAddEvent extends EventEntity {

    @SerializedName("payload")
    private TeamAddEntity mTeamAddEntity;

}
