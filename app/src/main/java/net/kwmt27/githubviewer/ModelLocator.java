package net.kwmt27.githubviewer;

import net.kwmt27.githubviewer.model.ApiClient;
import net.kwmt27.githubviewer.model.GithubServiceOkhttp;

/**
 * モデル置き場
 *
 * モデルを取り扱う際は、ModelLocatorから参照すること。
 */
public class ModelLocator {

    private static ApiClient sApiClient;

    private static final GithubServiceOkhttp sGithubService;

    static {
        sApiClient = new ApiClient();
        sGithubService = new GithubServiceOkhttp();
    }

    public ModelLocator() {
    }

    public static ApiClient getApiClient() {
        return sApiClient;
    }

    public static void setApiClient(ApiClient apiClient) {
        sApiClient = apiClient;
    }

    public static GithubServiceOkhttp getGithubService() {
        return sGithubService;
    }
}


