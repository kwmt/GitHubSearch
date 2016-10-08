package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.GistEventEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

/**
 * https://developer.github.com/v3/activity/events/types/#gistevent
 *
 * @deprecated Events of this type are no longer created, but it's possible that they exist in timelines of some users.
 */
@Deprecated
public class GistEvent extends EventEntity {

    @SerializedName("payload")
    private GistEventEntity mGistEventEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if (mGistEventEntity == null) {
            return newTextView(context, "data empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, mGistEventEntity.getAction(), true, null);
        flexboxLayout.addView(actionTextView);

        TextView repoTextView = newTextView(context, "gist", false,
                newOnLinkClickClickableSpan(listener, "gist", mGistEventEntity.getGist().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(repoTextView);
        return flexboxLayout;
    }
}
