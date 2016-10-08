package net.kwmt27.codesearch.util;

import android.util.Log;

import net.kwmt27.codesearch.BuildConfig;


/**
 * ロガークラス
 * <p/>
 * <p>デバッグビルドとリリースビルドでログ出力有無を切り替える。<br/>
 * デバッグビルドではログ出力し、リリースビルドではログ出力しない</p>
 */
public class Logger {

    private static final Boolean DEBUG = BuildConfig.DEBUG;

    /**
     * DEBUGログを出力する。
     *
     * @param msg
     */
    public static void d(String msg) {
        if (DEBUG) {
            Log.d(Logger.getLogTagWithMethod(), msg);
        }
    }

    /**
     * ERRORログを出力する。
     *
     * @param msg
     */
    public static void e(String msg) {
        if (DEBUG) {
            Log.e(Logger.getLogTagWithMethod(), msg);
        }
    }

    /**
     * ERRORログとExceptionを出力する。
     *
     * @param msg
     * @param tr
     */
    public static void e(String msg, Throwable tr) {
        if (DEBUG) {
            Log.e(Logger.getLogTagWithMethod(), msg, tr);
        }
    }

    /**
     * ERROR Exceptionを出力する。
     *
     * @param tr
     */
    public static void e(Throwable tr) {
        if (DEBUG) {
            Log.e(Logger.getLogTagWithMethod(), tr.getMessage(), tr);
        }
    }

    /**
     * INFOログを出力する。
     *
     * @param msg
     */
    public static void i(String msg) {
        if (DEBUG) {
            Log.i(Logger.getLogTagWithMethod(), msg);
        }
    }

    /**
     * VERBOSEログを出力する。
     *
     * @param msg
     */
    public static void v(String msg) {
        if (DEBUG) {
            Log.v(Logger.getLogTagWithMethod(), msg);
        }
    }

    /**
     * WARNログを出力する。
     *
     * @param msg
     */
    public static void w(String msg) {
        if (DEBUG) {
            Log.w(Logger.getLogTagWithMethod(), msg);
        }
    }

    /**
     * WARNログとExceptionを出力する。
     *
     * @param msg
     * @param tr
     */
    public static void w(String msg, Throwable tr) {
        if (DEBUG) {
            Log.w(Logger.getLogTagWithMethod(), msg, tr);
        }
    }

    /**
     * @return 実行されたクラス名・メソッド・行番号を取得する
     */
    private static String getLogTagWithMethod() {
        Throwable stack = new Throwable().fillInStackTrace();
        StackTraceElement[] trace = stack.getStackTrace();
        return trace[2].getClassName() + "." + trace[2].getMethodName() + ":" + trace[2].getLineNumber();
    }

    public static void methodOnly() {
        if(DEBUG) {
            d(getMethodName());
        }
    }
    private static String getMethodName() {
        Throwable stack = new Throwable().fillInStackTrace();
        StackTraceElement[] trace = stack.getStackTrace();
        return trace[2].getClassName() + "." + trace[2].getMethodName() + " is called.";
    }
}
