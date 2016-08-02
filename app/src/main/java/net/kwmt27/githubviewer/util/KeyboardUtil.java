package net.kwmt27.githubviewer.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * キーボード関連ユーティリティ
 */
public class KeyboardUtil {

    /**
     * キーボードを閉じる
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {

        // Close/hide the Android Soft Keyboard - Stack Overflow
        // http://stackoverflow.com/a/1109108/2520998
        View view = activity.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
