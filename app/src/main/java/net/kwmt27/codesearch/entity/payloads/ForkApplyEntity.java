package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

public class ForkApplyEntity {
    @SerializedName("head")
    private String mHead;
    @SerializedName("before")
    private String mBefore;
    @SerializedName("after")
    private String mAfter;

}
