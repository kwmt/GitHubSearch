package net.kwmt27.codesearch.entity.events;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.entity.payloads.PushEntity;
import net.kwmt27.codesearch.view.events.EventListFragment;

/**
 * https://developer.github.com/v3/activity/events/types/#pushevent
 */
public class PushEvent extends EventEntity {

    @SerializedName("payload")
    private PushEntity mPushEntity;

    @Override
    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        if(mPushEntity == null){
            return newTextView(context, "data empty", false, null);
        }

        FlexboxLayout flexboxLayout = newFlexboxLayout(context);
        TextView actionTextView = newTextView(context, "pushed", true, null);
        flexboxLayout.addView(actionTextView);

        GithubRepoEntity githubRepoEntity = new GithubRepoEntity(getRepo().getName());
        TextView pullRequestTextView = newTextView(context, githubRepoEntity.getFullName(), false, newOnLinkClickClickableSpan(listener, githubRepoEntity.getFullName(), githubRepoEntity.getHtmlUrl(),githubRepoEntity));
        flexboxLayout.addView(pullRequestTextView);

        return flexboxLayout;
    }


}
