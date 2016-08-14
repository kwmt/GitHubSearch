package net.kwmt27.githubsearch.view.search;

import net.kwmt27.githubsearch.model.ISearchModel;

public interface FragmentProgressCallback {
    void showProgress();

    void hideProgress();


    void showNotFoundPageIfNeeded(ISearchModel model, boolean show);
}
