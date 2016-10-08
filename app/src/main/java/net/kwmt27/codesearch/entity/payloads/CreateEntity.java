package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

public class CreateEntity {

    @SerializedName("ref_type")
    private String mRefType;
    @SerializedName("ref")
    private String mRef;
    @SerializedName("master_branch")
    private String mMasterBranch;
    @SerializedName("description")
    private String mDescription;

    public String getRefType() {
        return mRefType;
    }

    public String getRef() {
        return mRef;
    }

    public String getMasterBranch() {
        return mMasterBranch;
    }

    public String getDescription() {
        return mDescription;
    }
}
