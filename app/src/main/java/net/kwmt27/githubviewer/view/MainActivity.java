package net.kwmt27.githubviewer.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.presenter.IMainPresenter;
import net.kwmt27.githubviewer.presenter.MainPresenter;

import java.util.List;

public class MainActivity extends BaseActivity implements MainPresenter.IMainView {

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
        setUpActionBar();

        setTitle("レポジトリ一覧");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.github_repo_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mGitHubRepoListAdapter = new GitHubRepoListAdapter(getApplicationContext(), new OnItemClickListener<GitHubRepoListAdapter, GithubRepoEntity>() {
            @Override
            public void onItemClick(GitHubRepoListAdapter adapter, int position, GithubRepoEntity repo) {
                DetailActivity.startActivity(MainActivity.this, repo.getName(), repo.getHtmlUrl(), repo);
            }
        });
        recyclerView.setAdapter(mGitHubRepoListAdapter);

    }


    @Override
    public void updateGitHubRepoListView(List<GithubRepoEntity> githubRepoEntities) {
        mGitHubRepoListAdapter.setGithubRepoEntityList(githubRepoEntities);
        mGitHubRepoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        findViewById(R.id.progress_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        findViewById(R.id.progress_layout).setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        final View errorLayout = findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.VISIBLE);

        Button button = (Button)errorLayout.findViewById(R.id.reload_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorLayout.setVisibility(View.GONE);
                mPresenter.onClickReloadButton();
            }
        });

    }
}
