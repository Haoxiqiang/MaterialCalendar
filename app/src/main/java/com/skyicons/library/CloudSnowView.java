package com.skyicons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * This view draws cloud with snow.
 */
public class CloudSnowView extends WeatherTemplateView {

    PathPoints[] pathPoints11, pathPoints12, pathPoints21, pathPoints22,
            pointsCircle11, pointsCircle12, pointsCircle21, pointsCircle22;
    int m = 0, n = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0;
    boolean drop11 = true, drop12 = false, drop21 = false,
            drop22 = false, pointsStored = false;
    Cloud cloud;
    private Paint paintCloud, paintSnow;
    private Path path11, path12, path13,
            path21, path22, path23, //visible drawn paths

    cubicPath11, cubicPath12,
            cubicPath21, cubicPath22, //Invisible paths for drop movement

    pathCircle1, pathCircle2; //Invisible paths for rotate operation
    private double count;

    public CloudSnowView(Context context) {
        super(context);
    }

    // Initial declaration of the coordinates.
    public CloudSnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        count = 0;

        float strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());


        paintCloud = new Paint();
        paintSnow = new Paint();

        paintCloud.setColor(strokeColor);
        paintCloud.setAntiAlias(true);
        paintCloud.setStrokeCap(Paint.Cap.ROUND);
        paintCloud.setStrokeJoin(Paint.Join.ROUND);
        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setShadowLayer(0, 0, 0, strokeColor);
        paintCloud.setStrokeWidth(strokeWidth);

        paintSnow.setColor(strokeColor);
        paintSnow.setAntiAlias(true);
        paintSnow.setStrokeCap(Paint.Cap.ROUND);
        paintSnow.setStyle(Paint.Style.STROKE);
        paintSnow.setStrokeWidth(strokeWidth / 3);

        cloud = new Cloud();
        pathCircle1 = new Path();
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

        PointF P1c1 = cloud.getP1c1(centerX, centerY, (int) width, count);
        PointF P1c2 = cloud.getP1c2(centerX, centerY, (int) width, count);
        PointF P2c1 = cloud.getP2c1(centerX, centerY, (int) width, count);
        PointF P2c2 = cloud.getP2c2(centerX, centerY, (int) width, count);

        float P1Y = ((float) ((int) (0.1041667 * width) * Math.sin(Math.toRadians(80 + (0.111 * count))) + centerY));
        float P2Y = ((float) (((int) (0.1041667 * width) + ((0.00023125 * width) * count))
                * Math.sin(Math.toRadians(120 + (0.222 * count))) + centerY));


        if (x1 == 0) {
            x1 = (int) P1c2.x + 10;
        }
        if (y1 == 0) {
            float value = (int) P1c2.y - ((P1c1.y + P1Y) / 2);
            y1 = (int) (P1c2.y - value / 2);
        }

        if (x2 == 0) {
            x2 = (int) P2c2.x + 10;
        }
        if (y2 == 0) {
            float value = (int) P2c2.y - ((P2c1.y + P2Y) / 2);
            y2 = (int) (P2c2.y - value / 2);
        }

        if (!pointsStored) {

            // Store path coordinates for snow fall 1
            cubicPath11 = new Path();
            int h1 = (int) (height - y1);
            cubicPath11.moveTo(x1, y1);
            cubicPath11.cubicTo(x1 - width * 0.06f, y1 + h1 * 0.3f, x1 - width * 0.12f,
                    y1 + h1 * 0.7f, x1 - width * 0.18f, y1 + h1 * 1.1f);
            pathPoints11 = getPoints(cubicPath11, 100);

            // Store path coordinates for snow fall 2
            cubicPath12 = new Path();
            int x = x1 - 5;
            cubicPath12.moveTo(x, y1);
            cubicPath12.cubicTo(x + width * 0.06f, y1 + h1 * 0.3f, x + width * 0.1f,
                    y1 + h1 * 0.7f, x - width * 0.03f, y1 + h1 * 1.1f);
            pathPoints12 = getPoints(cubicPath12, 100);

            // Store path coordinates for snow fall 3
            cubicPath21 = new Path();
            cubicPath21.moveTo(x2, y2);
            cubicPath21.cubicTo(x2 + width * 0.06f, y2 + h1 * 0.3f, x2 + width * 0.12f,
                    y2 + h1 * 0.7f, x2 + width * 0.18f, y2 + h1 * 1.1f);
            pathPoints21 = getPoints(cubicPath21, 100);

            // Store path coordinates for snow fall 4
            cubicPath22 = new Path();
            int xx = x2 + 5;
            cubicPath22.moveTo(xx, y2);
            cubicPath22.cubicTo(xx - width * 0.06f, y2 + h1 * 0.3f, xx - width * 0.1f,
                    y2 + h1 * 0.6f, xx + width * 0.03f, y2 + h1 * 1.1f);
            pathPoints22 = getPoints(cubicPath22, 100);

            pointsStored = true;
        }


        if (isStatic) { //Initial static view

            int x = 55;

            pathCircle2 = new Path();
            pathCircle2.addCircle(pathPoints12[x].getX(), pathPoints12[x].getY(),
                    width * 0.03f, Path.Direction.CW);
            pointsCircle12 = getPoints(pathCircle2, 100);

            //2nd drop
            path21 = new Path();
            path22 = new Path();
            path23 = new Path();

            int a = (25 + x / 5) >= 100 ? 25 + x / 5 - 100 : 25 + x / 5;
            int b = (8 + x / 5) >= 100 ? 8 + x / 5 - 100 : 8 + x / 5;
            int c = (40 + x / 5) >= 100 ? 40 + x / 5 - 100 : 40 + x / 5;

            path21.moveTo(pointsCircle12[a].getX(), pointsCircle12[a].getY());
            path22.moveTo(pointsCircle12[b].getX(), pointsCircle12[b].getY());
            path23.moveTo(pointsCircle12[c].getX(), pointsCircle12[c].getY());

            a = (75 + x / 5) >= 100 ? 75 + x / 5 - 100 : 75 + x / 5;
            b = (59 + x / 5) >= 100 ? 59 + x / 5 - 100 : 59 + x / 5;
            c = (90 + x / 5) >= 100 ? 90 + x / 5 - 100 : 90 + x / 5;

            path21.lineTo(pointsCircle12[a].getX(), (pointsCircle12[a].getY()));
            path22.lineTo(pointsCircle12[b].getX(), (pointsCircle12[b].getY()));
            path23.lineTo(pointsCircle12[c].getX(), (pointsCircle12[c].getY()));

            canvas.drawPath(path21, paintSnow);
            canvas.drawPath(path22, paintSnow);
            canvas.drawPath(path23, paintSnow);

            // drawing cloud with fill
            paintCloud.setColor(bgColor);
            paintCloud.setStyle(Paint.Style.FILL);
            canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

            // drawing cloud with stroke
            paintCloud.setColor(strokeColor);
            paintCloud.setStyle(Paint.Style.STROKE);
            canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

            int y = 35;

            pathCircle2 = new Path();
            pathCircle2.addCircle(pathPoints22[y].getX(), pathPoints22[y].getY(),
                    width * 0.03f, Path.Direction.CW);
            pointsCircle22 = getPoints(pathCircle2, 100);

            //2nd drop
            path21 = new Path();
            path22 = new Path();
            path23 = new Path();

            a = (25 + y / 5) >= 100 ? 25 + y / 5 - 100 : 25 + y / 5;
            b = (8 + y / 5) >= 100 ? 8 + y / 5 - 100 : 8 + y / 5;
            c = (40 + y / 5) >= 100 ? 40 + y / 5 - 100 : 40 + y / 5;

            path21.moveTo(pointsCircle22[a].getX(), pointsCircle22[a].getY());
            path22.moveTo(pointsCircle22[b].getX(), pointsCircle22[b].getY());
            path23.moveTo(pointsCircle22[c].getX(), pointsCircle22[c].getY());

            a = (75 + y / 5) >= 100 ? 75 + y / 5 - 100 : 75 + y / 5;
            b = (59 + y / 5) >= 100 ? 59 + y / 5 - 100 : 59 + y / 5;
            c = (90 + y / 5) >= 100 ? 90 + y / 5 - 100 : 90 + y / 5;

            path21.lineTo(pointsCircle22[a].getX(), (pointsCircle22[a].getY()));
            path22.lineTo(pointsCircle22[b].getX(), (pointsCircle22[b].getY()));
            path23.lineTo(pointsCircle22[c].getX(), (pointsCircle22[c].getY()));

            canvas.drawPath(path21, paintSnow);
            canvas.drawPath(path22, paintSnow);
            canvas.drawPath(path23, paintSnow);

            // drawing cloud with fill
            paintCloud.setColor(bgColor);
            paintCloud.setStyle(Paint.Style.FILL);
            canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

            // drawing cloud with stroke
            paintCloud.setColor(strokeColor);
            paintCloud.setStyle(Paint.Style.STROKE);
            canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);


        } else { // Animating view

            if (drop11) {

                pathCircle1 = new Path();
                pathCircle1.addCircle(pathPoints11[m].getX(), pathPoints11[m].getY(),
                        width * 0.03f, Path.Direction.CW);
                pointsCircle11 = getPoints(pathCircle1, 100);

                //1st drop
                path11 = new Path();
                path12 = new Path();
                path13 = new Path();

                int a = (25 + m / 5) >= 100 ? 25 + m / 5 - 100 : 25 + m / 5;
                int b = (8 + m / 5) >= 100 ? 8 + m / 5 - 100 : 8 + m / 5;
                int c = (40 + m / 5) >= 100 ? 40 + m / 5 - 100 : 40 + m / 5;

                path11.moveTo(pointsCircle11[a].getX(), pointsCircle11[a].getY());
                path12.moveTo(pointsCircle11[b].getX(), pointsCircle11[b].getY());
                path13.moveTo(pointsCircle11[c].getX(), pointsCircle11[c].getY());

                a = (75 + m / 5) >= 100 ? 75 + m / 5 - 100 : 75 + m / 5;
                b = (59 + m / 5) >= 100 ? 59 + m / 5 - 100 : 59 + m / 5;
                c = (90 + m / 5) >= 100 ? 90 + m / 5 - 100 : 90 + m / 5;

                path11.lineTo(pointsCircle11[a].getX(), (pointsCircle11[a].getY()));
                path12.lineTo(pointsCircle11[b].getX(), (pointsCircle11[b].getY()));
                path13.lineTo(pointsCircle11[c].getX(), (pointsCircle11[c].getY()));

                canvas.drawPath(path11, paintSnow);
                canvas.drawPath(path12, paintSnow);
                canvas.drawPath(path13, paintSnow);

                // drawing cloud with fill
                paintCloud.setColor(bgColor);
                paintCloud.setStyle(Paint.Style.FILL);
                canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                // drawing cloud with stroke
                paintCloud.setColor(strokeColor);
                paintCloud.setStyle(Paint.Style.STROKE);
                canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                m = m + 1;

                if (m > 75) {

                    pathCircle2 = new Path();
                    pathCircle2.addCircle(pathPoints12[n].getX(), pathPoints12[n].getY(),
                            width * 0.03f, Path.Direction.CW);
                    pointsCircle12 = getPoints(pathCircle2, 100);

                    //2nd drop
                    path21 = new Path();
                    path22 = new Path();
                    path23 = new Path();

                    a = (25 + n / 5) >= 100 ? 25 + n / 5 - 100 : 25 + n / 5;
                    b = (8 + n / 5) >= 100 ? 8 + n / 5 - 100 : 8 + n / 5;
                    c = (40 + n / 5) >= 100 ? 40 + n / 5 - 100 : 40 + n / 5;

                    path21.moveTo(pointsCircle12[a].getX(), pointsCircle12[a].getY());
                    path22.moveTo(pointsCircle12[b].getX(), pointsCircle12[b].getY());
                    path23.moveTo(pointsCircle12[c].getX(), pointsCircle12[c].getY());

                    a = (75 + n / 5) >= 100 ? 75 + n / 5 - 100 : 75 + n / 5;
                    b = (59 + n / 5) >= 100 ? 59 + n / 5 - 100 : 59 + n / 5;
                    c = (90 + n / 5) >= 100 ? 90 + n / 5 - 100 : 90 + n / 5;

                    path21.lineTo(pointsCircle12[a].getX(), (pointsCircle12[a].getY()));
                    path22.lineTo(pointsCircle12[b].getX(), (pointsCircle12[b].getY()));
                    path23.lineTo(pointsCircle12[c].getX(), (pointsCircle12[c].getY()));

                    canvas.drawPath(path21, paintSnow);
                    canvas.drawPath(path22, paintSnow);
                    canvas.drawPath(path23, paintSnow);

                    // drawing cloud with fill
                    paintCloud.setColor(bgColor);
                    paintCloud.setStyle(Paint.Style.FILL);
                    canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                    // drawing cloud with stroke
                    paintCloud.setColor(strokeColor);
                    paintCloud.setStyle(Paint.Style.STROKE);
                    canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                    n = n + 1;

                }

                if (m == 100) {
                    m = 0;
                    path11.reset();
                    path11.moveTo(0, 0);
                    path12.reset();
                    path12.moveTo(0, 0);
                    path13.reset();
                    path13.moveTo(0, 0);

                    x1 = 0;
                    y1 = 0;

                    x2 = 0;
                    y2 = 0;

                    drop12 = true;
                    drop11 = false;

                }

            }

            if (drop12) {

                pathCircle2 = new Path();
                pathCircle2.addCircle(pathPoints12[n].getX(), pathPoints12[n].getY(),
                        width * 0.03f, Path.Direction.CW);
                pointsCircle12 = getPoints(pathCircle2, 100);

                //2nd drop
                path21 = new Path();
                path22 = new Path();
                path23 = new Path();

                int a = (25 + n / 5) >= 100 ? 25 + n / 5 - 100 : 25 + n / 5;
                int b = (8 + n / 5) >= 100 ? 8 + n / 5 - 100 : 8 + n / 5;
                int c = (40 + n / 5) >= 100 ? 40 + n / 5 - 100 : 40 + n / 5;

                path21.moveTo(pointsCircle12[a].getX(), pointsCircle12[a].getY());
                path22.moveTo(pointsCircle12[b].getX(), pointsCircle12[b].getY());
                path23.moveTo(pointsCircle12[c].getX(), pointsCircle12[c].getY());

                a = (75 + n / 5) >= 100 ? 75 + n / 5 - 100 : 75 + n / 5;
                b = (59 + n / 5) >= 100 ? 59 + n / 5 - 100 : 59 + n / 5;
                c = (90 + n / 5) >= 100 ? 90 + n / 5 - 100 : 90 + n / 5;

                path21.lineTo(pointsCircle12[a].getX(), (pointsCircle12[a].getY()));
                path22.lineTo(pointsCircle12[b].getX(), (pointsCircle12[b].getY()));
                path23.lineTo(pointsCircle12[c].getX(), (pointsCircle12[c].getY()));

                canvas.drawPath(path21, paintSnow);
                canvas.drawPath(path22, paintSnow);
                canvas.drawPath(path23, paintSnow);

                // drawing cloud with fill
                paintCloud.setColor(bgColor);
                paintCloud.setStyle(Paint.Style.FILL);
                canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                // drawing cloud with stroke
                paintCloud.setColor(strokeColor);
                paintCloud.setStyle(Paint.Style.STROKE);
                canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                n = n + 1;

                if (n == 100) {
                    m = 0;
                    n = 0;
                    path21.reset();
                    path21.moveTo(0, 0);
                    path22.reset();
                    path22.moveTo(0, 0);
                    path23.reset();
                    path23.moveTo(0, 0);

                    x1 = 0;
                    y1 = 0;

                    drop21 = true;
                    drop11 = false;
                    drop12 = false;

                }

            }

            if (drop21) {

                pathCircle1 = new Path();
                pathCircle1.addCircle(pathPoints21[m].getX(), pathPoints21[m].getY(),
                        width * 0.03f, Path.Direction.CW);
                pointsCircle21 = getPoints(pathCircle1, 100);

                //1st drop
                path11 = new Path();
                path12 = new Path();
                path13 = new Path();

                int a = (25 + m / 5) >= 100 ? 25 + m / 5 - 100 : 25 + m / 5;
                int b = (8 + m / 5) >= 100 ? 8 + m / 5 - 100 : 8 + m / 5;
                int c = (40 + m / 5) >= 100 ? 40 + m / 5 - 100 : 40 + m / 5;

                path11.moveTo(pointsCircle21[a].getX(), pointsCircle21[a].getY());
                path12.moveTo(pointsCircle21[b].getX(), pointsCircle21[b].getY());
                path13.moveTo(pointsCircle21[c].getX(), pointsCircle21[c].getY());

                a = (75 + m / 5) >= 100 ? 75 + m / 5 - 100 : 75 + m / 5;
                b = (59 + m / 5) >= 100 ? 59 + m / 5 - 100 : 59 + m / 5;
                c = (90 + m / 5) >= 100 ? 90 + m / 5 - 100 : 90 + m / 5;

                path11.lineTo(pointsCircle21[a].getX(), (pointsCircle21[a].getY()));
                path12.lineTo(pointsCircle21[b].getX(), (pointsCircle21[b].getY()));
                path13.lineTo(pointsCircle21[c].getX(), (pointsCircle21[c].getY()));

                canvas.drawPath(path11, paintSnow);
                canvas.drawPath(path12, paintSnow);
                canvas.drawPath(path13, paintSnow);

                // drawing cloud with fill
                paintCloud.setColor(bgColor);
                paintCloud.setStyle(Paint.Style.FILL);
                canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                // drawing cloud with stroke
                paintCloud.setColor(strokeColor);
                paintCloud.setStyle(Paint.Style.STROKE);
                canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                m = m + 1;

                if (m > 75) {

                    pathCircle2 = new Path();
                    pathCircle2.addCircle(pathPoints22[n].getX(), pathPoints22[n].getY(),
                            width * 0.03f, Path.Direction.CW);
                    pointsCircle22 = getPoints(pathCircle2, 100);

                    //2nd drop
                    path21 = new Path();
                    path22 = new Path();
                    path23 = new Path();

                    a = (25 + n / 5) >= 100 ? 25 + n / 5 - 100 : 25 + n / 5;
                    b = (8 + n / 5) >= 100 ? 8 + n / 5 - 100 : 8 + n / 5;
                    c = (40 + n / 5) >= 100 ? 40 + n / 5 - 100 : 40 + n / 5;

                    path21.moveTo(pointsCircle22[a].getX(), pointsCircle22[a].getY());
                    path22.moveTo(pointsCircle22[b].getX(), pointsCircle22[b].getY());
                    path23.moveTo(pointsCircle22[c].getX(), pointsCircle22[c].getY());

                    a = (75 + n / 5) >= 100 ? 75 + n / 5 - 100 : 75 + n / 5;
                    b = (59 + n / 5) >= 100 ? 59 + n / 5 - 100 : 59 + n / 5;
                    c = (90 + n / 5) >= 100 ? 90 + n / 5 - 100 : 90 + n / 5;

                    path21.lineTo(pointsCircle22[a].getX(), (pointsCircle22[a].getY()));
                    path22.lineTo(pointsCircle22[b].getX(), (pointsCircle22[b].getY()));
                    path23.lineTo(pointsCircle22[c].getX(), (pointsCircle22[c].getY()));

                    canvas.drawPath(path21, paintSnow);
                    canvas.drawPath(path22, paintSnow);
                    canvas.drawPath(path23, paintSnow);

                    // drawing cloud with fill
                    paintCloud.setColor(bgColor);
                    paintCloud.setStyle(Paint.Style.FILL);
                    canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                    // drawing cloud with stroke
                    paintCloud.setColor(strokeColor);
                    paintCloud.setStyle(Paint.Style.STROKE);
                    canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                    n = n + 1;

                }

                if (m == 100) {
                    m = 0;
                    path11.reset();
                    path11.moveTo(0, 0);
                    path12.reset();
                    path12.moveTo(0, 0);
                    path13.reset();
                    path13.moveTo(0, 0);

                    x1 = 0;
                    y1 = 0;

                    drop22 = true;
                    drop21 = false;
                }

            }

            if (drop22) {

                pathCircle2 = new Path();
                pathCircle2.addCircle(pathPoints22[n].getX(), pathPoints22[n].getY(),
                        width * 0.03f, Path.Direction.CW);
                pointsCircle22 = getPoints(pathCircle2, 100);

                //2nd drop
                path21 = new Path();
                path22 = new Path();
                path23 = new Path();

                int a = (25 + n / 5) >= 100 ? 25 + n / 5 - 100 : 25 + n / 5;
                int b = (8 + n / 5) >= 100 ? 8 + n / 5 - 100 : 8 + n / 5;
                int c = (40 + n / 5) >= 100 ? 40 + n / 5 - 100 : 40 + n / 5;

                path21.moveTo(pointsCircle22[a].getX(), pointsCircle22[a].getY());
                path22.moveTo(pointsCircle22[b].getX(), pointsCircle22[b].getY());
                path23.moveTo(pointsCircle22[c].getX(), pointsCircle22[c].getY());

                a = (75 + n / 5) >= 100 ? 75 + n / 5 - 100 : 75 + n / 5;
                b = (59 + n / 5) >= 100 ? 59 + n / 5 - 100 : 59 + n / 5;
                c = (90 + n / 5) >= 100 ? 90 + n / 5 - 100 : 90 + n / 5;

                path21.lineTo(pointsCircle22[a].getX(), (pointsCircle22[a].getY()));
                path22.lineTo(pointsCircle22[b].getX(), (pointsCircle22[b].getY()));
                path23.lineTo(pointsCircle22[c].getX(), (pointsCircle22[c].getY()));

                canvas.drawPath(path21, paintSnow);
                canvas.drawPath(path22, paintSnow);
                canvas.drawPath(path23, paintSnow);

                // drawing cloud with fill
                paintCloud.setColor(bgColor);
                paintCloud.setStyle(Paint.Style.FILL);
                canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                // drawing cloud with stroke
                paintCloud.setColor(strokeColor);
                paintCloud.setStyle(Paint.Style.STROKE);
                canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), paintCloud);

                n = n + 1;

                if (n == 100) {
                    m = 0;
                    n = 0;
                    path21.reset();
                    path21.moveTo(0, 0);
                    path22.reset();
                    path22.moveTo(0, 0);
                    path23.reset();
                    path23.moveTo(0, 0);

                    x1 = 0;
                    y1 = 0;

                    drop11 = true;
                    drop12 = false;
                    drop21 = false;
                    drop22 = false;
                }
            }

        }

        if (!isStatic) {
            postInvalidateDelayed(33);
        }

    }
}