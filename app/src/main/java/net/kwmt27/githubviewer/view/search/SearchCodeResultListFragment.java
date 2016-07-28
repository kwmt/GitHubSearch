package net.kwmt27.githubviewer.view.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.ItemEntity;
import net.kwmt27.githubviewer.entity.SearchCodeResultEntity;
import net.kwmt27.githubviewer.presenter.search.ISearchResultListPresenter;
import net.kwmt27.githubviewer.presenter.search.SearchCodeResultListPresenter;
import net.kwmt27.githubviewer.view.DividerItemDecoration;
import net.kwmt27.githubviewer.view.OnItemClickListener;


/**
 * コード検索結果一覧
 */
public class SearchCodeResultListFragment extends Fragment implements SearchCodeResultListPresenter.ISearchResultListView {


    public static final String TAG = "SearchCodeResultListFragment";
    private ISearchResultListPresenter mPresenter;
    private SearchCodeResultListAdapter mSearchCodeResultListAdapter;

    public static SearchCodeResultListFragment newInstance() {
        return new SearchCodeResultListFragment();
    }

    public SearchCodeResultListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchCodeResultListPresenter(this);
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
        mSearchCodeResultListAdapter = new SearchCodeResultListAdapter(getActivity().getApplicationContext(), new OnItemClickListener<SearchCodeResultListAdapter, ItemEntity>() {
            @Override
            public void onItemClick(SearchCodeResultListAdapter adapter, int position, ItemEntity repo) {

            }
        });
        recyclerView.setAdapter(mSearchCodeResultListAdapter);

    }

    @Override
    public void updateSearchResultListView(SearchCodeResultEntity searchCodeResultEntity) {
        mSearchCodeResultListAdapter.setSearchResultList(searchCodeResultEntity.getItemEntityList());
        mSearchCodeResultListAdapter.notifyDataSetChanged();
    }

}
