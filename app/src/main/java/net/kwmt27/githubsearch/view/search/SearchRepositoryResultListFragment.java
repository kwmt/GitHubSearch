package net.kwmt27.githubsearch.view.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.kwmt27.githubsearch.R;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubsearch.presenter.search.ISearchResultListPresenter;
import net.kwmt27.githubsearch.presenter.search.SearchRepositoryResultListPresenter;
import net.kwmt27.githubsearch.view.DetailActivity;
import net.kwmt27.githubsearch.view.DividerItemDecoration;
import net.kwmt27.githubsearch.view.OnItemClickListener;


/**
 * レポジトリ検索結果一覧
 */
public class SearchRepositoryResultListFragment extends Fragment implements SearchRepositoryResultListPresenter.ISearchResultListView {


    public static final String TAG = SearchRepositoryResultListFragment.class.getSimpleName();
    private ISearchResultListPresenter mPresenter;
    private SearchRepositoryResultListAdapter mSearchRepositoryResultListAdapter;
    private RelativeLayout mNotFoundLayout;

    public static SearchRepositoryResultListFragment newInstance() {
        return new SearchRepositoryResultListFragment();
    }

    public SearchRepositoryResultListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchRepositoryResultListPresenter(this);
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
        mSearchRepositoryResultListAdapter = new SearchRepositoryResultListAdapter(getActivity().getApplicationContext(), new OnItemClickListener<SearchRepositoryResultListAdapter, GithubRepoEntity>() {
            @Override
            public void onItemClick(SearchRepositoryResultListAdapter adapter, int position, GithubRepoEntity repo) {
                DetailActivity.startActivity(getActivity(), repo.getName(), repo.getHtmlUrl(), repo);
            }
        });
        recyclerView.setAdapter(mSearchRepositoryResultListAdapter);

    }



    @Override
    public void updateSearchResultListView(SearchRepositoryResultEntity entity) {
        mSearchRepositoryResultListAdapter.setSearchResultList(entity.getGithubRepoEntityList());
        mSearchRepositoryResultListAdapter.notifyDataSetChanged();
    }

}
