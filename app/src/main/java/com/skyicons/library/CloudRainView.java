package com.skyicons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * This view draws cloud with rain.
 */
public class CloudRainView extends WeatherTemplateView {

    boolean drop1 = true, drop2 = false, drop3 = false;
    float dx, dy = 0.0f;
    int dRain;
    Cloud cloud;
    private double count = 0;
    private Path mRainPath;

    public CloudRainView(Context context) {
        super(context);
    }

    public CloudRainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {

        int strokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        mPaint.setColor(strokeColor);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(0, 0, 0, strokeColor);

        mRainPath = new Path();
        cloud = new Cloud();
        dRain = 0;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // set canvas background color
        canvas.drawColor(bgColor);
        if (count > 360) {
            count = count % 360;
        } else {
            count += 15;
        }


        dRain += 5;
        dRain %= 100;

        dy = centerY+20;

        if (drop1) {
            dx = centerX - 20;
            if (dRain == 95) {
                drop2 = true;
                drop1 = false;
            }
        } else if (drop2) {
            dx = centerX;
            if (dRain == 95) {
                drop3 = true;
                drop2 = false;
            }
        } else if (drop3) {
            dx = centerX + 20;
            if (dRain == 95) {
                drop1 = true;
                drop3 = false;
            }
        }

        mRainPath.reset();
        mRainPath.moveTo(dx, dy);
        mRainPath.addArc(new RectF(dx - 5, dy - 5 + dRain, dx + 5, dy + 5 + dRain), 180, -180);
        mRainPath.lineTo(dx, dy - 10 + dRain);
        mRainPath.close();

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(mRainPath, mPaint);


        // drawing cloud with fill
        mPaint.setColor(bgColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), mPaint);

        // mPaint cloud with stroke
        mPaint.setColor(strokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), mPaint);

        if (!isStatic) {
            postInvalidateDelayed(33);
        }

    }

}

