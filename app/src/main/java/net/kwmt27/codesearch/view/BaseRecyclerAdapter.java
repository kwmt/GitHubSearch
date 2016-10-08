package net.kwmt27.codesearch.view;

import android.support.v7.widget.RecyclerView;

import net.kwmt27.codesearch.entity.BaseEntity;
import net.kwmt27.codesearch.entity.ItemType;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder, U extends BaseEntity> extends RecyclerView.Adapter<T> {

    public final static int HEADER_POSITION = 0;
    public final static int HEADER_SIZE = 1;

    private List<U> mEntityList = new ArrayList<>();


    @Override
    public int getItemViewType(int position) {
        if (position == HEADER_POSITION) {
            return ItemType.Ad.getTypeId();
        }
        if(mEntityList.get(position - HEADER_SIZE).getItemType() == ItemType.Progress){
            return ItemType.Progress.getTypeId();
        }
        return ItemType.Normal.getTypeId();
    }


    @Override
    public int getItemCount() {
        return mEntityList.size() > 0 ? mEntityList.size() + HEADER_SIZE : 0;
    }



    public U getEntityAtPosition(int position){
        return mEntityList.get(position);
    }


    public void setEntityList(List<U> entityList) {
        mEntityList = new ArrayList<>(entityList);
    }

    public void addProgressItemTypeThenNotify(U entity) {
        entity.setItemType(ItemType.Progress);
        mEntityList.add(entity);
        int pos = mEntityList.size() - 1;
        if (pos > -1) {
            notifyItemInserted(pos);
        }
    }

    public void removeProgressItemTypeThenNotify() {
        int pos = findPositionByItemType(ItemType.Progress);
        if (pos > -1) {
            mEntityList.remove(pos);
            notifyItemRemoved(pos);
        }
    }


    private int findPositionByItemType(ItemType type) {
        for (int i = 0; i < mEntityList.size(); i++) {
            if (mEntityList.get(i).getItemType() == type) {
                return i;
            }
        }
        return -1;
    }

}