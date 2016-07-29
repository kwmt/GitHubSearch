package net.kwmt27.githubviewer.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * コード検索結果
 */
public class SearchCodeResultEntity {
    @SerializedName("total_count")
    private int mTotalCount;
    @SerializedName("incomplete_results")
    private boolean mIncompleteResults;
    @SerializedName("items")
    private List<ItemEntity> mItemEntityList = new ArrayList<>();

    public int getTotalCount() {
        return mTotalCount;
    }

    public boolean isIncompleteResults() {
        return mIncompleteResults;
    }

    public List<ItemEntity> getItemEntityList() {
        if(mItemEntityList == null) {
            return new ArrayList<>();
        }
        return mItemEntityList;
    }

    /** 結果がみつかったどうか */
    public boolean foundResult() {
        return getTotalCount() > 0;
    }
}
