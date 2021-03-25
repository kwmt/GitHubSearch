package net.kwmt27.codesearch.view.search;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.analytics.AnalyticsManager;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.entity.ItemEntity;
import net.kwmt27.codesearch.entity.ItemType;
import net.kwmt27.codesearch.presenter.search.ISearchResultListPresenter;
import net.kwmt27.codesearch.presenter.search.SearchCodeResultListPresenter;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.util.ToastUtil;
import net.kwmt27.codesearch.view.customtabs.ChromeCustomTabs;
import net.kwmt27.codesearch.view.parts.DividerItemDecoration;
import net.kwmt27.codesearch.view.parts.OnItemClickListener;

import java.util.List;

import rx.Subscription;


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

    private FragmentProgressCallback mCallback;
    private View mErrorLayout;

    public static SearchCodeResultListFragment newInstance(FragmentProgressCallback callback) {
        SearchCodeResultListFragment fragment = new SearchCodeResultListFragment();
        fragment.setCallback(callback);
        return fragment;
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
    public void onDestroyView() {
        mSubscription.unsubscribe();
        mPresenter.onDestroyView();
        super.onDestroyView();
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
            public void onItemClick(SearchCodeResultListAdapter adapter, int position, ItemEntity item, ItemType type) {
                if(type == ItemType.Normal) {
                    AnalyticsManager.getInstance(getActivity().getApplicationContext())
                            .sendClickItem(AnalyticsManager.Param.Screen.SEARCH_CODE_RESULT_LIST, AnalyticsManager.Param.Category.CODE, item.getHtmlUrl());
                    //DetailActivity.startActivity(getActivity(), item.getName(), item.getHtmlUrl(), item.getRepository());
                    ChromeCustomTabs.open(SearchCodeResultListFragment.this.getActivity(), item.getHtmlUrl(), item.getRepository(), true);

                }
                if(type == ItemType.Ad) {
                    AnalyticsManager.getInstance(getActivity().getApplicationContext())
                            .sendClickItem(AnalyticsManager.Param.Screen.SEARCH_CODE_RESULT_LIST, AnalyticsManager.Param.Category.Ads);
                }
            }
        });
        mRecyclerView.setAdapter(mSearchCodeResultListAdapter);
        rxRecyclerViewScrollSubscribe();

        mErrorLayout = view.findViewById(R.id.error_layout);

    }

    @Override
    public void updateSearchResultListView(List<ItemEntity> itemEntityList) {
        mIsCalled = false;
        rxRecyclerViewScrollSubscribe();
        mCallback.showNotFoundPageIfNeeded(ModelLocator.getSearchCodeModel(), itemEntityList.size() > 0);

        if (itemEntityList.size() > 0) {
            mSearchCodeResultListAdapter.setEntityList(itemEntityList);
            mSearchCodeResultListAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onEditorActionSearch(String keyword, String repositoryFullName, GithubRepoEntity entity) {
        mPresenter.onEditorActionSearch(keyword, repositoryFullName, null);
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
        AnalyticsManager.getInstance(getActivity().getApplicationContext()).sendScreen(AnalyticsManager.Param.Screen.ERROR_PAGE);
        mErrorLayout.setVisibility(View.VISIBLE);

        Button button = (Button) mErrorLayout.findViewById(R.id.reload_button);
        button.setOnClickListener(view -> {
            AnalyticsManager.getInstance(getActivity().getApplicationContext())
                    .sendClickItem(AnalyticsManager.Param.Screen.ERROR_PAGE, AnalyticsManager.Param.Category.ERROR_PAGE, AnalyticsManager.Param.Widget.RELOAD_BUTTON);
            mErrorLayout.setVisibility(View.GONE);
            mPresenter.onClickReloadButton();
        });

    }
    @Override
    public void showProgressOnScroll() {
        mSearchCodeResultListAdapter.addProgressItemTypeThenNotify(new ItemEntity());
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

    private void setCallback(FragmentProgressCallback callback) {
        mCallback = callback;
    }
}
