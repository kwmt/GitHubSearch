package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.MembershipEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

/**
 * https://developer.github.com/v3/activity/events/types/#membershipevent
 */
public class MembershipEvent extends EventEntity {

    @SerializedName("payload")
    private MembershipEntity mMembershipEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if (mMembershipEntity == null) {
            return newTextView(context, "data empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, mMembershipEntity.getAction(), true, null);
        flexboxLayout.addView(actionTextView);

        TextView userTextView = newTextView(context, mMembershipEntity.getMember().getLogin(), true,
                newOnLinkClickClickableSpan(listener, mMembershipEntity.getMember().getLogin(), mMembershipEntity.getMember().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(userTextView);

        TextView toTextView = newTextView(context, "to", true, null);
        flexboxLayout.addView(toTextView);

        TextView repoTextView = newTextView(context, getRepo().getName(), false,
                newOnLinkClickClickableSpan(listener, getRepo().getName(), getRepo().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(repoTextView);

        return flexboxLayout;
    }
}
