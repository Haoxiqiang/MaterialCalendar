package com.skyicons.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import info.hxq.materialcalendar.R;

/**
 * Created by hxq on 15/3/21.
 */
public abstract class WeatherTemplateView extends View {

    protected static Paint mPaint = new Paint();
    protected boolean isStatic = false;
    protected int strokeColor = Color.WHITE;
    protected int bgColor = Color.TRANSPARENT;
    protected float width, height, centerX, centerY;
    protected Path mPath = new Path();

    public WeatherTemplateView(Context context) {
        super(context);
        init();
    }

    public WeatherTemplateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkyIcon);
        if (typedArray != null) {
            // get attributes from layout
            isStatic = typedArray.getBoolean(R.styleable.SkyIcon_isStatic, this.isStatic);
            strokeColor = typedArray.getColor(R.styleable.SkyIcon_strokeColor, this.strokeColor);
            bgColor = typedArray.getColor(R.styleable.SkyIcon_bgColor, this.bgColor);
            typedArray.recycle();
        }
        init();
    }

    protected abstract void init();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(widthMeasureSpec);

        centerX = width / 2;
        centerY = height / 2;

        mPath.reset();
        mPaint.setStrokeWidth((float) (0.02083 * width));
    }

    protected PathPoints[] getPoints(Path path, int size) {

        //Size of 100 indicates that, 100 points
        // would be extracted from the path
        PathPoints[] pointArray = new PathPoints[size];
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        float distance = 0f;
        float speed = length / size;
        int counter = 0;
        float[] aCoordinates = new float[2];

        while ((distance < length) && (counter < size)) {
            pm.getPosTan(distance, aCoordinates, null);
            pointArray[counter] = new PathPoints(aCoordinates[0], aCoordinates[1]);
            counter++;
            distance = distance + speed;
        }

        return pointArray;
    }

    static class PathPoints {

        float x, y;

        public PathPoints(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

    }
}
