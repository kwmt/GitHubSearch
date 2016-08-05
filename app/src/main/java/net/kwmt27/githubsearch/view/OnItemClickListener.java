package net.kwmt27.githubsearch.view;

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
    void onItemClick(T adapter, int position, U entity);
}
