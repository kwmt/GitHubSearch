package net.kwmt27.githubsearch.presenter.search;

import net.kwmt27.githubsearch.presenter.IBaseActivityPresenter;

public interface ISearchPresenter extends IBaseActivityPresenter {
    /**
     * コード検索できるかどうか
     * コード検索するには、GitHub APIの仕様上、userかrepo情報が必要
     */
    String CAN_SEARCH_CODE = "can_search_code";

    String REPO_ENTITY_KEY = "repo_entity_key";

    void onEditorActionSearch(String keyword);
}
