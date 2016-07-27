package net.kwmt27.githubviewer.presenter;

import android.os.Bundle;
import android.view.View;

public interface IBaseFragmentPresenter {
    void onViewCreated(View view, Bundle savedInstanceState);

    void onStop();

}
