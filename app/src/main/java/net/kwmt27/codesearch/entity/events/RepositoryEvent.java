package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.RepositoryEventEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

public class RepositoryEvent extends EventEntity {

    @SerializedName("payload")
    private RepositoryEventEntity mRepositoryEventEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if(mRepositoryEventEntity == null){
            return newTextView(context, "data empty", false, null);
        }

        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, mRepositoryEventEntity.getAction(), true, null);
        flexboxLayout.addView(actionTextView);

        TextView pullRequestTextView = newTextView(context, getRepo().getFullName(), false, newOnLinkClickClickableSpan(listener, getRepo().getFullName(), getRepo().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(pullRequestTextView);

        return flexboxLayout;
    }


}
