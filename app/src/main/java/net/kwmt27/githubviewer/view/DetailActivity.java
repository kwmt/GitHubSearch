package net.kwmt27.githubviewer.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.presenter.DetailPresenter;
import net.kwmt27.githubviewer.presenter.IDetailPresenter;

public class DetailActivity extends BaseActivity implements DetailPresenter.IDetailView {


    public static void startActivity(AppCompatActivity activity, String title, GithubRepoEntity repo) {
        Intent intent = new Intent(activity.getApplicationContext(), DetailActivity.class);
        intent.putExtra(TITLE_KEY, title);
        intent.putExtra(IDetailPresenter.OWENER_KEY,  repo.getOwner().getLogin());
        intent.putExtra(IDetailPresenter.REPO_KEY, repo.getName());
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
    public boolean onCreateOptionsMenu(Menu menu) {

        if(ModelLocator.getGithubService().getGitHubRepo() == null){
            MenuItem searchMenu = menu.findItem(R.id.action_search);
            if(searchMenu == null) { return true; }
            searchMenu.setEnabled(ModelLocator.getGithubService().getGitHubRepo() != null);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                SearchActivity.startActivity(this, true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setupComponents() {
        setUpActionBar();
    }

    @Override
    public void updateDetailView(GithubRepoEntity githubRepoEntity) {
        invalidateOptionsMenu();
    }
}
