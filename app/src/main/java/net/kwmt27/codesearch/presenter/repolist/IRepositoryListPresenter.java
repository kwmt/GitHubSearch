package net.kwmt27.codesearch.presenter.repolist;

import net.kwmt27.codesearch.presenter.IBaseFragmentPresenter;

public interface IRepositoryListPresenter extends IBaseFragmentPresenter {
    void onClickReloadButton();

    void onScrollToBottom();

}
