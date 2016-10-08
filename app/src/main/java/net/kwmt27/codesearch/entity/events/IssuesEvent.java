package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.IssueEventEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

/**
 * https://developer.github.com/v3/activity/events/types/#issuesevent
 */
public class IssuesEvent extends EventEntity {

    @SerializedName("payload")
    private IssueEventEntity mIssueEventEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if (mIssueEventEntity == null) {
            return newTextView(context, "data empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, mIssueEventEntity.getAction() + " issue", true, null);
        flexboxLayout.addView(actionTextView);

        String issue = getRepo().getFullName() + "#" + mIssueEventEntity.getIssueEntity().getNumber();
        TextView repoTextView = newTextView(context, issue, false,
                newOnLinkClickClickableSpan(listener, issue, mIssueEventEntity.getIssueEntity().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(repoTextView);
        return flexboxLayout;
    }


}
