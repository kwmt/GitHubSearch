package net.kwmt27.githubsearch;

import net.kwmt27.githubsearch.model.ApiClient;
import net.kwmt27.githubsearch.model.DbModel;
import net.kwmt27.githubsearch.model.LoginModel;
import net.kwmt27.githubsearch.model.SearchModel;

/**
 * モデル置き場
 *
 * モデルを取り扱う際は、ModelLocatorから参照すること。
 */
public class ModelLocator {

    private static ApiClient sApiClient;

    private static final SearchModel sSearchModel;

    private static final LoginModel sLoginModel;

    private static final DbModel sDbModel;

    static {
        sApiClient = new ApiClient();
        sSearchModel = new SearchModel();
        sLoginModel = new LoginModel();
        sDbModel = new DbModel(App.getInstance().getApplicationContext());
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

    public static LoginModel getLoginModel() {
        return sLoginModel;
    }

    public static DbModel getDbModel() {
        return sDbModel;
    }
}


