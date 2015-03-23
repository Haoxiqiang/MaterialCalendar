package com.skyicons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * This view draws the Moon.
 */
public class MoonView extends WeatherTemplateView {

    PathPoints[] pathPoints;
    float m = 0;
    float radius;
    boolean clockwise = false;
    float a = 0, b = 0, c = 0, d = 0;
    int count = 0; //counter for stopping animation

    RectF mRectF = new RectF();

    public MoonView(Context context) {
        super(context);
    }

    public MoonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        //Paint for drawing Moon
        mPaint.setColor(strokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        int strokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

        mPaint.setStrokeWidth(strokeWidth);
        radius = strokeWidth * 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // set canvas background color
        canvas.drawColor(bgColor);
        if (count > 360) {
            count = count % 360;
        } else {
            count += 15;
        }

        mPath.reset();


        if (!clockwise) {//Anticlockwise rotation

            // First arc of the Moon.
            mRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            mPath.addArc(mRectF, 65 - (m / 2), 275);

            pathPoints = getPoints(mPath, 1000);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF P1c1 = cubic2Points(a, b, c, d, true);
            PointF P1c2 = cubic2Points(a, b, c, d, false);

            // Second arc of the Moon in opposite face.
            mPath.moveTo(a, b);
            mPath.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, c, d);

            canvas.drawPath(mPath, mPaint);

            m = m + 0.5f;

            if (m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        } else {//Clockwise rotation

            // First arc of the Moon.
            mRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            mPath.addArc(mRectF, 15 + (m / 2), 275);

            pathPoints = getPoints(mPath, 1000);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF P1c1 = cubic2Points(a, b, c, d, true);
            PointF P1c2 = cubic2Points(a, b, c, d, false);

            // Second arc of the Moon in opposite face.
            mPath.moveTo(a, b);
            mPath.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, c, d);

            canvas.drawPath(mPath, mPaint);

            m = m + 0.5f;

            if (m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        }
        if (!isStatic) {
            postInvalidateDelayed(50);
        }

    }


    private PointF cubic2Points(float x1, float y1, float x2, float y2, boolean left) {

        PointF result = new PointF(0, 0);
        // finding center point between the coordinates
        float dy = y2 - y1;
        float dx = x2 - x1;
        // calculating angle and the distance between center and the two points
        float dangle = (float) ((Math.atan2(dy, dx) - Math.PI / 2f));
        float sideDist = (float) -0.6 * (float) Math.sqrt(dx * dx + dy * dy); //square

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
