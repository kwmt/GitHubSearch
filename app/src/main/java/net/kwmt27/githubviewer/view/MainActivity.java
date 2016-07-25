package net.kwmt27.githubviewer.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.presenter.IMainPresenter;
import net.kwmt27.githubviewer.presenter.MainPresenter;
import net.kwmt27.githubviewer.util.Logger;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.IMainView {

    private IMainPresenter mPresenter;
    private GitHubRepoListAdapter mGitHubRepoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);

        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void setupComponents() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.github_repo_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mGitHubRepoListAdapter = new GitHubRepoListAdapter(getApplicationContext(), new OnItemClickListener<GitHubRepoListAdapter, GithubRepoEntity>() {
            @Override
            public void onItemClick(GitHubRepoListAdapter adapter, int position, GithubRepoEntity entity) {
                Logger.d("name:" + entity.getName());
            }
        });
        List<GithubRepoEntity> datasource
                = Arrays.asList(new GithubRepoEntity("data1"), new GithubRepoEntity("data2"));
        mGitHubRepoListAdapter.setGithubRepoEntityList(datasource);
        recyclerView.setAdapter(mGitHubRepoListAdapter);

    }


    @Override
    public void updateGitHubRepoListView(List<GithubRepoEntity> githubRepoEntities) {
        mGitHubRepoListAdapter.setGithubRepoEntityList(githubRepoEntities);
        mGitHubRepoListAdapter.notifyDataSetChanged();
    }
}
