package net.kwmt27.rxjavasample;

import net.kwmt27.rxjavasample.model.ApiClient;
import net.kwmt27.rxjavasample.model.GithubService;

/**
 * モデル置き場
 *
 * モデルを取り扱う際は、ModelLocatorから参照すること。
 */
public class ModelLocator {

    private static ApiClient sApiClient;

    private static final GithubService sGithubService;

    static {
        sApiClient = new ApiClient();
        sGithubService = new GithubService();
    }

    public ModelLocator() {
    }

    public static ApiClient getApiClient() {
        return sApiClient;
    }

    public static void setApiClient(ApiClient apiClient) {
        sApiClient = apiClient;
    }

    public static GithubService getGithubService() {
        return sGithubService;
    }
}


