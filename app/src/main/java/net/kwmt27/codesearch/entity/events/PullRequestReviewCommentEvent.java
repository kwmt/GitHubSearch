package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.PullRequestReviewCommentEntity;
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

        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, mPullRequestReviewCommentEntity.getAction(), true, null);
        flexboxLayout.addView(actionTextView);

        String pullRequest = getRepo().getName() + "#" + mPullRequestReviewCommentEntity.getPullRequestEntity().getNumber();
        TextView pullRequestTextView = newTextView(context, pullRequest, false, newOnLinkClickClickableSpan(listener, pullRequest, mPullRequestReviewCommentEntity.getPullRequestEntity().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(pullRequestTextView);

        return flexboxLayout;
    }

}
