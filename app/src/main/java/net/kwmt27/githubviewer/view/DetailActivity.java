package net.kwmt27.githubviewer.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.presenter.DetailPresenter;
import net.kwmt27.githubviewer.presenter.IDetailPresenter;

public class DetailActivity extends BaseActivity implements DetailPresenter.IDetailView {


    public static void startActivity(AppCompatActivity activity, String title, String owner, String repo) {
        Intent intent = new Intent(activity.getApplicationContext(), DetailActivity.class);
        intent.putExtra(TITLE_KEY, title);
        intent.putExtra(IDetailPresenter.OWENER_KEY, owner);
        intent.putExtra(IDetailPresenter.REPO_KEY, repo);
        activity.startActivity(intent);
    }

    private IDetailPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPresenter = new DetailPresenter(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void setupComponents() {
        setUpActionBar();
    }

    @Override
    public void updateDetailView(GithubRepoEntity githubRepoEntity) {

    }
}
