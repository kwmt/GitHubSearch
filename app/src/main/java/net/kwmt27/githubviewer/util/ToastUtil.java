package net.kwmt27.githubviewer.util;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {


    public static void show(final Context context, final String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
