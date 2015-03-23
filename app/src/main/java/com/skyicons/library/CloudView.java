package com.skyicons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class CloudView extends WeatherTemplateView {

    private Cloud mCloud;
    private double count = 0;

    @Override
    protected void init() {
        mCloud = new Cloud();
        mPaint.setColor(strokeColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(0, 0, 0, Color.BLACK);
    }

    public CloudView(Context context) {
        super(context);
    }

    public CloudView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(bgColor);

        mPaint.setStrokeWidth((float) (0.02083 * width));

        if (count > 360) {
            count = count % 360;
        } else {
            count += 3;
        }
        // draw cloud
        canvas.drawPath(mCloud.getCloud(centerX, centerY, (int) width, count), mPaint);

        if (!isStatic) {
            postInvalidateDelayed(50);
        }
    }

}
