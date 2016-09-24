package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

public class GistEventEntity {
    // TODO
    @SerializedName("action")
    private String mAction;
@SerializedName("gist")
    private GistEntity mGist;

    public String getAction() {
        return mAction;
    }

    public GistEntity getGist() {
        return mGist;
    }
}
