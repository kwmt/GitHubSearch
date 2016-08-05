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

import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import net.kwmt27.githubsearch.R;
import net.kwmt27.githubsearch.entity.GithubRepoEntity;
import net.kwmt27.githubsearch.entity.SearchRepositoryResultEntity;
import net.kwmt27.githubsearch.presenter.search.ISearchResultListPresenter;
import net.kwmt27.githubsearch.presenter.search.SearchRepositoryResultListPresenter;
import net.kwmt27.githubsearch.util.Logger;
import net.kwmt27.githubsearch.util.ToastUtil;
import net.kwmt27.githubsearch.view.DetailActivity;
import net.kwmt27.githubsearch.view.DividerItemDecoration;
import net.kwmt27.githubsearch.view.OnItemClickListener;

import rx.Subscription;
import rx.functions.Action1;


/**
 * レポジトリ検索結果一覧
 */
public class SearchRepositoryResultListFragment extends Fragment implements SearchRepositoryResultListPresenter.ISearchResultListView {


    public static final String TAG = SearchRepositoryResultListFragment.class.getSimpleName();
    private ISearchResultListPresenter mPresenter;
    private SearchRepositoryResultListAdapter mSearchRepositoryResultListAdapter;
    private RelativeLayout mNotFoundLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Subscription mSubscription;
    private boolean mIsCalled = false;

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.search_result_list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, R.drawable.divider));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSearchRepositoryResultListAdapter = new SearchRepositoryResultListAdapter(getActivity().getApplicationContext(), new OnItemClickListener<SearchRepositoryResultListAdapter, GithubRepoEntity>() {
            @Override
            public void onItemClick(SearchRepositoryResultListAdapter adapter, int position, GithubRepoEntity repo) {
                DetailActivity.startActivity(getActivity(), repo.getName(), repo.getHtmlUrl(), repo);
            }
        });
        mRecyclerView.setAdapter(mSearchRepositoryResultListAdapter);
        rxRecyclerViewScrollSubscribe();
    }



    @Override
    public void updateSearchResultListView(SearchRepositoryResultEntity entity) {
        mSearchRepositoryResultListAdapter.setSearchResultList(entity.getGithubRepoEntityList());
        mSearchRepositoryResultListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressOnScroll() {
        mSearchRepositoryResultListAdapter.addProgressItemTypeThenNotify();
    }

    @Override
    public void hideProgressOnScroll() {
        mSearchRepositoryResultListAdapter.removeProgressItemTypeThenNotify();
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
