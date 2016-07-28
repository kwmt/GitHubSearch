package net.kwmt27.githubviewer.presenter.search;

import net.kwmt27.githubviewer.presenter.IBaseActivityPresenter;

public interface ISearchPresenter extends IBaseActivityPresenter {
    /**
     * コード検索できるかどうか
     * コード検索するには、GitHub APIの仕様上、userかrepo情報が必要
     */
    String CAN_SEARCH_CODE = "can_search_code";


    void onEditorActionSearch(String keyword);
}
