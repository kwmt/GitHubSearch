package net.kwmt27.githubsearch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TextMatchEntity {
    /** 検索語句を含む一部のテキスト */
    @SerializedName("fragment")
    private String mFragmentText;
    @SerializedName("matches")
    private List<MatchEntity> mMatches = new ArrayList<>();

    public List<MatchEntity> getMatches() {
        if(mMatches == null) {
            return new ArrayList<>();
        }
        return mMatches;
    }

    public String getFragmentText() {
        return mFragmentText;
    }
}
