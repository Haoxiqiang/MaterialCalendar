package com.thbs.skycons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * This view draws cloud with Moon.
 */
public class CloudMoonView extends WeatherTemplateView {

    Paint paintCloud, paintMoon;
    Path pathMoon;
    float m = 0;
    float radius;
    boolean clockwise = false;
    float a = 0, b = 0, c = 0, d = 0;
    Cloud cloud;
    private float X2, Y2;
    private double count;
    PathPoints[] pathPoints;

    public CloudMoonView(Context context) {
        super(context);
    }

    public CloudMoonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {

        float strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());


        //Paint for drawing cloud
        paintCloud = new Paint();
        paintCloud.setStrokeCap(Paint.Cap.ROUND);
        paintCloud.setStrokeJoin(Paint.Join.ROUND);
        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setAntiAlias(true);
        paintCloud.setShadowLayer(0, 0, 0, strokeColor);

        //Paint for drawing Moon
        paintMoon = new Paint();
        paintMoon.setColor(strokeColor);
        paintMoon.setAntiAlias(true);
        paintMoon.setStrokeCap(Paint.Cap.ROUND);
        paintMoon.setStyle(Paint.Style.STROKE);

        paintCloud.setStrokeWidth(strokeWidth);
        paintMoon.setStrokeWidth(strokeWidth);

        count = 0;
        cloud = new Cloud();
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

        // Moon shape
        pathMoon = new Path();
        RectF rectF1 = new RectF();

        PointF P5c1 = cloud.getP5c1(centerX, centerY, (int) width, count);
        if (X2 == 0) {
            X2 = P5c1.x;
            Y2 = P5c1.y + (int) (0.042 * width);

            radius = (int) (0.1042 * width);
        }

        if (!clockwise) { //Anticlockwise rotation

            // First arc of the Moon.
            rectF1.set(X2 - radius, Y2 - radius, X2 + radius, Y2 + radius);
            pathMoon.addArc(rectF1, 65 - (m / 2), 275);

            pathPoints = getPoints(pathMoon,1000);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF p1 = cubic2Points(a, b, c, d, true);
            PointF p2 = cubic2Points(a, b, c, d, false);

            // Second arc of the Moon in opposite face.
            pathMoon.moveTo(a, b);
            pathMoon.cubicTo(p1.x, p1.y, p2.x, p2.y, c, d);

            canvas.drawPath(pathMoon, paintMoon);

            m = m + 0.5f;

            if (m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        } else { //Clockwise rotation

            // First arc of the Moon.
            rectF1.set(X2 - radius, Y2 - radius, X2 + radius, Y2 + radius);
            pathMoon.addArc(rectF1, 15 + (m / 2), 275);

            pathPoints = getPoints(pathMoon,1000);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF p1 = cubic2Points(a, b, c, d, true);
            PointF p2 = cubic2Points(a, b, c, d, false);

            // Second arc of the Moon in opposite face.
            pathMoon.moveTo(a, b);
            pathMoon.cubicTo(p1.x, p1.y, p2.x, p2.y, c, d);

            canvas.drawPath(pathMoon, paintMoon);

            m = m + 0.5f;

            if (m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        }

        // drawing cloud with fill
        paintCloud.setColor(bgColor);
        paintCloud.setStyle(Paint.Style.FILL);
        canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

        // drawing cloud with stroke
        paintCloud.setColor(strokeColor);
        paintCloud.setStyle(Paint.Style.STROKE);
        canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

        if (!isStatic) {
            postInvalidateDelayed(33);
        }

    }

    // Used to get cubic 2 points between staring & end coordinates.
    private PointF cubic2Points(float x1, float y1, float x2,
                                float y2, boolean left) {

        PointF result = new PointF(0, 0);
        // finding center point between the coordinates
        float dy = y2 - y1;
        float dx = x2 - x1;
        // calculating angle and the distance between center and the two points
        float dangle = (float) ((Math.atan2(dy, dx) - Math.PI / 2f));
        float sideDist = (float) -0.5 * (float) Math.sqrt(dx * dx + dy * dy); //square

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
