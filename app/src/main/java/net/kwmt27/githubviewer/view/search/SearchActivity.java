package net.kwmt27.githubviewer.view.search;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.entity.SearchCodeResultEntity;
import net.kwmt27.githubviewer.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubviewer.presenter.search.ISearchPresenter;
import net.kwmt27.githubviewer.presenter.search.SearchPresenter;
import net.kwmt27.githubviewer.view.BaseActivity;

public class SearchActivity extends BaseActivity implements SearchPresenter.ISearchView {


    public static void startActivity(AppCompatActivity activity, boolean canSearchCode) {
        SearchActivity.startActivity(activity, canSearchCode, null);
    }

    public static void startActivity(AppCompatActivity activity, boolean canSearchCode, GithubRepoEntity entity) {

        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(ISearchPresenter.CAN_SEARCH_CODE, canSearchCode);
        if (entity != null) {
            intent.putExtra(ISearchPresenter.REPO_ENTITY_KEY, entity);
        }
        activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }

    private ISearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mPresenter = new SearchPresenter(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void setupComponents() {
        setUpActionBar();

        EditText editText = (EditText) findViewById(R.id.search_edit);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // http://developer.android.com/reference/android/widget/TextView.OnEditorActionListener.html#onEditorAction(android.widget.TextView, int, android.view.KeyEvent)
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //KeyboardUtil.hideKeyboard(SearchActivity.this);
                    mPresenter.onEditorActionSearch(v.getText().toString());
                }
                return handled;
            }
        });

    }

    @Override
    public void updateSearchRepositoryResultView(SearchRepositoryResultEntity searchRepositoryResultEntity) {
        SearchRepositoryResultListFragment fragment = (SearchRepositoryResultListFragment) getSupportFragmentManager().findFragmentByTag(SearchRepositoryResultListFragment.TAG);
        fragment.updateSearchResultListView(searchRepositoryResultEntity);
    }

    @Override
    public void updateSearchCodeResultView(SearchCodeResultEntity entity) {
        SearchCodeResultListFragment fragment = (SearchCodeResultListFragment) getSupportFragmentManager().findFragmentByTag(SearchCodeResultListFragment.TAG);
        fragment.updateSearchResultListView(entity);

    }

}
