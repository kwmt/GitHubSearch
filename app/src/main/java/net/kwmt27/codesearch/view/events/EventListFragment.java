package net.kwmt27.codesearch.view.events;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.analytics.AnalyticsManager;
import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.presenter.events.EventListPresenter;
import net.kwmt27.codesearch.presenter.events.IEventListPresenter;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.util.ToastUtil;
import net.kwmt27.codesearch.view.customtabs.ChromeCustomTabs;
import net.kwmt27.codesearch.view.MainFragment;
import net.kwmt27.codesearch.view.parts.DividerItemDecoration;

import java.util.List;

import rx.Subscription;


/**
 * レポジトリ一覧
 */
public class EventListFragment extends Fragment implements EventListPresenter.IEventListView, MainFragment {

    public interface OnLinkClickListener {
        /**
         *
         * @param title
         * @param url
         * @param githubRepoEntity nullならレポジトリ検索できないので、検索メニューを非表示にする
         */
        void onLinkClick(String title, String url, GithubRepoEntity githubRepoEntity);
    }

    public static final String TAG = EventListFragment.class.getSimpleName();
    private IEventListPresenter mPresenter;
    private EventListAdapter mEventListAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Subscription mSubscription;
    private boolean mIsCalled = false;

    private View mErrorLayout;
    private View mProgressLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static EventListFragment newInstance(boolean isAddedAd) {
        EventListFragment fragment = new EventListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(MainFragment.IS_ADDED_AD, isAddedAd);
        fragment.setArguments(bundle);
        return fragment;
    }

    public EventListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsManager.getInstance(getActivity().getApplicationContext()).sendScreen(AnalyticsManager.Param.Screen.EVENT_LIST);
        mPresenter = new EventListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.onViewCreated(view, savedInstanceState);
        mSubscription = subscribeRxRecyclerViewScroll();
    }

    @Override
    public void onDestroyView() {
        mSubscription.unsubscribe();
        mPresenter.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void setupComponents(View view, Bundle savedInstanceState) {

        mProgressLayout = view.findViewById(R.id.progress_layout);
        mErrorLayout = view.findViewById(R.id.error_layout);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.event_list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), R.drawable.divider));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mEventListAdapter = new EventListAdapter(getActivity().getApplicationContext(), new OnLinkClickListener() {
            @Override
            public void onLinkClick(String title, String url, GithubRepoEntity githubRepoEntity) {
                AnalyticsManager.getInstance(getContext()).sendClickItem(AnalyticsManager.Param.Screen.EVENT_LIST, AnalyticsManager.Param.Action.CLICK_LINK, url);
                //DetailActivity.startActivity(EventListFragment.this.getActivity(), title, url, githubRepoEntity);
                ChromeCustomTabs.open(EventListFragment.this.getActivity(), url, githubRepoEntity, true);
            }
        });
        mRecyclerView.setAdapter(mEventListAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.onRefresh();
        });
    }



    private Subscription subscribeRxRecyclerViewScroll() {
        return RxRecyclerView.scrollEvents(mRecyclerView).subscribe(event -> {
                    int totalItemCount = mLayoutManager.getItemCount();
                    int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                    if (totalItemCount - 1 <= lastVisibleItemPosition) {
                        if(mIsCalled){
                            return;
                        }
                        mIsCalled = true;
                        Logger.d("onLastVisible");
                        mPresenter.onScrollToBottom();
                    }
                }
        );
    }


    @Override
    public void updateEventListView(List<EventEntity> entityList) {
        mIsCalled = false;
        mEventListAdapter.setEntityList(entityList);
        mEventListAdapter.notifyDataSetChanged();
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
        mEventListAdapter.addProgressItemTypeThenNotify(new EventEntity());
    }

    @Override
    public void hideProgressOnScroll() {
        mEventListAdapter.removeProgressItemTypeThenNotify();
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
