package net.kwmt27.githubsearch.presenter.repolist;

import net.kwmt27.githubsearch.presenter.IBaseFragmentPresenter;

public interface IRepositoryListPresenter extends IBaseFragmentPresenter {
    void onClickReloadButton();

    void onScrollToBottom();

}
