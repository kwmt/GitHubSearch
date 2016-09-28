package net.kwmt27.codesearch.view.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.analytics.AnalyticsManager;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.entity.ItemType;
import net.kwmt27.codesearch.presenter.search.ISearchResultListPresenter;
import net.kwmt27.codesearch.presenter.search.SearchRepositoryResultListPresenter;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.util.ToastUtil;
import net.kwmt27.codesearch.view.detail.DetailActivity;
import net.kwmt27.codesearch.view.parts.DividerItemDecoration;

import java.util.List;

import rx.Subscription;


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
    private FragmentProgressCallback mCallback;
    private View mErrorLayout;

    public static SearchRepositoryResultListFragment newInstance(FragmentProgressCallback callback) {
        SearchRepositoryResultListFragment fragment = new SearchRepositoryResultListFragment();
        fragment.setFragmentProgressCallback(callback);
        return fragment;
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
        mSearchRepositoryResultListAdapter = new SearchRepositoryResultListAdapter(getActivity().getApplicationContext(), (adapter, position, repo, type) -> {
            if(type == ItemType.Normal) {
                AnalyticsManager.getInstance(getActivity().getApplicationContext())
                        .sendClickItem(AnalyticsManager.Param.Screen.SEARCH_REPOSITORY_RESULT_LIST, AnalyticsManager.Param.Category.REPOSITORY, repo.getName());
                DetailActivity.startActivity(getActivity(), repo.getName(), repo.getHtmlUrl(), repo);
            }
            if(type == ItemType.Ad) {
                AnalyticsManager.getInstance(getActivity().getApplicationContext())
                        .sendClickItem(AnalyticsManager.Param.Screen.SEARCH_REPOSITORY_RESULT_LIST, AnalyticsManager.Param.Category.Ads);
            }
        });
        mRecyclerView.setAdapter(mSearchRepositoryResultListAdapter);
        rxRecyclerViewScrollSubscribe();

        mErrorLayout = view.findViewById(R.id.error_layout);

    }



    @Override
    public void updateSearchResultListView(List<GithubRepoEntity> entities) {
        mIsCalled = false;
        rxRecyclerViewScrollSubscribe();
        mCallback.showNotFoundPageIfNeeded(ModelLocator.getSearchRepositoryModel(), entities.size() > 0);
        if (entities.size() > 0) {
            mSearchRepositoryResultListAdapter.setEntityList(entities);
            mSearchRepositoryResultListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onEditorActionSearch(String keyword, GithubRepoEntity entity) {
        mPresenter.onEditorActionSearch(keyword, entity);
    }

    @Override
    public void showProgress() {
        mCallback.showProgress();
    }

    @Override
    public void hideProgress() {
        mCallback.hideProgress();
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
        mSearchRepositoryResultListAdapter.addProgressItemTypeThenNotify(new GithubRepoEntity());
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
        mSubscription = RxRecyclerView.scrollEvents(mRecyclerView).subscribe(event -> {
                    int totalItemCount = mLayoutManager.getItemCount();
                    int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                    if (totalItemCount - 1 <= lastVisibleItemPosition) {
                        if (mIsCalled) {
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

    public void setFragmentProgressCallback(FragmentProgressCallback fragmentProgressCallback) {
        mCallback = fragmentProgressCallback;
    }
}
