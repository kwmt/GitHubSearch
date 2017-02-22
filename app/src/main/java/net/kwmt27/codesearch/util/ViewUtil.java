package net.kwmt27.codesearch.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import net.kwmt27.codesearch.R;

public class ViewUtil {

    public static void setRippleDrawable(Context context, View view) {
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        view.setBackgroundResource(backgroundResource);
    }

}
