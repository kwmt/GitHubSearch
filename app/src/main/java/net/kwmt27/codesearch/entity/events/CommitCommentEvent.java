package net.kwmt27.codesearch.entity.events;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.CommitCommentEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

// https://developer.github.com/v3/activity/events/types/#commitcommentevent
public class CommitCommentEvent extends EventEntity {

    @SerializedName("payload")
    private CommitCommentEntity mCommitCommentEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if(mCommitCommentEntity == null){
            return newTextView(context, "data empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, mCommitCommentEntity.getAction(), true, null);
        flexboxLayout.addView(actionTextView);

        TextView repoTextView = newTextView(context, getRepo().getName(), false, newOnLinkClickClickableSpan(listener, getRepo().getName(), getRepo().getHtmlUrl(), getRepo())); ;
        flexboxLayout.addView(repoTextView);
        return flexboxLayout;
    }


}
