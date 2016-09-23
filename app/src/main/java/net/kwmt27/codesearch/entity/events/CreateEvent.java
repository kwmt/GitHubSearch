package net.kwmt27.codesearch.entity.events;


import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.CreateEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

// https://developer.github.com/v3/activity/events/types/#createevent
public class CreateEvent extends EventEntity {
    @SerializedName("payload")
    private CreateEntity mCreateEntity;


    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if(mCreateEntity == null){
            return newTextView(context, "data, empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, "created " + mCreateEntity.getRefType(), true, null);
        flexboxLayout.addView(actionTextView);

        TextView repoTextView = newTextView(context, getRepo().getName(), false, new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onLinkClick(getRepo().getName(), getRepo().getHtmlUrl(), getRepo());
                }
            }
        });
        flexboxLayout.addView(repoTextView);
        return flexboxLayout;
    }
}
