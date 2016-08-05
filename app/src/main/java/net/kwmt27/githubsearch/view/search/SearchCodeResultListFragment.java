package net.kwmt27.githubsearch.view.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import net.kwmt27.githubsearch.R;
import net.kwmt27.githubsearch.entity.ItemEntity;
import net.kwmt27.githubsearch.entity.SearchCodeResultEntity;
import net.kwmt27.githubsearch.presenter.search.ISearchResultListPresenter;
import net.kwmt27.githubsearch.presenter.search.SearchCodeResultListPresenter;
import net.kwmt27.githubsearch.util.Logger;
import net.kwmt27.githubsearch.util.ToastUtil;
import net.kwmt27.githubsearch.view.DetailActivity;
import net.kwmt27.githubsearch.view.DividerItemDecoration;
import net.kwmt27.githubsearch.view.OnItemClickListener;

import rx.Subscription;
import rx.functions.Action1;


/**
 * コード検索結果一覧
 */
public class SearchCodeResultListFragment extends Fragment implements SearchCodeResultListPresenter.ISearchResultListView {


    public static final String TAG = SearchCodeResultListFragment.class.getSimpleName();
    private ISearchResultListPresenter mPresenter;
    private SearchCodeResultListAdapter mSearchCodeResultListAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Subscription mSubscription;
    private boolean mIsCalled = false;

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
        mSubscription.unsubscribe();
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void setupComponents(View view, Bundle savedInstanceState) {
        final Context context = getActivity().getApplicationContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.search_result_list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, R.drawable.divider));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSearchCodeResultListAdapter = new SearchCodeResultListAdapter(getActivity().getApplicationContext(), new OnItemClickListener<SearchCodeResultListAdapter, ItemEntity>() {
            @Override
            public void onItemClick(SearchCodeResultListAdapter adapter, int position, ItemEntity item) {
                Logger.d("onItemClick");
                DetailActivity.startActivity(getActivity(), item.getName(), item.getHtmlUrl(), item.getRepository());
            }
        });
        mRecyclerView.setAdapter(mSearchCodeResultListAdapter);
        rxRecyclerViewScrollSubscribe();
    }

    @Override
    public void updateSearchResultListView(SearchCodeResultEntity searchCodeResultEntity) {
        mSearchCodeResultListAdapter.setSearchResultList(searchCodeResultEntity.getItemEntityList());
        mSearchCodeResultListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressOnScroll() {
        mSearchCodeResultListAdapter.addProgressItemTypeThenNotify();
    }

    @Override
    public void hideProgressOnScroll() {
        mSearchCodeResultListAdapter.removeProgressItemTypeThenNotify();
    }

    @Override
    public void showErrorOnScroll() {
        ToastUtil.show(getActivity().getApplicationContext(), "データ取得に失敗しました。");
    }

    private void rxRecyclerViewScrollSubscribe() {
        mSubscription = RxRecyclerView.scrollEvents(mRecyclerView).subscribe(
                new Action1<RecyclerViewScrollEvent>() {
                    @Override
                    public void call(RecyclerViewScrollEvent event) {
                        int totalItemCount = mLayoutManager.getItemCount();
                        int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                        if (totalItemCount - 1 <= lastVisibleItemPosition) {
                            if(mIsCalled){
                                return;
                            }
                            mIsCalled = true;
                            Logger.d("onLastVisible");
                            mSubscription.unsubscribe();
                            mPresenter.onScrollToBottom();
                        }
                    }
                }
        );
    }


}
