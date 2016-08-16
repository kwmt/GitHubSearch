package net.kwmt27.codesearch.presenter.detail;

import net.kwmt27.codesearch.presenter.IBaseActivityPresenter;

public interface IDetailPresenter extends IBaseActivityPresenter {

    String REPO_ENTITY_KEY = "repo_entity_key";
    String URL_KEY = "url_key";


    void onActionSearchSelected();
}
