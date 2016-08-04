package net.kwmt27.githubviewer.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.presenter.IMainPresenter;
import net.kwmt27.githubviewer.presenter.MainPresenter;
import net.kwmt27.githubviewer.util.Logger;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements MainPresenter.IMainView {

    private IMainPresenter mPresenter;
    private GitHubRepoListAdapter mGitHubRepoListAdapter;
    private Subscription mSubscription;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsCalled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);

        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        Logger.d("onStop is called.");
        mSubscription.unsubscribe();
        mPresenter.onStop();
        super.onStop();
    }




    @Override
    public void setupComponents() {
        setUpActionBar();

        setTitle("レポジトリ一覧");

        mRecyclerView = (RecyclerView) findViewById(R.id.github_repo_list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mGitHubRepoListAdapter = new GitHubRepoListAdapter(getApplicationContext(), new OnItemClickListener<GitHubRepoListAdapter, GithubRepoEntity>() {
            @Override
            public void onItemClick(GitHubRepoListAdapter adapter, int position, GithubRepoEntity repo) {
                DetailActivity.startActivity(MainActivity.this, repo.getName(), repo.getHtmlUrl(), repo);
            }
        });
        mRecyclerView.setAdapter(mGitHubRepoListAdapter);

        rxRecyclerViewScrollSubscribe();

    }

    private void rxRecyclerViewScrollSubscribe() {
        mSubscription = RxRecyclerView.scrollEvents(mRecyclerView).subscribe(
                new Action1<RecyclerViewScrollEvent>() {
                    @Override
                    public void call(RecyclerViewScrollEvent event) {
                        int totalItemCount = mLayoutManager.getItemCount();
                        int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                        if (totalItemCount - 1 <= lastVisibleItemPosition) {
                            if(mIsCalled){
                                return;
                            }
                            mIsCalled = true;
                            Logger.d("onLastVisible");
                            mSubscription.unsubscribe();
                            mPresenter.onScrollToBottom();
                        }
                    }
                }
        );
    }


    @Override
    public void updateGitHubRepoListView(List<GithubRepoEntity> githubRepoEntities) {
        mIsCalled = false;
        rxRecyclerViewScrollSubscribe();
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
