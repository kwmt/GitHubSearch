package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.PullRequestEventEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

/**
 * https://developer.github.com/v3/activity/events/types/#pullrequestevent
 */
public class PullRequestEvent extends EventEntity {

    @SerializedName("payload")
    private PullRequestEventEntity mPullRequestEventEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if(mPullRequestEventEntity == null){
            return newTextView(context, "data empty", false, null);
        }

        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, mPullRequestEventEntity.getAction()+ " pull request", true, null);
        flexboxLayout.addView(actionTextView);

        String pullRequest = getRepo().getName() + "#" + mPullRequestEventEntity.getNumber();
        TextView pullRequestTextView = newTextView(context, pullRequest, false, newOnLinkClickClickableSpan(listener, pullRequest, mPullRequestEventEntity.getPullRequestEntity().getHtmlUrl(), getRepo()));
        flexboxLayout.addView(pullRequestTextView);

        return flexboxLayout;
    }

}
