package net.kwmt27.githubsearch.view.search;

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

import net.kwmt27.githubsearch.R;
import net.kwmt27.githubsearch.entity.ItemEntity;
import net.kwmt27.githubsearch.entity.ItemType;
import net.kwmt27.githubsearch.entity.MatchEntity;
import net.kwmt27.githubsearch.entity.TextMatchEntity;
import net.kwmt27.githubsearch.util.RoundedBackgroundSpan;
import net.kwmt27.githubsearch.view.DividerItemDecoration;
import net.kwmt27.githubsearch.view.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class SearchCodeResultListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private OnItemClickListener<SearchCodeResultListAdapter, ItemEntity> mListener;

    private List<ItemEntity> mSearchResultList = new ArrayList<>();

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
    public int getItemViewType(int position) {
        ItemType itemType = mSearchResultList.get(position).getItemType();
        if (itemType == null) {
            return ItemType.Normal.getTypeId();
        }
        switch (itemType) {
            case Progress:
                return ItemType.Progress.getTypeId();
            case Ad:
                return ItemType.Ad.getTypeId();
            default:
                return ItemType.Normal.getTypeId();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ItemType itemType = ItemType.valueOf(viewType);
        switch (itemType) {
            case Progress:
                view = mLayoutInflater.inflate(R.layout.recyclerview_progress_layout, parent, false);
                return new ProgressViewHolder(view);
            case Ad:
                view = mLayoutInflater.inflate(R.layout.recyclerview_ad_layout, parent, false);
                AdView adView = (AdView) view.findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
                return new AdViewHolder(view);
            default:
                view = mLayoutInflater.inflate(R.layout.recyclerview_search_code_result_list_item, parent, false);
                return new SearchCodeResultListAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemCount() <= 0) {
            return;
        }
        final ItemEntity item = mSearchResultList.get(position);

        if(!(holder instanceof ViewHolder)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder)holder;

        if (item.getItemType() == null) {
            viewHolder.nameTextView.setText(item.getName());
            viewHolder.pathTextView.setText(item.getPath());
            viewHolder.pathTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(SearchCodeResultListAdapter.this, position, item);
                    }
                }
            });
            viewHolder.mChildAdapter.setSearchResultList(item.getTextMatchEntityList());
            viewHolder.textMatchRecyclerView.setAdapter(viewHolder.mChildAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return mSearchResultList.size();
    }


    public void setSearchResultList(List<ItemEntity> searchResultList) {
        mSearchResultList = searchResultList;
    }

    public void addProgressItemTypeThenNotify() {
        int pos = addItemType(ItemType.Progress);
        if (pos > -1) {
            notifyItemInserted(pos);
        }
    }

    public void removeProgressItemTypeThenNotify() {
        int pos = findPositionByItemType(ItemType.Progress);
        if (pos > -1) {
            mSearchResultList.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public void addAdItemTypeThenNotify() {
        int pos = addItemTypeAtBeginningPosition(ItemType.Ad);
        if (pos > -1) {
            notifyItemInserted(pos);
        }
    }
    private int addItemTypeAtBeginningPosition(ItemType type) {
        mSearchResultList.add(0, new ItemEntity(type));
        return mSearchResultList.size() - 1;
    }


    private int addItemType(ItemType type) {
        mSearchResultList.add(new ItemEntity(type));
        return mSearchResultList.size() - 1;
    }

    private int findPositionByItemType(ItemType type) {
        for (int i = 0; i < mSearchResultList.size(); i++) {
            if (mSearchResultList.get(i).getItemType() == type) {
                return i;
            }
        }
        return -1;
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