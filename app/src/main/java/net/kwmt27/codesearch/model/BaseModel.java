package net.kwmt27.codesearch.model;


import net.kwmt27.codesearch.dagger.DaggerAppComponent;

import javax.inject.Inject;

public class BaseModel {

    @Inject
    ApiClient mApiClient;

    public BaseModel() {
        DaggerAppComponent.create().inject(this);
    }

    public BaseModel(ApiClient apiClient) {
        mApiClient = apiClient;
    }
}
