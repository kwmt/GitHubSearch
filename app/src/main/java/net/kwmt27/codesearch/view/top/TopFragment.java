package net.kwmt27.codesearch.view.top;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.analytics.AnalyticsManager;
import net.kwmt27.codesearch.view.login.LoginActivity;
import net.kwmt27.codesearch.view.search.SearchActivity;


/**
 * トップ画面
 */
public class TopFragment extends Fragment { //implements TopPresenter.ITopView {

    public static TopFragment newInstance() {
        return new TopFragment();
    }

    public TopFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsManager.getInstance(getActivity().getApplicationContext()).sendScreen(AnalyticsManager.Param.Screen.TOP);
        //mPresenter = new RepositoryListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //mPresenter.onViewCreated(view, savedInstanceState);

        final Button searchGithub = (Button)view.findViewById(R.id.search_github);
        searchGithub.setOnClickListener(view1 -> {
            AnalyticsManager.getInstance(getActivity().getApplicationContext())
                    .sendClickButton(AnalyticsManager.Param.Screen.TOP, AnalyticsManager.Param.Widget.SEARCH_BUTTON);
            SearchActivity.startActivity(getActivity(), false, null, searchGithub, "search_bar");
        });

        Button button = (Button) view.findViewById(R.id.login_with_github);
        button.setOnClickListener(view2 -> {
            AnalyticsManager.getInstance(getActivity().getApplicationContext())
                    .sendClickButton(AnalyticsManager.Param.Screen.TOP, AnalyticsManager.Param.Widget.LOGIN_WITH_GITHUB_BUTTON);
            Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, 0);
        });

    }

    @Override
    public void onStop() {
        //mSubscription.unsubscribe();
        //mPresenter.onStop();
        super.onStop();
    }


}
