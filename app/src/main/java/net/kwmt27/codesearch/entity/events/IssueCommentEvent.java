package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.IssueCommentEntity;
import net.kwmt27.codesearch.util.TextViewUtil;
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
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setOnClickListener(v -> {
            if(listener != null) {
                listener.onLinkClick(mIssueCommentEntity.getIssueEntity().getTitle(), mIssueCommentEntity.getCommentEntity().getHtmlUrl(), getRepo());
            }
        });

        // action
        TextView actionTextView = newTextView(context, "issue " + " commented", true, null);
        linearLayout.addView(actionTextView);

        // title
        TextView titleTextView = newTextView(context, mIssueCommentEntity.getIssueEntity().getTitle(), false, null);
        titleTextView.setLines(1);
        titleTextView.setEllipsize(TextUtils.TruncateAt.END);
        linearLayout.addView(titleTextView);

        // comment body
        String body = TextViewUtil.removeNewLines(mIssueCommentEntity.getCommentEntity().getBody());
        TextView bodyTextView = newTextView(context, body, false, null);
        bodyTextView.setLines(1);
        bodyTextView.setEllipsize(TextUtils.TruncateAt.END);
        linearLayout.addView(bodyTextView);

        return linearLayout;
    }
}
