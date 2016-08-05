package net.kwmt27.githubsearch.presenter.search;

import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.presenter.IBaseFragmentPresenter;

public interface ISearchResultListPresenter extends IBaseFragmentPresenter {


    void onScrollToBottom();

    void onEditorActionSearch(String keyword, GithubRepoEntity entity);
}
