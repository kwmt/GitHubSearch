package net.kwmt27.githubviewer.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ItemEntity {
    @SerializedName("name")
    private String mName;
    @SerializedName("path")
    private String mPath;
    @SerializedName("repository")
    private GithubRepoEntity mRepository;
    @SerializedName("text_matches")
    private List<TextMatchEntity> mTextMatchEntity = new ArrayList<>();


}
