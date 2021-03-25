package net.kwmt27.codesearch.util;

import androidx.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextViewUtil {

    /**
     * {@code textView} のリンクを付けたい{@code targetString}にリンクを付ける。
     * @param textView 対象のTextView
     * @param targetString リンクを付けたい文字列
     * @param clickableSpan リンクをクリックされた時のコールバック
     */
    public static void addLink(TextView textView, String targetString, ClickableSpan clickableSpan) {
        addLink(textView, targetString, clickableSpan, null, null);
    }

    public static void addLink(TextView textView, String targetString1, ClickableSpan clickableSpan1, String targetString2, ClickableSpan clickableSpan2) {

        String text = textView.getText().toString();
        SpannableString ss = new SpannableString(text);

        Matcher matcher1 = getMatcher(targetString1, text);
        if (matcher1 == null) return;
        int start1 = matcher1.start();
        int end1 = matcher1.end();
        ss.setSpan(clickableSpan1, start1, end1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        if(targetString2 != null) {
            Matcher matcher2 = getMatcher(targetString2, text);
            if (matcher2 == null) return;
            int start2 = matcher2.start();
            int end2 = matcher2.end();
            ss.setSpan(clickableSpan2, start2, end2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Nullable
    private static Matcher getMatcher(String targetString, String text) {
        Pattern pattern = Pattern.compile(targetString);
        Matcher matcher = pattern.matcher(text);

        if (!matcher.find()) {
            Logger.w(pattern.toString() + " couldn't find.");
            return null;
        }
        return matcher;
    }

    /**
     * {@code textView} の {@code targetString} のカラーを {@code color} に変更する。
     * @param textView
     * @param targetString
     * @param color
     */
    public static void changeColor(TextView textView, String targetString, int color) {

        String text = textView.getText().toString();

        Pattern pattern = Pattern.compile(targetString);
        Matcher matcher = pattern.matcher(text);

        if (!matcher.find()) {
            Logger.w(pattern.toString() + " couldn't find.");
            return;
        }

        int start = matcher.start();
        int end = matcher.end();

        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
    }

    /** すべての改行を取り除いたStringを返す */
    public static String removeNewLines(String text) {
        if(text == null) { return ""; }
        return text.replaceAll("\\r|\\n", "");
    }
}
