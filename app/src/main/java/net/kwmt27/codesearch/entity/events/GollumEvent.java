package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.GollumEntity;
import net.kwmt27.codesearch.entity.payloads.PageEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

public class GollumEvent extends EventEntity {

    @SerializedName("payload")
    private GollumEntity mGistEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if (mGistEntity == null) {
            return newTextView(context, "data empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);

        for (int i = 0; i < mGistEntity.getPages().size(); i++) {
            PageEntity page = mGistEntity.getPages().get(i);
            TextView actionTextView = newTextView(context, page.getAction(), true, null);
            flexboxLayout.addView(actionTextView);

            // 最後はpaddingは不要
            boolean hasPadding = true;
            if (i == mGistEntity.getPages().size() - 1) {
                hasPadding = false;
            }
            TextView repoTextView = newTextView(context, page.getPageName(), hasPadding,
                    newOnLinkClickClickableSpan(listener, page.getPageName(), page.getHtmlUrl(), getRepo()));
            flexboxLayout.addView(repoTextView);

            if (i < mGistEntity.getPages().size() - 1) {
                TextView andTextView = newTextView(context, "and", true, null);
                flexboxLayout.addView(andTextView);
            }
        }


        return flexboxLayout;
    }
}
