package net.kwmt27.codesearch.entity;

public abstract class BaseEntity {
    private ItemType mItemType;

    public BaseEntity() {
    }

    public ItemType getItemType() {
        return mItemType;
    }

    public BaseEntity(ItemType itemType) {
        mItemType = itemType;
    }

    public void setItemType(ItemType itemType) {
        mItemType = itemType;
    }
}
