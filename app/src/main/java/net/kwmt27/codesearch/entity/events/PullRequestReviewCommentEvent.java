package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.PullRequestReviewCommentEntity;
import net.kwmt27.codesearch.util.TextViewUtil;
import net.kwmt27.codesearch.util.ViewUtil;
import net.kwmt27.codesearch.view.events.EventListFragment;

/**
 * https://developer.github.com/v3/activity/events/types/#pullrequestreviewcommentevent
 */
public class PullRequestReviewCommentEvent extends EventEntity {

    @SerializedName("payload")
    private PullRequestReviewCommentEntity mPullRequestReviewCommentEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if(mPullRequestReviewCommentEntity == null){
            return newTextView(context, "data empty", false, null);
        }

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setOnClickListener(v -> {
            if(listener != null) {
                listener.onLinkClick(mPullRequestReviewCommentEntity.getPullRequestEntity().getTitle(), mPullRequestReviewCommentEntity.getCommentEntity().getHtmlUrl(), getRepo());
            }
        });

        ViewUtil.setRippleDrawable(context, linearLayout);

        // action
        TextView actionTextView = newTextView(context, "commented " + " on pull request", true, null);
        linearLayout.addView(actionTextView);

        // title
        TextView titleTextView = newTextView(context, mPullRequestReviewCommentEntity.getPullRequestEntity().getTitle(), false, null);
        titleTextView.setLines(1);
        titleTextView.setEllipsize(TextUtils.TruncateAt.END);
        titleTextView.setTypeface(null, Typeface.BOLD);
        linearLayout.addView(titleTextView);

        // comment body
        String body = TextViewUtil.removeNewLines(mPullRequestReviewCommentEntity.getCommentEntity().getBody());
        TextView bodyTextView = newTextView(context, body, false, null);
        bodyTextView.setLines(1);
        bodyTextView.setEllipsize(TextUtils.TruncateAt.END);
        linearLayout.addView(bodyTextView);

        return linearLayout;

    }

}
