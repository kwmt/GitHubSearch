package net.kwmt27.codesearch.view.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.entity.ItemType;
import net.kwmt27.codesearch.util.Logger;
import net.kwmt27.codesearch.view.BaseRecyclerAdapter;
import net.kwmt27.codesearch.view.parts.OnItemClickListener;


public class SearchRepositoryResultListAdapter extends BaseRecyclerAdapter<SearchRepositoryResultListAdapter.ViewHolder, GithubRepoEntity> {

    private final LayoutInflater mLayoutInflater;
    private OnItemClickListener<SearchRepositoryResultListAdapter, GithubRepoEntity> mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        TextView favoriteCountTextView;
        TextView languageTextView;
        TextView pushedAtTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            favoriteCountTextView = (TextView) itemView.findViewById(R.id.favorite_count);
            languageTextView = (TextView) itemView.findViewById(R.id.language_text);
            pushedAtTextView = (TextView) itemView.findViewById(R.id.pushed_at);
        }
    }

    public SearchRepositoryResultListAdapter(Context context, OnItemClickListener<SearchRepositoryResultListAdapter, GithubRepoEntity> listener) {
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }



    @Override
    public SearchRepositoryResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        final ItemType itemType = ItemType.valueOf(viewType);
        switch (itemType) {
            case Progress:
                view = mLayoutInflater.inflate(R.layout.recyclerview_progress_layout, parent, false);
                break;
            case Ad:
                view = mLayoutInflater.inflate(R.layout.recyclerview_ad_layout, parent, false);
                AdView adView = (AdView) view.findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
                break;
            default:
                view = mLayoutInflater.inflate(R.layout.recyclerview_search_repo_result_list_item, parent, false);
                break;
        }

        final SearchRepositoryResultListAdapter.ViewHolder viewHolder = new SearchRepositoryResultListAdapter.ViewHolder(view);
        viewHolder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                int position = viewHolder.getAdapterPosition();
                Logger.d("click position:" + position);
                GithubRepoEntity entity = getEntityAtPosition(position - HEADER_SIZE);
                mListener.onItemClick(SearchRepositoryResultListAdapter.this, position, entity, itemType);
            }

        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemCount() <= 0) {
            return;
        }
        if(position == HEADER_POSITION){
            return;
        }
        final GithubRepoEntity item = getEntityAtPosition(position - HEADER_SIZE);
        if(item.getItemType() == ItemType.Progress){
            return;
        }

        holder.nameTextView.setText(item.getFullName());
        holder.descriptionTextView.setText(item.getDescription());
        holder.favoriteCountTextView.setText(item.getStargazersCount());
        holder.languageTextView.setText(item.getLanguage());
        holder.pushedAtTextView.setText(item.getFormattedPushedAt());
    }


}