package com.thbs.skycons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

public class SunView extends WeatherTemplateView {

    private Path sunPath;
    private int count = 0;

    public SunView(Context context) {
        super(context);
    }

    public SunView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        sunPath = new Path();

        mPaint.setColor(strokeColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(0, 0, 0, Color.BLACK);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (count > 360) {
            count = count % 360;
        } else {
            count += 3;
        }

        // set canvas background color
        canvas.drawColor(bgColor);
        sunPath.reset();
        mPath.reset();


        // drawing arms of sun
        for (int i = 0; i < 360; i += 45) {

            double fromCosValue = (0.1458 * width) * Math.cos(Math.toRadians(i + count));
            double fromSinValue = (0.1458 * width) * Math.sin(Math.toRadians(i + count));
            double toSinValue = (0.1875 * width) * Math.sin(Math.toRadians(i + count));
            double toCosValue = (0.1875 * width) * Math.cos(Math.toRadians(i + count));

            float x1 = (float) (fromCosValue + centerX);
            float y1 = (float) (fromSinValue + centerY);
            float X2 = (float) (toCosValue + centerX);
            float Y2 = (float) (toSinValue + centerY);
            sunPath.moveTo(x1, y1);
            sunPath.lineTo(X2, Y2);
        }

        mPath.moveTo(centerX, centerY);
        mPath.addCircle(centerX, centerY, (int) (0.1042 * width), Path.Direction.CW);

        canvas.drawPath(mPath, mPaint);
        canvas.drawPath(sunPath, mPaint);

        if (!isStatic) {
            postInvalidateDelayed(33);
        }
    }

}
