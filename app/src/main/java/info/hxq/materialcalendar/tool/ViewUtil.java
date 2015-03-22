package info.hxq.materialcalendar.tool;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class ViewUtil {
    public static float centerX(View view){
        return com.nineoldandroids.view.ViewHelper.getX(view) + view.getWidth()/2;
    }

    public static float centerY(View view){
        return com.nineoldandroids.view.ViewHelper.getY(view) + view.getHeight()/2;
    }
}
