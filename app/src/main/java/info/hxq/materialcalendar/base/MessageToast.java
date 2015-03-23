package info.hxq.materialcalendar.base;

import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.SuperToast;

import info.hxq.materialcalendar.R;

/**
 * Created by haoxiqiang on 15/3/18.
 */
public final class MessageToast {

    public static class Style {
        public static final int INFO = R.drawable.supertoast_blue;
        public static final int ALERT = R.drawable.supertoast_red;
    }

    public static void show(String msg, int style) {
        SuperToast superToast = SuperToast.create(MainApplication.getApplication(), msg, SuperToast.Duration.SHORT);
        TextView superToastTextView=superToast.getTextView();
        superToastTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        superToastTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        superToast.setBackground(style);
        superToast.setAnimations(SuperToast.Animations.FADE);
        superToast.show();
    }

    public static void cancelAllToast(){
        SuperToast.cancelAllSuperToasts();
    }

}
