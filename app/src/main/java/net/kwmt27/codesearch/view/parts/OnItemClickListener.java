package net.kwmt27.codesearch.view.parts;

import net.kwmt27.codesearch.entity.ItemType;

/**
 * AdapterViewにあるitemがクリックされたときに呼ばれるコールバックを定義
 */
public interface OnItemClickListener<T,U> {
    /**
     * AdapterViewにあるitemがクリックされたときに呼ばれる
     * @param adapter Adapter自身
     * @param position item位置
     * @param entity クリックされたitemのデータ(entity)
     */
    void onItemClick(T adapter, int position, U entity, ItemType type);
}
