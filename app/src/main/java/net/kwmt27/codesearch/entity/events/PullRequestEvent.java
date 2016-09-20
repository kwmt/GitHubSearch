package net.kwmt27.codesearch.entity.events;

import android.text.style.ClickableSpan;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.PullRequestEntity;
import net.kwmt27.codesearch.util.TextViewUtil;

public class PullRequestEvent extends EventEntity {

    @SerializedName("payload")
    private PullRequestEntity mPullRequestEntity;

    @Override
    public void action(TextView view, ClickableSpan repoClickableSpann) {
        if(mPullRequestEntity == null) {
            return;
        }
        String repoName = getRepo().getName();
        // TODO
        //String pullRequest = repoName + "#" + mPullRequestEntity.getNumber();
        String action = mPullRequestEntity.getAction() + " pull request at " + repoName;
        view.setText(action);
        TextViewUtil.addLink(view, repoName, repoClickableSpann);
    }

}
