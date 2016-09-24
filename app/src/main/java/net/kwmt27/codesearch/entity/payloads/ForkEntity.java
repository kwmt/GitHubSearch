package net.kwmt27.codesearch.entity.payloads;

import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.entity.GithubRepoEntity;

public class ForkEntity {

    @SerializedName("forkee")
    private GithubRepoEntity mRepository;

    public GithubRepoEntity getRepository() {
        return mRepository;
    }
}
