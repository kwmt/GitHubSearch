package net.kwmt27.rxjavasample;

import net.kwmt27.rxjavasample.model.ApiClient;

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