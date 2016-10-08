package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.ForkEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

/**
 * https://developer.github.com/v3/activity/events/types/#forkevent
 */
public class ForkEvent extends EventEntity {

    @SerializedName("payload")
    private ForkEntity mForkEntity;


    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if (mForkEntity == null) {
            return newTextView(context, "data empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, "forked", true, null);
        flexboxLayout.addView(actionTextView);

        TextView repoTextView = newTextView(context, mForkEntity.getRepository().getName(), false,
                newOnLinkClickClickableSpan(listener, mForkEntity.getRepository().getName(), mForkEntity.getRepository().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(repoTextView);
        return flexboxLayout;
    }
}
