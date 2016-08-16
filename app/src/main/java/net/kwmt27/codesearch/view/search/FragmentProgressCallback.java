package net.kwmt27.codesearch.view.search;

import net.kwmt27.codesearch.model.ISearchModel;

public interface FragmentProgressCallback {
    void showProgress();

    void hideProgress();


    void showNotFoundPageIfNeeded(ISearchModel model, boolean show);
}
