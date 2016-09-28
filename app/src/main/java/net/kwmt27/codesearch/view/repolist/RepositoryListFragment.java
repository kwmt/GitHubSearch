package net.kwmt27.codesearch.view.repolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.analytics.AnalyticsManager;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.entity.ItemType;
import net.kwmt27.codesearch.presenter.repolist.IRepositoryListPresenter;
import net.kwmt27.codesearch.presenter.repolist.RepositoryListPresenter;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.util.ToastUtil;
import net.kwmt27.codesearch.view.MainFragment;
import net.kwmt27.codesearch.view.detail.DetailActivity;
import net.kwmt27.codesearch.view.parts.DividerItemDecoration;

import java.util.List;

import rx.Subscription;


/**
 * レポジトリ一覧
 */
public class RepositoryListFragment extends Fragment implements RepositoryListPresenter.IRepositoryListView, MainFragment {

    public static final String TAG = RepositoryListFragment.class.getSimpleName();
    private IRepositoryListPresenter mPresenter;
    private RepositoryListAdapter mRepositoryListAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Subscription mSubscription;
    private boolean mIsCalled = false;

    private View mErrorLayout;
    private View mProgressLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mOnRefreshing = false;
    private boolean mIsAddedAd = false;

    public static RepositoryListFragment newInstance(boolean isAddedAd) {
        RepositoryListFragment fragment = new RepositoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(MainFragment.IS_ADDED_AD, isAddedAd);
        fragment.setArguments(bundle);
        return fragment;
    }

    public RepositoryListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsManager.getInstance(getActivity().getApplicationContext()).sendScreen(AnalyticsManager.Param.Screen.REPOSITORY_LIST);
        mPresenter = new RepositoryListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repository_list, container, false);
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
        // bottom navigationを切り替えるたびにFragmentがnewされるのでmIsAddedAdが初期化(false)され、広告が追加され増え続けるための対策
        mIsAddedAd = getArguments().getBoolean(MainFragment.IS_ADDED_AD);

        mProgressLayout = view.findViewById(R.id.progress_layout);
        mErrorLayout = view.findViewById(R.id.error_layout);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.github_repo_list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), R.drawable.divider));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRepositoryListAdapter = new RepositoryListAdapter(getActivity().getApplicationContext(), (adapter, position, repo, type) -> {
            if(type == ItemType.Normal) {
                AnalyticsManager.getInstance(getActivity().getApplicationContext())
                        .sendClickItem(AnalyticsManager.Param.Screen.REPOSITORY_LIST, AnalyticsManager.Param.Category.REPOSITORY, repo.getName());
                DetailActivity.startActivity(getActivity(), repo.getName(), repo.getHtmlUrl(), repo);
            }
            if (type == ItemType.Ad) {
                AnalyticsManager.getInstance(getActivity().getApplicationContext())
                        .sendClickItem(AnalyticsManager.Param.Screen.REPOSITORY_LIST, AnalyticsManager.Param.Category.Ads);
            }
        });
        mRecyclerView.setAdapter(mRepositoryListAdapter);

        rxRecyclerViewScrollSubscribe();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mOnRefreshing = true;
            mPresenter.onRefresh();
        });

    }



    private void rxRecyclerViewScrollSubscribe() {
        mSubscription = RxRecyclerView.scrollEvents(mRecyclerView).subscribe(event -> {
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
        );
    }


    @Override
    public void updateGitHubRepoListView(List<GithubRepoEntity> githubRepoEntities) {
        mIsCalled = false;
        rxRecyclerViewScrollSubscribe();
        mRepositoryListAdapter.setEntityList(githubRepoEntities);
        mRepositoryListAdapter.notifyDataSetChanged();


//        if(!mIsAddedAd || mOnRefreshing) {
//            mRepositoryListAdapter.addAdItemTypeThenNotify();
//            mIsAddedAd = true;
//        }
//        mOnRefreshing = false;
    }

    @Override
    public void showProgress() {
        mProgressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mErrorLayout.setVisibility(View.VISIBLE);

        Button button = (Button) mErrorLayout.findViewById(R.id.reload_button);
        button.setOnClickListener(view -> {
            mErrorLayout.setVisibility(View.GONE);
            mPresenter.onClickReloadButton();
        });

    }
    @Override
    public void showProgressOnScroll() {
        mRepositoryListAdapter.addProgressItemTypeThenNotify(new GithubRepoEntity());
    }

    @Override
    public void hideProgressOnScroll() {
        mRepositoryListAdapter.removeProgressItemTypeThenNotify();
    }

    @Override
    public void showErrorOnScroll() {
        ToastUtil.show(getActivity().getApplicationContext(), "データ取得に失敗しました。");
    }

    @Override
    public void hideSwipeRefreshLayout() {
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void moveToTop() {
        if(mRecyclerView == null) {
            return;
        }
        mRecyclerView.smoothScrollToPosition(0);

    }
}
