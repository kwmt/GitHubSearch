package net.kwmt27.githubsearch.presenter.repolist;

import net.kwmt27.githubsearch.presenter.IBaseFragmentPresenter;

/**
 * Created by kwmt on 2016/08/10.
 */
public interface IRepositoryListPresenter extends IBaseFragmentPresenter {
    void onClickReloadButton();

    void onScrollToBottom();

}
