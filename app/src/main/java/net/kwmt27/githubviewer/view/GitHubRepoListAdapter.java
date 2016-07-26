package net.kwmt27.githubviewer.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.util.Logger;

import java.util.ArrayList;
import java.util.List;


public class GitHubRepoListAdapter extends RecyclerView.Adapter<GitHubRepoListAdapter.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private OnItemClickListener<GitHubRepoListAdapter, GithubRepoEntity> mListener;

    private List<GithubRepoEntity> mGithubRepoEntityList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public GitHubRepoListAdapter(Context context, OnItemClickListener<GitHubRepoListAdapter, GithubRepoEntity> listener) {
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public GitHubRepoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_github_repo_list_item, parent, false);
        final GitHubRepoListAdapter.ViewHolder viewHolder = new GitHubRepoListAdapter.ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    Logger.d("click position:" + position);
                    GithubRepoEntity parkingEntity = mGithubRepoEntityList.get(position);
                    mListener.onItemClick(GitHubRepoListAdapter.this, position, parkingEntity);
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

        holder.nameTextView.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return mGithubRepoEntityList.size();
    }


    public void setGithubRepoEntityList(List<GithubRepoEntity> githubRepoEntityList) {
        mGithubRepoEntityList = githubRepoEntityList;
    }
}