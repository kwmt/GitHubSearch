package net.kwmt27.githubviewer.entity;

import com.google.gson.annotations.SerializedName;

public class MatchEntity {
    @SerializedName("text")
    private String mText;
    @SerializedName("indices")
    private int[] mIndices;

    public String getText() {
        return mText;
    }

    public int[] getIndices() {
        return mIndices;
    }
}
