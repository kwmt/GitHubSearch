package net.kwmt27.codesearch.entity.error;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ApiErrorEntity {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("errors")
    private List<ErrorEntity> mErrors = new ArrayList<>();


    public ApiErrorEntity() {

    }

    public ApiErrorEntity(String message) {
        mMessage = message;
    }

    public ApiErrorEntity(String message, List<ErrorEntity> errors) {
        mMessage = message;
        mErrors = errors;
    }

    /**
     * @return エラーメッセージを取得する。
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * エラーメッセージをセットする。
     *
     * @param message
     */
    public void setMessage(String message) {
        mMessage = message;
    }

    public List<ErrorEntity> getErrors() {
        return mErrors;
    }


    /**
     * @return 指定したfieldの詳細なエラーメッセージを取得
     */
    public String findDetailMessageByField(String field) {
        for (ErrorEntity error : mErrors) {
            if (error.getField().equals(field)) {
                return error.getMessage();
            }
        }
        return "";
    }

    /**
     * 詳細なエラーメッセージを取得
     *
     * @return 項目(field)が一つ以上の場合、一番目のメッセージを取得({@code findDetailMessageByField}の簡易版)
     */
    public String getDetailMessage() {
        if (mErrors.size() >= 1) {
            return mErrors.get(0).getMessage();
        }
        return "";
    }


}
