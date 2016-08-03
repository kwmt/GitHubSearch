package net.kwmt27.githubviewer.view.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.util.Logger;
import net.kwmt27.githubviewer.view.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class SearchRepositoryResultListAdapter extends RecyclerView.Adapter<SearchRepositoryResultListAdapter.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private OnItemClickListener<SearchRepositoryResultListAdapter, GithubRepoEntity> mListener;

    private List<GithubRepoEntity> mSearchResultList = new ArrayList<>();

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
        View view = mLayoutInflater.inflate(R.layout.recyclerview_search_repo_result_list_item, parent, false);
        final SearchRepositoryResultListAdapter.ViewHolder viewHolder = new SearchRepositoryResultListAdapter.ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    Logger.d("click position:" + position);
                    GithubRepoEntity entity = mSearchResultList.get(position);
                    mListener.onItemClick(SearchRepositoryResultListAdapter.this, position, entity);
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
        GithubRepoEntity item = mSearchResultList.get(position);

        holder.nameTextView.setText(item.getFullName());
        holder.descriptionTextView.setText(item.getDescription());
        holder.favoriteCountTextView.setText(item.getStargazersCount());
        holder.languageTextView.setText(item.getLanguage());
        holder.pushedAtTextView.setText(item.getFormattedPushedAt());
    }

    @Override
    public int getItemCount() {
        return mSearchResultList.size();
    }


    public void setSearchResultList(List<GithubRepoEntity> searchResultList) {
        mSearchResultList = searchResultList;
    }
}