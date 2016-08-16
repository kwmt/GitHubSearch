package net.kwmt27.codesearch.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.text.style.ReplacementSpan;

import net.kwmt27.codesearch.R;

/**
 * テキストの背景に角丸の背景色を付ける
 */
public class RoundedBackgroundSpan extends ReplacementSpan{

    private static final int CORNER_RADIUS = 8;
    private int backgroundColor = 0;
    private int textColor = 0;

    public RoundedBackgroundSpan(Context context) {
        super();
        backgroundColor = ContextCompat.getColor(context, R.color.match_background_color);
        textColor = ContextCompat.getColor(context, R.color.text_color);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
        paint.setColor(backgroundColor);
        canvas.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, paint);
        paint.setColor(textColor);
        canvas.drawText(text, start, end, x, y, paint);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(paint.measureText(text, start, end));
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }

}
