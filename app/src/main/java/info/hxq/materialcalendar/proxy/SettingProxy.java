package info.hxq.materialcalendar.proxy;

import android.content.Context;
import android.content.SharedPreferences;

import info.hxq.materialcalendar.base.MainApplication;

/**
 * Created by haoxiqiang on 15/3/23.
 */
public class SettingProxy {

    private static SharedPreferences mSharedPreferences;
    private static final String SPNAME = "app-setting";

    public static void init() {
        if (mSharedPreferences == null) {
            mSharedPreferences = MainApplication.getApplication().getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        }
    }

    public static boolean hasTabooInit() {
        return mSharedPreferences.getBoolean("taboo-init", false);
    }

    public static void saveTabooInit() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("taboo-init", true);
        editor.apply();
    }
}
