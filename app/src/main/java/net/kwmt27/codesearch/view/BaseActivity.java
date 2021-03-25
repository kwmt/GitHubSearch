package net.kwmt27.codesearch.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.view.search.SearchActivity;

/**
 * 戻るボタンがある共通Activity
 */
public class BaseActivity extends AppCompatActivity {

    public static final String TITLE_KEY = "title_key";


    protected void setUpActionBar() {
        setUpActionBar(true);
    }

    protected void setUpActionBar(boolean showHomeAsUp) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        String title = getIntent().getStringExtra(TITLE_KEY);
        setTitle(title);
        setDisplayHomeAsUpEnabled(showHomeAsUp);

    }

    protected void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        if (title == null) {
            return;
        }
        actionBar.setTitle(title);
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
