package net.kwmt27.githubviewer.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.presenter.IMainPresenter;
import net.kwmt27.githubviewer.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainPresenter.IMainView {

    private IMainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);

        setupComponents();
    }

    private void setupComponents() {
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onGetClick();
            }
        });

        Button clearButton = (Button)findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClearClick();
            }
        });
    }


    @Override
    public void updateTextView(String str) {
        TextView tv = (TextView) findViewById(R.id.text_view);
        tv.setText(str);
    }
}
