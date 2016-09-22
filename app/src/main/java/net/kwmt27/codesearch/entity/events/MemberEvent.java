package net.kwmt27.codesearch.entity.events;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.MemberEntity;

public class MemberEvent extends EventEntity {

    @SerializedName("payload")
    private MemberEntity mMemberEntity;
//
//    @Override
//    public void action(TextView view, ClickableSpan repoClickableSpan) {
//        if(mMemberEntity == null) {
//            return;
//        }
//
//        String repoName = getRepo().getName();
//
//        // who actioned repo
//        // TODO: add link to mMemberEntity.getUser().getLogin()
//        String action = mMemberEntity.getAction() + " " + mMemberEntity.getUser().getLogin() + " to " + repoName;
//
//        view.setText(action);
//        //TextViewUtil.addLink(view, repoName, clickableSpan);
//        TextViewUtil.addLink(view, repoName, repoClickableSpan);
//    }

}
