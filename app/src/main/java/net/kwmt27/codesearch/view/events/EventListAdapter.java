package net.kwmt27.codesearch.view.events;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.ItemType;
import net.kwmt27.codesearch.view.BaseRecyclerAdapter;


public class EventListAdapter extends BaseRecyclerAdapter<EventListAdapter.ViewHolder, EventEntity> {

    private final Context mContext;
    private EventListFragment.OnLinkClickListener mListener;

    private LayoutInflater mLayoutInflater;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView displayLoginTextView;
        TextView dateTextView;
        FrameLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.avatar);

            displayLoginTextView = (TextView) itemView.findViewById(R.id.display_login);
            dateTextView = (TextView) itemView.findViewById(R.id.date);
            container = (FrameLayout) itemView.findViewById(R.id.container);
        }
    }

    public EventListAdapter(Context context, EventListFragment.OnLinkClickListener listener) {
        mContext = context.getApplicationContext();
        mListener = listener;
    }

    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(parent.getContext());

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
                view = mLayoutInflater.inflate(R.layout.recyclerview_event_list_item, parent, false);
                break;
        }


        final EventListAdapter.ViewHolder viewHolder = new EventListAdapter.ViewHolder(view);
//        viewHolder.itemView.setOnClickListener(v -> {
//            if (mListener != null) {
//                int position = viewHolder.getAdapterPosition();
//                Logger.d("click position:" + position);
//                EventEntity entity = mEventEntityList.get(position);
//                mListener.onItemClick(EventListAdapter.this, position, entity, itemType);
//            }
//
//        });
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

        EventEntity item = getEntityAtPosition(position - HEADER_SIZE);
        if(item.getItemType() == ItemType.Progress){
            return;
        }

        Glide.with(mContext).load(item.getActor().getAvatarUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.avatarImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.avatarImageView.setImageDrawable(circularBitmapDrawable);
            }
        });

        holder.dateTextView.setText(item.getFormattedCreatedAt());
        holder.displayLoginTextView.setText(item.getActor().getDisplayLogin());
        holder.container.removeAllViews();
        holder.container.addView(item.createView(mLayoutInflater.getContext(), mListener));
    }


}