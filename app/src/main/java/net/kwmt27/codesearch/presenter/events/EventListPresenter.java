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
        fetchEventList(null);
    }

    @Override
    public void onStop() {
        ModelLocator.getEventModel().unsubscribe();
        // ModelLocator.getSearchRepositoryModel().clear();
    }


    @Override
    public void onClickReloadButton() {
        fetchEventList(null);
    }

    @Override
    public void onScrollToBottom() {
        if(ModelLocator.getEventModel().hasNextPage()) {
            fetchEventListOnScroll(ModelLocator.getEventModel().getNextPage());
        }
    }

    private void fetchEventList(Integer page) {
        if(ModelLocator.getEventModel().hasEventList()) {
            mEventListView.updateEventListView(ModelLocator.getEventModel().getEventList());
            return;
        }


        mEventListView.showProgress();
        String user = "kwmt"; // TODO
        ModelLocator.getEventModel().fetchEvent(user, page, new ApiSubscriber<List<EventEntity>>(((EventListFragment) mEventListView).getActivity()) {
            @Override
            public void onCompleted() {
                mEventListView.hideProgress();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                mEventListView.hideProgress();
                mEventListView.showError();
            }

            @Override
            public void onNext(List<EventEntity> githubRepoEntities) {
                mEventListView.updateEventListView(githubRepoEntities);
            }
        });
    }

    private void fetchEventListOnScroll(Integer page) {
        mEventListView.showProgressOnScroll();
        String user = "kwmt"; // TODO
        ModelLocator.getEventModel().fetchEvent(user, page, new ApiSubscriber<List<EventEntity>>(((EventListFragment) mEventListView).getActivity()) {
            @Override
            public void onCompleted() {
                mEventListView.hideProgressOnScroll();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                mEventListView.hideProgressOnScroll();
                mEventListView.showErrorOnScroll();
            }

            @Override
            public void onNext(List<EventEntity> githubRepoEntities) {
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

    }

}
