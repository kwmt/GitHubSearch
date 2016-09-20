package net.kwmt27.codesearch.entity.events;

import android.text.style.ClickableSpan;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.payloads.GollumEntity;
import net.kwmt27.codesearch.util.TextViewUtil;

public class GollumEvent extends EventEntity {

    @SerializedName("payload")
    private GollumEntity mGistEntity;

    @Override
    public void action(TextView view, ClickableSpan clickableSpan) {
        String repoName = getRepo().getName();
        String action = "gollum " + repoName; // TODO
        view.setText(action);
        TextViewUtil.addLink(view, repoName, clickableSpan);
    }

}
