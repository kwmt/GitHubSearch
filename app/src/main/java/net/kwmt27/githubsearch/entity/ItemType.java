package net.kwmt27.githubsearch.entity;

/**
 * RecyclerView Itemのタイプ
 */
public enum ItemType {

    Normal(0),
    Progress(1),
    Ad(2);

    private int typeId;

    ItemType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public static ItemType valueOf(int id) {
        for (ItemType itemType : values()) {
            if (itemType.getTypeId() == id) {
                return itemType;
            }
        }
        throw new IllegalArgumentException("no such enum object for the id: " + id);
    }
}
