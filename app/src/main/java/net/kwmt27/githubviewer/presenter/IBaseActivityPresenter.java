package net.kwmt27.githubviewer.presenter;

import android.os.Bundle;

public interface IBaseActivityPresenter {
    void onCreate(Bundle savedInstanceState);
    void onStop();
}