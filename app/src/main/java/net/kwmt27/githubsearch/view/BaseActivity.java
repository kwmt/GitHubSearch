package net.kwmt27.githubsearch.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.kwmt27.githubsearch.R;
import net.kwmt27.githubsearch.view.search.SearchActivity;

/**
 * 戻るボタンがある共通Activity
 */
public class BaseActivity extends AppCompatActivity {

    public static final String TITLE_KEY = "title_key";


    protected void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        String title = getIntent().getStringExtra(TITLE_KEY);
        setTitle(title);
        setDisplayHomeAsUpEnabled(true);
    }

    protected void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repository_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_search:
                SearchActivity.startActivity(this, false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
