package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.IssueCommentEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

/**
 * https://developer.github.com/v3/activity/events/types/#issuecommentevent
 */
public class IssueCommentEvent extends EventEntity {

    @SerializedName("payload")
    private IssueCommentEntity mIssueCommentEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if (mIssueCommentEntity == null) {
            return newTextView(context, "data empty", false, null);
        }
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, mIssueCommentEntity.getAction() + " issue", true, null);
        flexboxLayout.addView(actionTextView);

        String issue = getRepo().getFullName() + "#" + mIssueCommentEntity.getIssueEntity().getNumber();
        TextView repoTextView = newTextView(context, issue, false,
                newOnLinkClickClickableSpan(listener, issue, mIssueCommentEntity.getCommentEntity().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(repoTextView);
        return flexboxLayout;
    }
}
