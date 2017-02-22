package net.kwmt27.codesearch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class IssueEntity {
    @SerializedName("id")
    private int mId;
    @SerializedName("number")
    private int mNumber;
    @SerializedName("state")
    private String mState;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("body")
    private String mBody;
    @SerializedName("labels_url")
    private String mLabelsUrl;
    @SerializedName("comments_url")
    private String mCommentsUrl;
    @SerializedName("events_url")
    private String mEventsUrl;
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("created_at")
    private Date mCreatedAt;
    @SerializedName("updated_at")
    private Date mUpdatedAt;

    public int getId() {
        return mId;
    }

    public int getNumber() {
        return mNumber;
    }

    public String getState() {
        return mState;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public String getLabelsUrl() {
        return mLabelsUrl;
    }

    public String getCommentsUrl() {
        return mCommentsUrl;
    }

    public String getEventsUrl() {
        return mEventsUrl;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }
}
