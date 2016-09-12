package net.kwmt27.codesearch.presenter.events;

import net.kwmt27.codesearch.presenter.IBaseFragmentPresenter;

public interface IEventListPresenter extends IBaseFragmentPresenter {
    void onClickReloadButton();

    void onScrollToBottom();

    void onRefresh();
}
