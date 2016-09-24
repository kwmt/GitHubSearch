package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.FollowEventEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

public class FollowEvent extends EventEntity {

    @SerializedName("payload")
    private FollowEventEntity mFollowEventEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if (mFollowEventEntity == null) {
            return newTextView(context, "data empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, "downloaded ", true, null);
        flexboxLayout.addView(actionTextView);

        TextView repoTextView = newTextView(context, mFollowEventEntity.getTargetUser().getLogin(), false,
                newOnLinkClickClickableSpan(listener, mFollowEventEntity.getTargetUser().getLogin(), mFollowEventEntity.getTargetUser().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(repoTextView);
        return flexboxLayout;
    }
}
