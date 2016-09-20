package net.kwmt27.codesearch.view.events;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.kwmt27.codesearch.R;
import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.ItemType;
import net.kwmt27.codesearch.view.parts.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private final Context mContext;
    private OnItemClickListener<EventListAdapter, EventEntity> mListener;

    private List<EventEntity> mEventEntityList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView displayLoginTextView;
        TextView dateTextView;
        TextView eventTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.avatar);

            displayLoginTextView = (TextView) itemView.findViewById(R.id.display_login);
            dateTextView = (TextView) itemView.findViewById(R.id.date);
            eventTextView = (TextView) itemView.findViewById(R.id.event);
        }
    }

    public EventListAdapter(Context context, OnItemClickListener<EventListAdapter, EventEntity> listener) {
        mContext = context.getApplicationContext();
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        ItemType itemType = mEventEntityList.get(position).getItemType();
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
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        final ItemType itemType = ItemType.valueOf(viewType);
        switch (itemType) {
            case Progress:
                view = inflater.inflate(R.layout.recyclerview_progress_layout, parent, false);
                break;
            case Ad:
                view = inflater.inflate(R.layout.recyclerview_ad_layout, parent, false);
                AdView adView = (AdView) view.findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
                break;
            default:
                view = inflater.inflate(R.layout.recyclerview_event_list_item, parent, false);
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
        EventEntity item = mEventEntityList.get(position);
        if (item.getItemType() == null) {
            Glide.with(mContext).load(item.getActor().getAvatarUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.avatarImageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.avatarImageView.setImageDrawable(circularBitmapDrawable);
                }
            });
            holder.displayLoginTextView.setText(item.getActor().getDisplayLogin());
            holder.dateTextView.setText(item.getFormattedCreatedAt());

            item.action(holder.eventTextView, new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(EventListAdapter.this, position, item, ItemType.Normal);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mEventEntityList.size();
    }


    public void setEventEntityList(List<EventEntity> eventEntityList) {
        mEventEntityList = eventEntityList;
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
            mEventEntityList.remove(pos);
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
//            mEventEntityList.remove(pos);
//            notifyItemRemoved(pos);
//        }
//    }

    private int addItemType(ItemType type) {
        mEventEntityList.add(new EventEntity(type));
        return mEventEntityList.size() - 1;
    }
    private int addItemTypeAtBeginningPosition(ItemType type) {
        mEventEntityList.add(0, new EventEntity(type));
        return mEventEntityList.size() - 1;
    }

    private int findPositionByItemType(ItemType type) {
        for (int i = 0; i < mEventEntityList.size(); i++) {
            if (mEventEntityList.get(i).getItemType() == type) {
                return i;
            }
        }
        return -1;
    }

}