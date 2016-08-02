package net.kwmt27.githubviewer.view.search;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.entity.SearchCodeResultEntity;
import net.kwmt27.githubviewer.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubviewer.presenter.search.ISearchPresenter;
import net.kwmt27.githubviewer.presenter.search.SearchPresenter;
import net.kwmt27.githubviewer.util.KeyboardUtil;
import net.kwmt27.githubviewer.view.BaseActivity;

public class SearchActivity extends BaseActivity implements SearchPresenter.ISearchView {


    private MenuItem mActionClearMenu;
    private EditText mSearchEditText;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        mActionClearMenu = menu.findItem(R.id.action_clear);
        mActionClearMenu.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_clear:
                mSearchEditText.getText().clear();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setupComponents() {
        setUpActionBar();

        mSearchEditText = (EditText) findViewById(R.id.search_edit);
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // http://developer.android.com/reference/android/widget/TextView.OnEditorActionListener.html#onEditorAction(android.widget.TextView, int, android.view.KeyEvent)
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //KeyboardUtil.hideKeyboard(SearchActivity.this);
                    KeyboardUtil.hideKeyboard(SearchActivity.this);
                    mPresenter.onEditorActionSearch(v.getText().toString());
                }
                return handled;
            }
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                mActionClearMenu.setVisible(s.length() > 0);
            }
        });


    }

    @Override
    public void updateSearchRepositoryResultView(SearchRepositoryResultEntity entity) {
        showNotFoundPageIfNeeded(entity.foundResult());
        SearchRepositoryResultListFragment fragment = (SearchRepositoryResultListFragment) getSupportFragmentManager().findFragmentByTag(SearchRepositoryResultListFragment.TAG);
        fragment.updateSearchResultListView(entity);
    }

    @Override
    public void updateSearchCodeResultView(SearchCodeResultEntity entity) {
        showNotFoundPageIfNeeded(entity.foundResult());
        SearchCodeResultListFragment fragment = (SearchCodeResultListFragment) getSupportFragmentManager().findFragmentByTag(SearchCodeResultListFragment.TAG);
        fragment.updateSearchResultListView(entity);
    }

    private void showNotFoundPageIfNeeded(boolean show) {
        RelativeLayout notFoundLayout = (RelativeLayout) findViewById(R.id.not_found_layout);
        ((TextView)notFoundLayout.findViewById(R.id.keyword)).setText(ModelLocator.getGithubService().getKeyword());
        int visibility = show ? View.GONE : View.VISIBLE;
        notFoundLayout.setVisibility(visibility);
    }
}
