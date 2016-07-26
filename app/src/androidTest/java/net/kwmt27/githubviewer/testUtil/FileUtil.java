package net.kwmt27.githubviewer.testUtil;

import net.kwmt27.githubviewer.App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * File関連のユーティリティ
 */
public class FileUtil {

    /**
     * jsonファイルからStringを取得する
     * @param fileName
     * @return
     */
    public static String getJsonStringFromFile(String fileName) {
        // http://stackoverflow.com/a/21909245/2520998
        InputStream raw = null;
        try {
            raw = App.getInstance().getApplicationContext().getAssets().open(fileName);
            return  new Scanner(raw).useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
