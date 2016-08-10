package net.kwmt27.githubsearch.presenter.detail;

import net.kwmt27.githubsearch.presenter.IBaseActivityPresenter;

public interface IDetailPresenter extends IBaseActivityPresenter {

    String REPO_ENTITY_KEY = "repo_entity_key";
    String URL_KEY = "url_key";


    void onActionSearchSelected();
}
