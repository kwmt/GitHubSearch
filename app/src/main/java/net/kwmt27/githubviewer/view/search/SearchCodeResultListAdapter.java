package net.kwmt27.githubviewer.view.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.ItemEntity;
import net.kwmt27.githubviewer.util.Logger;
import net.kwmt27.githubviewer.view.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class SearchCodeResultListAdapter extends RecyclerView.Adapter<SearchCodeResultListAdapter.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private OnItemClickListener<SearchCodeResultListAdapter, ItemEntity> mListener;

    private List<ItemEntity> mSearchResultList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        TextView favoriteCountTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            favoriteCountTextView = (TextView) itemView.findViewById(R.id.favorite_count);
        }
    }

    public SearchCodeResultListAdapter(Context context, OnItemClickListener<SearchCodeResultListAdapter, ItemEntity> listener) {
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public SearchCodeResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_github_repo_list_item, parent, false);
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
    }

    @Override
    public int getItemCount() {
        return mSearchResultList.size();
    }


    public void setSearchResultList(List<ItemEntity> searchResultList) {
        mSearchResultList = searchResultList;
    }
}