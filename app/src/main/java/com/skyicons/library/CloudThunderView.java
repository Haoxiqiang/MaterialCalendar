package com.skyicons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;

public class CloudThunderView extends WeatherTemplateView {


    int ctr = 0;
    int ctr2 = 0;
    float thHeight;
    PathPoints[] leftPoints;
    Boolean check;
    Cloud cloud;
    private Path thPath, thFillPath;
    private double count = 0;

    public CloudThunderView(Context context) {
        super(context);
    }

    public CloudThunderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        count = 0;
        check = false;
        thHeight = 0;
        thPath = new Path();
        thFillPath = new Path();

        int strokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        mPaint.setColor(strokeColor);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        cloud = new Cloud();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bgColor);

        if (count > 360) {
            count = count % 360;
        } else {
            count += 15;
        }

        PointF P2c1 = cloud.getP2c1(centerX, centerY, (int) width, count);

        //Setting up the height of thunder from the cloud
        if (thHeight == 0) {
            thHeight = P2c1.y;
        }
        float startHeight = thHeight - (thHeight * 0.1f);

        //Setting up X coordinates of thunder
        float path2StartX = centerX + (centerX * 0.04f);


        //Calculating coordinates of thunder

        thPath.reset();
        thPath.moveTo(path2StartX, startHeight);
        thPath.lineTo(centerX - (centerX * 0.1f), startHeight + (startHeight * 0.2f)); //1
        thPath.lineTo(centerX + (centerX * 0.03f), startHeight + (startHeight * 0.15f));
        thPath.lineTo(centerX - (centerX * 0.08f), startHeight + (startHeight * 0.3f));

        leftPoints = getPoints(thPath, 100);

        if (ctr <= 98) {

            if (check == false) {
                thFillPath.moveTo(leftPoints[ctr].getX(), leftPoints[ctr].getY());
                thFillPath.lineTo(leftPoints[ctr + 1].getX(), leftPoints[ctr + 1].getY());
            } else {
                //Once filled, erasing the fill from top to bottom
                thFillPath.reset();
                thFillPath.moveTo(leftPoints[ctr].getX(), leftPoints[ctr].getY());
                for (int i = ctr + 1; i < leftPoints.length - 1; i++) {
                    thFillPath.lineTo(leftPoints[i].getX(), leftPoints[i].getY());
                }
            }
            ctr = ctr + 1;
        } else {
            if (isStatic) {
                if (ctr2 == 2) {
                    ctr2 = 0;
                }
                ctr2++;
            }

            ctr = 0;
            if (check == false) {
                check = true;
            } else {
                check = false;
            }
        }
        canvas.drawPath(thFillPath, mPaint);
        canvas.drawPath(cloud.getCloud(centerX, centerY, (int) width, count), mPaint);

        if (!isStatic) {
            postInvalidateDelayed(50);
        }

    }
}