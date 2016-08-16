package net.kwmt27.codesearch.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemEntity implements Serializable {
    @SerializedName("name")
    private String mName;
    @SerializedName("path")
    private String mPath;
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("repository")
    private GithubRepoEntity mRepository;
    @SerializedName("text_matches")
    private List<TextMatchEntity> mTextMatchEntityList = new ArrayList<>();

    private ItemType mItemType;

    public ItemEntity(ItemType itemType) {
        mItemType = itemType;
    }

    public String getName() {
        return mName;
    }

    public String getPath() {
        return mPath;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public GithubRepoEntity getRepository() {
        return mRepository;
    }

    public List<TextMatchEntity> getTextMatchEntityList() {
        return mTextMatchEntityList;
    }

    public ItemType getItemType() {
        return mItemType;
    }
}
