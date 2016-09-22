package net.kwmt27.codesearch.entity.events;


import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.CreateEntity;

// https://developer.github.com/v3/activity/events/types/#createevent
public class CreateEvent extends EventEntity {
    @SerializedName("payload")
    private CreateEntity mCreateEntity;

//    @Override
//    public void action(TextView view, ClickableSpan clickableSpan) {
//        String repoName = getRepo().getName();
//        String action = this.getClass().getSimpleName().replace("Event", "").toLowerCase() + "d " + repoName;
//        view.setText(action);
//        TextViewUtil.addLink(view, repoName, clickableSpan);
//    }

}
