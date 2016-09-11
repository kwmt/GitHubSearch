package net.kwmt27.codesearch.entity.events;

import android.text.style.ClickableSpan;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.PullRequestReviewCommentEntity;
import net.kwmt27.codesearch.util.TextViewUtil;

public class PullRequestReviewCommentEvent extends EventEntity {

    @SerializedName("payload")
    private PullRequestReviewCommentEntity mPullRequestReviewCommentEntity;

    @Override
    public void action(TextView view, ClickableSpan clickableSpan) {
        String repoName = getRepo().getName();
        String action = "pull request review comment " + repoName; // TODO
        view.setText(action);
        TextViewUtil.addLink(view, repoName, clickableSpan);
    }

}
