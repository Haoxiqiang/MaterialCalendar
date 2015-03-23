package com.skyicons.library;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * This view draws cloud with heavy rain.
 */
public class CloudHvRainView extends WeatherTemplateView {

    private static Paint paintRain;
    int m1 = 0, m2 = 0, m3 = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0, x3 = 0, y3 = 0;
    int count1 = 0, count2 = 0, count3 = 0, i = 0;
    boolean pointsStored = false;
    double radius1, radius2;
    Cloud cloud;
    private Path path1, path2, path3;
    private double count=0;

    public CloudHvRainView(Context context) {
        super(context);
    }

    public CloudHvRainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        float strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        paintRain = new Paint();

        //Paint for drawing cloud
        mPaint.setColor(strokeColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(0, 0, 0, strokeColor);
        mPaint.setStrokeWidth(strokeWidth);

        //Paint for drawing rain drops
        paintRain.setColor(strokeColor);
        paintRain.setAntiAlias(true);
        paintRain.setStrokeCap(Paint.Cap.ROUND);
        paintRain.setStyle(Paint.Style.FILL_AND_STROKE);
        paintRain.setStrokeWidth(strokeWidth/2);

        cloud = new Cloud();

        radius1 = 90;
        radius2 = 50;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // set canvas background color
        canvas.drawColor(bgColor);

        // set canvas background color
        canvas.drawColor(bgColor);
        if (count > 360) {
            count = count % 360;
        } else {
            count += 15;
        }

        path1 = new Path();// pathCloud for drop 1
        path2 = new Path();// pathCloud for drop 2
        path3 = new Path();// pathCloud for drop 3

        PointF P1c2 = cloud.getP1c2(centerX, centerY, (int) width, count);
        PointF P2c2 = cloud.getP2c2(centerX, centerY, (int) width, count);
        PointF P1c1 = cloud.getP1c1(centerX, centerY, (int) width, count);

        float P1Y = ((float) ((int) (0.1041667 * width) * Math.sin(Math.toRadians(80 + (0.111 * count))) + centerY));

        // Store starting x, y coordinates of rain drops
        if (!pointsStored) {
            x1 = (int) P1c2.x;
            x2 = (int) P2c2.x;
            x3 = (x1 + x2) / 2;

            float value = (int) P1c2.y - ((P1c1.y + P1Y) / 2);
            y1 = y2 = y3 = (int) (P1c2.y - value / 2) - 20;

            pointsStored = true;

        }

        if (isStatic) { //Initial static view
            int m = 95;

            path1.moveTo(x1, y1 + (m - 24));
            path1.lineTo(x1, y1 + m + (float) (centerY * 0.1));
            canvas.drawPath(path1, paintRain);

            path2.moveTo(x2, y2 + (m - 24));
            path2.lineTo(x2, y2 + m + (float) (centerY * 0.1));
            canvas.drawPath(path2, paintRain);

            path3.moveTo(x3, y3 + ((m - 50) - 24));
            path3.lineTo(x3, y3 + (m - 50) + (float) (centerY * 0.1));
            canvas.drawPath(path3, paintRain);

        } else { // Animating view

            if (i <= 2 * 49) {

                if (i < 2 * 25) {

                    //drop11 logic
                    if (m1 < 24) {
                        path1.moveTo(x1, y1);

                    } else {
                        count1 = count1 + 4;
                        path1.moveTo(x1, y1 + count1);
                    }

                    path1.lineTo(x1, y1 + m1 + (float) (centerY * 0.1));
                    canvas.drawPath(path1, paintRain);

                    m1 = m1 + 4;

                    if (m1 == 100) {
                        m1 = 0;
                        count1 = 0;
                    }

                    //drop21 logic
                    if (i > 2 * 10) {

                        if (m2 < 24) {
                            path2.moveTo(x2, y2);

                        } else {
                            count2 = count2 + 4;
                            path2.moveTo(x2, y2 + count2);
                        }

                        path2.lineTo(x2, y2 + m2 + (float) (centerY * 0.1));
                        canvas.drawPath(path2, paintRain);

                        m2 = m2 + 4;

                        if (m2 == 100) {
                            m2 = 0;
                            count2 = 0;
                        }

                    }

                }

                if (i >= 2 * 25 && i <= 2 * 49) {

                    // drop 3
                    if (m3 < 24) {
                        path3.moveTo(x3, y3);

                    } else {
                        count3 = count3 + 4;
                        path3.moveTo(x3, y3 + count3);
                    }

                    path3.lineTo(x3, y3 + m3 + (float) (centerY * 0.1));
                    canvas.drawPath(path3, paintRain);

                    m3 = m3 + 4;

                    if (m3 == 100) {
                        m3 = 0;
                        count3 = 0;
                    }

                    // drop21
                    if (i < 2 * 36) {

                        if (m2 < 24) {
                            path2.moveTo(x2, y2);

                        } else {
                            count2 = count2 + 4;
                            path2.moveTo(x2, y2 + count2);
                        }

                        path2.lineTo(x2, y2 + m2 + (float) (centerY * 0.1));
                        canvas.drawPath(path2, paintRain);

                        m2 = m2 + 4;

                        if (m2 == 100) {
                            m2 = 0;
                            count2 = 0;
                        }

                    }

                }


            }

            i += 2;

            if (i == 2 * 50) {
                i = 0;
            }

        }

        // drawing cloud with fill
        mPaint.setColor(bgColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), mPaint);

        // drawing cloud with stroke
        mPaint.setColor(strokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), mPaint);

        if (!isStatic) {
            postInvalidateDelayed(33);
        }
    }
}
