package info.hxq.materialcalendar.proxy;

import android.content.Context;
import android.content.SharedPreferences;

import info.hxq.materialcalendar.base.MainApplication;

/**
 * Created by haoxiqiang on 15/3/23.
 */
public class SettingProxy {

    private static final String SPNAME = "app-setting";
    private static SharedPreferences mSharedPreferences;

    public static SharedPreferences getInstance() {
        if (mSharedPreferences == null) {
            mSharedPreferences = MainApplication.getApplication().getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static boolean hasTabooInit() {
        return getInstance().getBoolean("taboo-init", false);
    }

    public static void saveTabooInit() {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean("taboo-init", true);
        editor.apply();
    }
}
