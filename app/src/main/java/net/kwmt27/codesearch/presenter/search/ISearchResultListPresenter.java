package net.kwmt27.codesearch.presenter.search;

import androidx.annotation.Nullable;

import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.presenter.IBaseFragmentPresenter;

public interface ISearchResultListPresenter extends IBaseFragmentPresenter {


    void onScrollToBottom();

    void onEditorActionSearch(String keyword, @Nullable String repositoryFullNam, @Nullable GithubRepoEntity entity);

    void onClickReloadButton();
}
