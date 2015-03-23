package info.hxq.materialcalendar.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.List;

import info.hxq.materialcalendar.db.DatabaseHelper;
import info.hxq.materialcalendar.proxy.SettingProxy;

/**
 * Created by haoxiqiang on 15/3/20.
 */
public class MainApplication extends Application {

    private static Context context;

    public static Context getApplication() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        SettingProxy.init();

        if (!isMainProcess()) {
            return;
        }

        DatabaseHelper.init();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        if (!isMainProcess()) {
            return;
        }

        DatabaseHelper.closeDatabase();
        super.onTerminate();
    }

    private boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }

        return false;
    }
}
