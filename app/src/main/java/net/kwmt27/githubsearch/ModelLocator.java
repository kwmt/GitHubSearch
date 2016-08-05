package net.kwmt27.githubsearch;

import net.kwmt27.githubsearch.model.ApiClient;
import net.kwmt27.githubsearch.model.SearchModel;

/**
 * モデル置き場
 *
 * モデルを取り扱う際は、ModelLocatorから参照すること。
 */
public class ModelLocator {

    private static ApiClient sApiClient;

    private static final SearchModel sSearchModel;

    static {
        sApiClient = new ApiClient();
        sSearchModel = new SearchModel();
    }

    public ModelLocator() {
    }

    public static ApiClient getApiClient() {
        return sApiClient;
    }

    public static void setApiClient(ApiClient apiClient) {
        sApiClient = apiClient;
    }

    public static SearchModel getSearchModel() {
        return sSearchModel;
    }
}


