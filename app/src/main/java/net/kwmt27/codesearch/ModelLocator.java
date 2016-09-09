package net.kwmt27.codesearch;

import net.kwmt27.codesearch.model.EventModel;
import net.kwmt27.codesearch.model.LoginModel;
import net.kwmt27.codesearch.model.SearchCodeModel;
import net.kwmt27.codesearch.model.SearchRepositoryModel;

/**
 * モデル置き場
 *
 * モデルを取り扱う際は、ModelLocatorから参照すること。
 */
public class ModelLocator {

    private static final SearchCodeModel sSearchCodeModel;

    private static final SearchRepositoryModel sSearchRepositoryModel;
    private static final EventModel sEventModel;

    private static final LoginModel sLoginModel;


    static {
        sSearchCodeModel = new SearchCodeModel();
        sSearchRepositoryModel = new SearchRepositoryModel();
        sLoginModel = new LoginModel(App.getInstance().getApplicationContext());
        sEventModel = new EventModel();
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

    public static EventModel getEventModel() {
        return sEventModel;
    }
}


