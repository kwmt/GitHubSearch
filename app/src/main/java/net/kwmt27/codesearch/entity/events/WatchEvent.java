package net.kwmt27.codesearch.entity.events;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.WatchEntity;

public class WatchEvent extends EventEntity {

    @SerializedName("payload")
    private WatchEntity mWatchEntity;

//    @Override
//    public void action(TextView view, ClickableSpan repoClickableSpan) {
//        if(mWatchEntity != null) {
//            String repoName = getRepo().getName(); // full_name
//            String action = mWatchEntity.getAction() + " " + repoName;
//            view.setText(action);
//            TextViewUtil.addLink(view, repoName, repoClickableSpan);
//        }
//    }


}
