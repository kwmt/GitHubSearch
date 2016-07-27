package net.kwmt27.githubviewer.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.presenter.ISearchPresenter;
import net.kwmt27.githubviewer.presenter.SearchPresenter;

public class SearchActivity extends BaseActivity implements SearchPresenter.ISearchView {

    public static void startActivity(AppCompatActivity activity) {

        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }

    private ISearchPresenter mSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchPresenter = new SearchPresenter(this);
        mSearchPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void setupComponents() {
        setUpActionBar();
    }

    @Override
    public void updateDetailView(GithubRepoEntity githubRepoEntity) {

    }
}
