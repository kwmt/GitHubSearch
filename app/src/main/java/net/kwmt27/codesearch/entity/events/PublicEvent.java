package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.PublicEventEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

/**
 * https://developer.github.com/v3/activity/events/types/#publicevent
 */
public class PublicEvent extends EventEntity {

    @SerializedName("payload")
    private PublicEventEntity mPublicEventEntity;


    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if (mPublicEventEntity == null) {
            return newTextView(context, "data empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, "A private repository", true, null);
        flexboxLayout.addView(actionTextView);

        TextView userTextView = newTextView(context, getRepo().getFullName(), true,
                newOnLinkClickClickableSpan(listener, getRepo().getFullName(), getRepo().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(userTextView);

        TextView toTextView = newTextView(context, "is open sourced", false, null);
        flexboxLayout.addView(toTextView);

        return flexboxLayout;
    }
}
