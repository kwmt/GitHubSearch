package net.kwmt27.codesearch.view.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.ItemEntity;
import net.kwmt27.codesearch.entity.ItemType;
import net.kwmt27.codesearch.entity.MatchEntity;
import net.kwmt27.codesearch.entity.TextMatchEntity;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.util.RoundedBackgroundSpan;
import net.kwmt27.codesearch.view.BaseRecyclerAdapter;
import net.kwmt27.codesearch.view.parts.DividerItemDecoration;
import net.kwmt27.codesearch.view.parts.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class SearchCodeResultListAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, ItemEntity> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private OnItemClickListener<SearchCodeResultListAdapter, ItemEntity> mListener;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ChildSearchCodeResultListAdapter mChildAdapter;
        TextView nameTextView;
        TextView pathTextView;
        RecyclerView textMatchRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            pathTextView = (TextView) itemView.findViewById(R.id.path);
            textMatchRecyclerView = (RecyclerView) itemView.findViewById(R.id.text_match_list);
            textMatchRecyclerView.addItemDecoration(new DividerItemDecoration(itemView.getContext(), R.drawable.divider));
            RecyclerView.LayoutManager layout = new LinearLayoutManager(itemView.getContext());
            layout.setAutoMeasureEnabled(true);
            textMatchRecyclerView.setLayoutManager(layout);
            textMatchRecyclerView.setHasFixedSize(false);
            mChildAdapter = new ChildSearchCodeResultListAdapter(itemView.getContext(), null);

        }
    }

    public static class AdViewHolder extends RecyclerView.ViewHolder {

        public AdViewHolder(View itemView) {
            super(itemView);
        }
    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }



    public SearchCodeResultListAdapter(Context context, OnItemClickListener<SearchCodeResultListAdapter, ItemEntity> listener) {
        mContext = context.getApplicationContext();
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        final ItemType itemType = ItemType.valueOf(viewType);
        switch (itemType) {
            case Progress:
                itemView = mLayoutInflater.inflate(R.layout.recyclerview_progress_layout, parent, false);
                return new ProgressViewHolder(itemView);
            case Ad:
                itemView = mLayoutInflater.inflate(R.layout.recyclerview_ad_layout, parent, false);
                AdView adView = (AdView) itemView.findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
                return new AdViewHolder(itemView);
            default:
                itemView = mLayoutInflater.inflate(R.layout.recyclerview_search_code_result_list_item, parent, false);
                final SearchCodeResultListAdapter.ViewHolder viewHolder = new SearchCodeResultListAdapter.ViewHolder(itemView);
                viewHolder.pathTextView.setOnClickListener(view -> {
                    if (mListener != null) {
                        int position = viewHolder.getAdapterPosition();
                        Logger.d("click position:" + position);
                        ItemEntity entity = getEntityAtPosition(position - HEADER_SIZE);
                        mListener.onItemClick(SearchCodeResultListAdapter.this, position, entity, itemType);
                    }
                });
                return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemCount() <= 0) {
            return;
        }
        if(position == HEADER_POSITION){
            return;
        }
        final ItemEntity item = getEntityAtPosition(position - HEADER_SIZE);
        if(item.getItemType() == ItemType.Progress){
            return;
        }

        if(!(holder instanceof ViewHolder)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.nameTextView.setText(item.getName());
        viewHolder.pathTextView.setText(item.getPath());
        viewHolder.mChildAdapter.setSearchResultList(item.getTextMatchEntityList());
        viewHolder.textMatchRecyclerView.setAdapter(viewHolder.mChildAdapter);
    }


    /**
     * TextMatches
     */
    public static class ChildSearchCodeResultListAdapter extends RecyclerView.Adapter<ChildSearchCodeResultListAdapter.ViewHolder> {

        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private OnItemClickListener<ChildSearchCodeResultListAdapter, TextMatchEntity> mListener;

        private List<TextMatchEntity> mSearchResultList = new ArrayList<>();

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textMatchTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                textMatchTextView = (TextView) itemView.findViewById(R.id.text_match);
            }
        }

        public ChildSearchCodeResultListAdapter(Context context, OnItemClickListener<ChildSearchCodeResultListAdapter, TextMatchEntity> listener) {
            mContext = context.getApplicationContext();
            mLayoutInflater = LayoutInflater.from(context);
            mListener = listener;
        }

        @Override
        public ChildSearchCodeResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mLayoutInflater.inflate(R.layout.recyclerview_search_code_child_result_list_item, parent, false);
            ChildSearchCodeResultListAdapter.ViewHolder viewHolder = new ChildSearchCodeResultListAdapter.ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemCount() <= 0) {
                return;
            }
            TextMatchEntity item = mSearchResultList.get(position);

            Spannable spannable = new SpannableString(item.getFragmentText());
            for(MatchEntity entity : item.getMatches()) {
                spannable.setSpan(new RoundedBackgroundSpan(mContext), entity.getIndices()[0], entity.getIndices()[1],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            holder.textMatchTextView.setText(spannable);
        }

        @Override
        public int getItemCount() {
            return mSearchResultList.size();
        }


        public void setSearchResultList(List<TextMatchEntity> searchResultList) {
            mSearchResultList = searchResultList;
        }
    }
}