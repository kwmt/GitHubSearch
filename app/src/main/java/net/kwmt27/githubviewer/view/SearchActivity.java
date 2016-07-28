package net.kwmt27.githubviewer.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubviewer.presenter.ISearchPresenter;
import net.kwmt27.githubviewer.presenter.SearchPresenter;

public class SearchActivity extends BaseActivity implements SearchPresenter.ISearchView {

    public static void startActivity(AppCompatActivity activity) {

        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
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

        EditText editText = (EditText)findViewById(R.id.search_edit);
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
    public void updateSearchResultView(SearchRepositoryResultEntity searchRepositoryResultEntity) {
        SearchRepositoryResultListFragment fragment = (SearchRepositoryResultListFragment) getSupportFragmentManager().findFragmentByTag(SearchRepositoryResultListFragment.TAG);
        fragment.updateSearchResultListView(searchRepositoryResultEntity);
    }

}
