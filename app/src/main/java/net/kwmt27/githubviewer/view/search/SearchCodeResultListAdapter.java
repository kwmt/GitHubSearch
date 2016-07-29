package net.kwmt27.githubviewer.view.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.ItemEntity;
import net.kwmt27.githubviewer.entity.TextMatchEntity;
import net.kwmt27.githubviewer.util.Logger;
import net.kwmt27.githubviewer.view.DividerItemDecoration;
import net.kwmt27.githubviewer.view.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class SearchCodeResultListAdapter extends RecyclerView.Adapter<SearchCodeResultListAdapter.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private OnItemClickListener<SearchCodeResultListAdapter, ItemEntity> mListener;

    private List<ItemEntity> mSearchResultList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        TextView favoriteCountTextView;
        RecyclerView textMatchRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.text_match);
            favoriteCountTextView = (TextView) itemView.findViewById(R.id.favorite_count);
            textMatchRecyclerView = (RecyclerView) itemView.findViewById(R.id.text_match_list);
            textMatchRecyclerView.addItemDecoration(new DividerItemDecoration(itemView.getContext(), R.drawable.divider));
            RecyclerView.LayoutManager layout = new LinearLayoutManager(itemView.getContext());
            layout.setAutoMeasureEnabled(true);
            textMatchRecyclerView.setLayoutManager(layout);
            textMatchRecyclerView.setHasFixedSize(false);

        }
    }

    public SearchCodeResultListAdapter(Context context, OnItemClickListener<SearchCodeResultListAdapter, ItemEntity> listener) {
        mContext = context.getApplicationContext();
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public SearchCodeResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_github_code_list_item, parent, false);
        final SearchCodeResultListAdapter.ViewHolder viewHolder = new SearchCodeResultListAdapter.ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    Logger.d("click position:" + position);
                    ItemEntity entity = mSearchResultList.get(position);
                    mListener.onItemClick(SearchCodeResultListAdapter.this, position, entity);
                }

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemCount() <= 0) {
            return;
        }
        ItemEntity item = mSearchResultList.get(position);

        holder.nameTextView.setText(item.getName());
        holder.descriptionTextView.setText(item.getTextMatchEntityList().get(0).getFragmentText());
        holder.favoriteCountTextView.setText(item.getRepository().getStargazersCount());

        ChildSearchCodeResultListAdapter childAdapter = new ChildSearchCodeResultListAdapter(mContext, new OnItemClickListener<ChildSearchCodeResultListAdapter, TextMatchEntity>() {
            @Override
            public void onItemClick(ChildSearchCodeResultListAdapter adapter, int position, TextMatchEntity entity) {
                Logger.d("onItemClick is called. position:" + position + " :"  + entity.getFragmentText());
            }
        });
        childAdapter.setSearchResultList(item.getTextMatchEntityList());
        holder.textMatchRecyclerView.setAdapter(childAdapter);
    }

    @Override
    public int getItemCount() {
        return mSearchResultList.size();
    }


    public void setSearchResultList(List<ItemEntity> searchResultList) {
        mSearchResultList = searchResultList;
    }

    /**
     * TextMatches
     */
    public static class ChildSearchCodeResultListAdapter extends RecyclerView.Adapter<ChildSearchCodeResultListAdapter.ViewHolder> {

        private final LayoutInflater mLayoutInflater;
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
            mLayoutInflater = LayoutInflater.from(context);
            mListener = listener;
        }

        @Override
        public ChildSearchCodeResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mLayoutInflater.inflate(R.layout.recyclerview_github_code_child_list_item, parent, false);
            final ChildSearchCodeResultListAdapter.ViewHolder viewHolder = new ChildSearchCodeResultListAdapter.ViewHolder(view);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = viewHolder.getAdapterPosition();
                        Logger.d("click position:" + position);
                        TextMatchEntity entity = mSearchResultList.get(position);
                        mListener.onItemClick(ChildSearchCodeResultListAdapter.this, position, entity);
                    }

                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemCount() <= 0) {
                return;
            }
            TextMatchEntity item = mSearchResultList.get(position);

            holder.textMatchTextView.setText(item.getFragmentText());
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