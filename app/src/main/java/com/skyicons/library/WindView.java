package com.skyicons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Created by administrator on 18/09/14.
 */

public class WindView extends WeatherTemplateView {

    int degrees;
    boolean isFirstPath;
    PathPoints[] points;
    private float X2, Y2, X11, Y11, Y21, X21, Xc, Yc;
    private Path tracePath, windPath, leafPath;
    private double count;

    public WindView(Context context) {
        super(context);
    }

    public WindView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        isFirstPath = true;
        windPath = new Path();
        leafPath = new Path();
        tracePath = new Path();
        count = 0;
        degrees = 200;

        float strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());


        mPaint.setColor(strokeColor);
        mPaint.setStrokeWidth(width / 25);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        // set stroke width
        mPaint.setStrokeWidth(strokeWidth);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerY = (float) (height / 1.5);
        tracePath.moveTo(centerX, centerY);


        // set canvas background color
        canvas.drawColor(bgColor);

        //initializing the paths
        windPath = new Path();
        leafPath = new Path();

        if (count > 310) {
            count = count % 310;
            //resetting counter and initializing values on completion of a rotation
            tracePath = new Path(); //reinitializing tracepath
            count = 0; //resetting counter
            degrees = 200;//resetting leaf's starting angle
            isFirstPath = !isFirstPath;//resetting path flag
        } else {
            count += 10;
        }


        if (isFirstPath) {

            // trace for first path
            centerX = width / 3;
            centerY = height / 1.5f;
            X2 = width + 50;
            Y2 = height / 4;

            tracePath.moveTo(centerX, centerY);
            PointF P1c1 = calculateTriangle(centerX, centerY, X2, Y2, true, 0.2, "CCW");
            PointF P1c2 = calculateTriangle(centerX, centerY, X2, Y2, false, 0.2, "CCW");
            tracePath.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, X2, Y2);

            //getting points from the trace path
            points = getPoints(tracePath, 250);

        } else {
            // trace for second path

            centerX = width / 3;
            centerY = height / 1.5f;


            X2 = width / 2.0f;
            Y2 = height / 3.0f;
            Xc = width + 10;
            Yc = height / 2.0f;

            tracePath.moveTo(centerX - 5, centerY);
            tracePath.cubicTo(centerX + (width / 3.2f), centerY + (width / 12), centerX + (width / 2.0f), centerY - (width / 3.2f), centerX + (width / 2.5f), centerY - (width / 2.8235f));
            tracePath.cubicTo(centerX + (width / 2.7f), centerY - (width / 2.7428f), centerX + (width / 3.2f), centerY - (width / 3f), centerX + (width / 3.0f), centerY - (width / 3.6923f));
            tracePath.cubicTo(centerX + (width / 2.6f), centerY - (width / 6), centerX + (width / 1.5f), centerY - (width / 9.6f), width + 50, centerY - (width / 2.4f));

            //getting points from the trace path
            points = getPoints(tracePath, 250);

        }

        if (count <= 20) {
            // draw nothing

        } else if ((count > 20) && (count <= 60)) {
            // draw initial path of length 60
            for (int i = 0; i < (count - 20); i++) {

                windPath.moveTo(points[i].getX(), points[i].getY());
                windPath.lineTo(points[i + 1].getX(), points[i + 1].getY());

            }
        } else if (count >= 249) {
            // draw path of decrementing length from last

            for (int i = (int) count - 60; i <= 248; i++) {

                windPath.moveTo(points[i].getX(), points[i].getY());
                windPath.lineTo(points[i + 1].getX(), points[i + 1].getY());

            }

        } else {
            // move initial path of length 60
            for (int i = (int) (count - 60); i < (count - 20); i++) {

                windPath.moveTo(points[i].getX(), points[i].getY());
                windPath.lineTo(points[i + 1].getX(), points[i + 1].getY());

            }

        }

        // draw windpath

        canvas.drawPath(windPath, mPaint);

        if ((int) count < 250) {

            // initialize coordinates for leaf
            Xc = points[(int) (count)].getX();
            Yc = points[(int) (count)].getY();
            X11 = (float) (((width * 4) / 100) * Math.cos(Math.toRadians
                    ((degrees + count) - 30)) + Xc);
            Y11 = (float) (((width * 4) / 100) * Math.sin(Math.toRadians((degrees + count) - 30)) + Yc);
            X21 = (float) (((width * 12) / 100) * Math.cos(Math.toRadians((degrees + count))) + Xc);
            Y21 = (float) (((width * 12) / 100) * Math.sin(Math.toRadians((degrees + count))) + Yc);

            // getting points in between coordinates for leaf shape
            PointF P11c1 = calculateTriangle(Xc, Yc, X21, Y21, true, 0.7, "CW");
            PointF P11c2 = calculateTriangle(Xc, Yc, X21, Y21, false, 0.7, "CW");
            PointF P21c1 = calculateTriangle(X21, Y21, X11, Y11, true, 0.8, "CW");
            PointF P21c2 = calculateTriangle(X21, Y21, X11, Y11, false, 0.8, "CW");
            PointF P31c1 = calculateTriangle(X11, Y11, Xc, Yc, true, 0.2, "CCW");
            PointF P31c2 = calculateTriangle(X11, Y11, Xc, Yc, false, 0.2, "CCW");

            // drawing arcs between coordinates
            leafPath.moveTo(Xc, Yc);
            leafPath.cubicTo(P11c1.x, P11c1.y, P11c2.x, P11c2.y, X21, Y21);
            leafPath.cubicTo(P21c1.x, P21c1.y, P21c2.x, P21c2.y, X11, Y11);
            leafPath.cubicTo(P31c1.x, P31c1.y, P31c2.x, P31c2.y, Xc, Yc);

            // drawing leaf on canvas
            mPaint.setColor(bgColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(leafPath, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(strokeColor);
            canvas.drawPath(leafPath, mPaint);
        }

        if (!isStatic) {
            postInvalidateDelayed(33);
        }

    }

    private PointF calculateTriangle(float x1, float y1, float x2, float y2, boolean left, double distOffset, String dir) {

        PointF result = new PointF(0, 0);
        // finding center point between the coordinates
        float dy = y2 - y1;
        float dx = x2 - x1;


        float dangle = 0;
        float sideDist = 0;

        // calculating angle and the distance between center and the two points with direction
        if (dir == "CW") {
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI / 2f));
            sideDist = (float) distOffset * (float) Math.sqrt(dx * dx + dy * dy); //square
        } else if (dir == "CCW") {
            dangle = (float) ((Math.atan2(dy, dx) + Math.PI / 2f));
            sideDist = (float) distOffset * (float) Math.sqrt(dx * dx + dy * dy); //square
        }

        if (left) {
            //point from center to the left
            result.x = (int) (Math.cos(dangle) * sideDist + x1);
            result.y = (int) (Math.sin(dangle) * sideDist + y1);
        } else {
            //point from center to the right
            result.x = (int) (Math.cos(dangle) * sideDist + x2);
            result.y = (int) (Math.sin(dangle) * sideDist + y2);
        }
        return result;
    }

}
