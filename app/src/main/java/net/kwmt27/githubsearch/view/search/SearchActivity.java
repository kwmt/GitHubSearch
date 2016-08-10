package net.kwmt27.githubsearch.view.search;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import net.kwmt27.githubsearch.ModelLocator;
import net.kwmt27.githubsearch.R;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.presenter.search.ISearchPresenter;
import net.kwmt27.githubsearch.presenter.search.SearchPresenter;
import net.kwmt27.githubsearch.util.KeyboardUtil;
import net.kwmt27.githubsearch.view.BaseActivity;

public class SearchActivity extends BaseActivity implements SearchPresenter.ISearchView, FragmentProgressCallback {


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
    public static void startActivity(AppCompatActivity activity, boolean canSearchCode,  GithubRepoEntity entity, View sharedElement, String sharedElementName) {

        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(ISearchPresenter.CAN_SEARCH_CODE, canSearchCode);
        if (entity != null) {
            intent.putExtra(ISearchPresenter.REPO_ENTITY_KEY, entity);
        }
        activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName).toBundle());
    }
    public static void startActivity(Activity activity, boolean canSearchCode, GithubRepoEntity entity, View sharedElement, String sharedElementName) {

        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(ISearchPresenter.CAN_SEARCH_CODE, canSearchCode);
        if (entity != null) {
            intent.putExtra(ISearchPresenter.REPO_ENTITY_KEY, entity);
        }
        activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName).toBundle());
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

        Intent intent = getIntent();
        final boolean canSearchCode = intent.getBooleanExtra(ISearchPresenter.CAN_SEARCH_CODE, false);


        final FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (canSearchCode) {
            transaction.replace(R.id.container, SearchCodeResultListFragment.newInstance(this), SearchCodeResultListFragment.TAG);
        } else {
            transaction.replace(R.id.container, SearchRepositoryResultListFragment.newInstance(this), SearchRepositoryResultListFragment.TAG);
        }
        transaction.commit();

        mSearchEditText = (EditText) findViewById(R.id.search_edit);
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // http://developer.android.com/reference/android/widget/TextView.OnEditorActionListener.html#onEditorAction(android.widget.TextView, int, android.view.KeyEvent)
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtil.hideKeyboard(SearchActivity.this);
                    if(v.getText().toString().length() <= 0) {
                        return false;
                    }
                    GithubRepoEntity repo = (GithubRepoEntity) getIntent().getSerializableExtra(ISearchPresenter.REPO_ENTITY_KEY);
                    if (canSearchCode) {
                        SearchCodeResultListFragment fragment = (SearchCodeResultListFragment) manager.findFragmentByTag(SearchCodeResultListFragment.TAG);
                        fragment.onEditorActionSearch(v.getText().toString(), repo);
                    } else {
                        SearchRepositoryResultListFragment fragment = (SearchRepositoryResultListFragment) manager.findFragmentByTag(SearchRepositoryResultListFragment.TAG);
                        fragment.onEditorActionSearch(v.getText().toString(), repo);
                    }
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
    public void showProgress() {
        findViewById(R.id.progress_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        findViewById(R.id.progress_layout).setVisibility(View.GONE);
    }


    @Override
    public void showNotFoundPageIfNeeded(boolean show) {
        RelativeLayout notFoundLayout = (RelativeLayout) findViewById(R.id.not_found_layout);
        ((TextView)notFoundLayout.findViewById(R.id.keyword)).setText(ModelLocator.getSearchModel().getKeyword());
        int visibility = show ? View.GONE : View.VISIBLE;
        notFoundLayout.setVisibility(visibility);

    }

}
