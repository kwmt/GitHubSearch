package net.kwmt27.githubsearch;

import net.kwmt27.githubsearch.model.ApiClient;
import net.kwmt27.githubsearch.model.DbModel;
import net.kwmt27.githubsearch.model.LoginModel;
import net.kwmt27.githubsearch.model.SearchCodeModel;
import net.kwmt27.githubsearch.model.SearchRepositoryModel;

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

    private static final DbModel sDbModel;

    static {
        sApiClient = new ApiClient();
        sSearchCodeModel = new SearchCodeModel();
        sSearchRepositoryModel = new SearchRepositoryModel();
        sLoginModel = new LoginModel(App.getInstance().getApplicationContext());
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

    public static SearchCodeModel getSearchCodeModel() {
        return sSearchCodeModel;
    }

    public static SearchRepositoryModel getSearchRepositoryModel() {
        return sSearchRepositoryModel;
    }

    public static LoginModel getLoginModel() {
        return sLoginModel;
    }

    public static DbModel getDbModel() {
        return sDbModel;
    }
}


