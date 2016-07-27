package net.kwmt27.githubviewer.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.presenter.ISearchResultListPresenter;
import net.kwmt27.githubviewer.presenter.SearchResultListPresenter;


/**
 * 検索結果一覧
 */
public class SearchResultListFragment extends Fragment implements SearchResultListPresenter.ISearchResultListView {


    private ISearchResultListPresenter mPresenter;

    public static SearchResultListFragment newInstance(RecyclerView.OnScrollListener onScrollListener) {
        return new SearchResultListFragment();
    }

    public SearchResultListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchResultListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void setupComponents(View view, Bundle savedInstanceState) {
        final Context context = getActivity().getApplicationContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.search_result_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, R.drawable.divider));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        mGitHubRepoListAdapter = new GitHubRepoListAdapter(getActivity().getApplicationContext(), new OnItemClickListener<GitHubRepoListAdapter, GithubRepoEntity>() {
//            @Override
//            public void onItemClick(GitHubRepoListAdapter adapter, int position, GithubRepoEntity repo) {
//
//            }
//        });
//        recyclerView.setAdapter(mGitHubRepoListAdapter);

    }

    @Override
    public void updateDetailView(GithubRepoEntity githubRepoEntity) {

    }

}
