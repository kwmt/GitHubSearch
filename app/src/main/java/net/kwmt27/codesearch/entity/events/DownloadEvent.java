package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.DownloadEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

/**
 * https://developer.github.com/v3/activity/events/types/#downloadevent
 *
 * Events of this type are no longer created, but it's possible that they exist in timelines of some users.
 */
public class DownloadEvent extends EventEntity {

    @SerializedName("payload")
    private DownloadEntity mDownloadEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if (mDownloadEntity == null) {
            return newTextView(context, "data empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, "downloaded ", true, null);
        flexboxLayout.addView(actionTextView);

        TextView repoTextView = newTextView(context, mDownloadEntity.getName(), false,
                newOnLinkClickClickableSpan(listener, mDownloadEntity.getName(), mDownloadEntity.getHtmlUrl(), getRepo()));
        flexboxLayout.addView(repoTextView);
        return flexboxLayout;
    }
}
