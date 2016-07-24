package net.kwmt27.githubviewer;

import net.kwmt27.githubviewer.model.ApiClient;

import okhttp3.HttpUrl;

public class MockApiClient extends ApiClient {

    private HttpUrl mBaseUrl;

    public MockApiClient(HttpUrl baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Override
    public String buildUrl(String path) {
        return mBaseUrl.resolve(path).toString();
    }

}