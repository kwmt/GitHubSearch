package net.kwmt27.codesearch.view.repolist;

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
import net.kwmt27.codesearch.view.parts.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private OnItemClickListener<RepositoryListAdapter, GithubRepoEntity> mListener;

    private List<GithubRepoEntity> mGithubRepoEntityList = new ArrayList<>();

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

    public RepositoryListAdapter(Context context, OnItemClickListener<RepositoryListAdapter, GithubRepoEntity> listener) {
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        ItemType itemType = mGithubRepoEntityList.get(position).getItemType();
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
    public RepositoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
                view = mLayoutInflater.inflate(R.layout.recyclerview_repo_list_item, parent, false);
                break;
        }


        final RepositoryListAdapter.ViewHolder viewHolder = new RepositoryListAdapter.ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    Logger.d("click position:" + position);
                    GithubRepoEntity entity = mGithubRepoEntityList.get(position);
                    mListener.onItemClick(RepositoryListAdapter.this, position, entity, itemType);
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
        GithubRepoEntity item = mGithubRepoEntityList.get(position);
        if (item.getItemType() == null) {
            holder.nameTextView.setText(item.getFullName());
            holder.descriptionTextView.setText(item.getDescription());
            holder.favoriteCountTextView.setText(item.getStargazersCount());
            holder.languageTextView.setText(item.getLanguage());
            holder.pushedAtTextView.setText(item.getFormattedPushedAt());
        }
    }

    @Override
    public int getItemCount() {
        return mGithubRepoEntityList.size();
    }


    public void setGithubRepoEntityList(List<GithubRepoEntity> githubRepoEntityList) {
        mGithubRepoEntityList = githubRepoEntityList;
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
            mGithubRepoEntityList.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public void addAdItemTypeThenNotify() {
        int pos = addItemTypeAtBeginningPosition(ItemType.Ad);
        if (pos > -1) {
            notifyItemInserted(pos);
        }
    }

//    public void removeAdItemTypeIfNeeded() {
//        int pos = findPositionByItemType(ItemType.Ad);
//        if (pos > -1) {
//            mGithubRepoEntityList.remove(pos);
//            notifyItemRemoved(pos);
//        }
//    }

    private int addItemType(ItemType type) {
        mGithubRepoEntityList.add(new GithubRepoEntity(type));
        return mGithubRepoEntityList.size() - 1;
    }
    private int addItemTypeAtBeginningPosition(ItemType type) {
        mGithubRepoEntityList.add(0, new GithubRepoEntity(type));
        return mGithubRepoEntityList.size() - 1;
    }

    private int findPositionByItemType(ItemType type) {
        for (int i = 0; i < mGithubRepoEntityList.size(); i++) {
            if (mGithubRepoEntityList.get(i).getItemType() == type) {
                return i;
            }
        }
        return -1;
    }

}