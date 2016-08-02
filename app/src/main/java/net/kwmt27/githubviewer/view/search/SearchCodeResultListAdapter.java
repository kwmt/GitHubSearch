package net.kwmt27.githubviewer.view.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.kwmt27.githubviewer.R;
import net.kwmt27.githubviewer.entity.ItemEntity;
import net.kwmt27.githubviewer.entity.MatchEntity;
import net.kwmt27.githubviewer.entity.TextMatchEntity;
import net.kwmt27.githubviewer.util.RoundedBackgroundSpan;
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

    public SearchCodeResultListAdapter(Context context, OnItemClickListener<SearchCodeResultListAdapter, ItemEntity> listener) {
        mContext = context.getApplicationContext();
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public SearchCodeResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_github_code_list_item, parent, false);
        final SearchCodeResultListAdapter.ViewHolder viewHolder = new SearchCodeResultListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (getItemCount() <= 0) {
            return;
        }
        final ItemEntity item = mSearchResultList.get(position);

        holder.nameTextView.setText(item.getName());
        holder.pathTextView.setText(item.getPath());
        holder.pathTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onItemClick(SearchCodeResultListAdapter.this, position, item);
                }
            }
        });


        holder.mChildAdapter.setSearchResultList(item.getTextMatchEntityList());
        holder.textMatchRecyclerView.setAdapter(holder.mChildAdapter);
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
            View view = mLayoutInflater.inflate(R.layout.recyclerview_github_code_child_list_item, parent, false);
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