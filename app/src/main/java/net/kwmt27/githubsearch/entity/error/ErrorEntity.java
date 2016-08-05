package net.kwmt27.githubsearch.entity.error;

import com.google.gson.annotations.SerializedName;

public class ErrorEntity {

    /** エラー対象パラメータ名、または識別子 */
    @SerializedName("field")
    private String mField;
    /** 詳細メッセージ */
    @SerializedName("message")
    private String mMessage;


    public ErrorEntity(String field, String detailMessage) {
        mField = field;
        mMessage = detailMessage;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getField() {
        return mField;
    }
}
