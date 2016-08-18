package net.kwmt27.codesearch;

import net.kwmt27.codesearch.model.ApiClient;
import net.kwmt27.codesearch.model.LoginModel;
import net.kwmt27.codesearch.model.SearchCodeModel;
import net.kwmt27.codesearch.model.SearchRepositoryModel;

/**
 * モデル置き場
 *
 * モデルを取り扱う際は、ModelLocatorから参照すること。
 */
public class ModelLocator {

    private static ApiClient sApiClient;

    private static final SearchCodeModel sSearchCodeModel;

    private static final SearchRepositoryModel sSearchRepositoryModel;

    private static final LoginModel sLoginModel;


    static {
        sApiClient = new ApiClient();
        sSearchCodeModel = new SearchCodeModel();
        sSearchRepositoryModel = new SearchRepositoryModel();
        sLoginModel = new LoginModel(App.getInstance().getApplicationContext());
    }

    public ModelLocator() {
    }

    public static ApiClient getApiClient() {
        return sApiClient;
    }

    public static void setApiClient(ApiClient apiClient) {
        sApiClient = apiClient;
    }

    public static SearchCodeModel getSearchCodeModel() {
        return sSearchCodeModel;
    }

    public static SearchRepositoryModel getSearchRepositoryModel() {
        return sSearchRepositoryModel;
    }

    public static LoginModel getLoginModel() {
        return sLoginModel;
    }

}


