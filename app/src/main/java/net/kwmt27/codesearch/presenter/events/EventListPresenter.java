package net.kwmt27.codesearch.presenter.events;

import android.os.Bundle;
import android.view.View;

import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.model.rx.ApiSubscriber;
import net.kwmt27.codesearch.view.events.EventListFragment;

import java.util.List;

public class EventListPresenter implements IEventListPresenter {

    private IEventListView mEventListView;

    public EventListPresenter(IEventListView eventListView) {
        mEventListView = eventListView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mEventListView.setupComponents(view, savedInstanceState);
        fetchEventList(null, true);
    }

    @Override
    public void onStop() {
        ModelLocator.getEventModel().unsubscribe();
        // ModelLocator.getSearchRepositoryModel().clear();
    }


    @Override
    public void onClickReloadButton() {
        fetchEventList(null, true);
    }

    @Override
    public void onScrollToBottom() {
        if(ModelLocator.getEventModel().hasNextPage()) {
            fetchEventListOnScroll(ModelLocator.getEventModel().getNextPage());
        }
    }

    @Override
    public void onRefresh() {
        ModelLocator.getEventModel().clear();
        fetchEventList(null, false);
    }

    private void fetchEventList(Integer page, boolean show) {
        if(ModelLocator.getEventModel().hasEventList()) {
            mEventListView.updateEventListView(ModelLocator.getEventModel().getEventList());
            return;
        }
        fetchEventListImpl(false, page, show);
    }

    private void fetchEventListOnScroll(Integer page) {
        fetchEventListImpl(true, page, false);
    }

    private void fetchEventListImpl(boolean onScroll, Integer page, boolean show) {
        if(onScroll){
            mEventListView.showProgressOnScroll();
        }
        if(show) {
            mEventListView.showProgress();
        }
        ModelLocator.getEventModel().fetchEvent(page, new ApiSubscriber<List<EventEntity>>(((EventListFragment) mEventListView).getActivity()) {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                if(onScroll) {
                    mEventListView.hideProgressOnScroll();
                    mEventListView.showErrorOnScroll();
                    return;
                }
                mEventListView.hideProgress();
                mEventListView.hideSwipeRefreshLayout();
                mEventListView.showError();

            }

            @Override
            public void onNext(List<EventEntity> githubRepoEntities) {
                if(onScroll) {
                    mEventListView.hideProgressOnScroll();
                } else {
                    mEventListView.hideProgress();
                    mEventListView.hideSwipeRefreshLayout();
                }
                mEventListView.updateEventListView(githubRepoEntities);
            }
        });
    }


        public interface IEventListView {
        void setupComponents(View view, Bundle savedInstanceState);

        void updateEventListView(List<EventEntity> eventList);

        void showProgress();

        void hideProgress();

        void showError();

        void showProgressOnScroll();

        void hideProgressOnScroll();

        void showErrorOnScroll();

        void hideSwipeRefreshLayout();
    }

}
