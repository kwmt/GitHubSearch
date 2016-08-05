package net.kwmt27.githubsearch.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerViewのdivider用
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;
    private Context mContext;
    /** 端から端まで線をひくかどうか。 */
    private boolean mEndToEndLine = true;

    /**
     * デフォルトdividerを使用する
     */
    public DividerItemDecoration(Context context) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        mDivider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
    }

    /**
     * カスタムdividerを使用する
     */
    public DividerItemDecoration(Context context, int resId) {
        mContext = context.getApplicationContext();
        mDivider = ContextCompat.getDrawable(context, resId);
    }

    public DividerItemDecoration(Context context, int resId, boolean endToEndLine) {
        this(context, resId);
        mEndToEndLine = endToEndLine;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // アイテムのビューより下に描画される
        // 上に描画するのはonDrawOver()

        int margin = 0;
        if(!mEndToEndLine) {
            float scaleDensity = mContext.getResources().getDisplayMetrics().density;
            margin = (int)scaleDensity * 16; // 16dp
        }


        int left = parent.getPaddingLeft() + margin;
        int right = parent.getWidth() - parent.getPaddingRight() - margin;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
