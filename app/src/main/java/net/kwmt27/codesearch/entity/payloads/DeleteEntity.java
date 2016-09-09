package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

public class DeleteEntity {

    @SerializedName("ref_type")
    private String mRefType;
    @SerializedName("ref")
    private String mRef;
}
