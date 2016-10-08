package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.GithubRepoEntity;

public class RepositoryEventEntity {
    @SerializedName("action")
    private String mAction;
    @SerializedName("repository")
    private GithubRepoEntity mRepository;

    public String getAction() {
        return mAction;
    }

    public GithubRepoEntity getRepository() {
        return mRepository;
    }
}
