package info.hxq.materialcalendar.tool;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import info.hxq.materialcalendar.base.MainApplication;

/**
 * Created by haoxiqiang on 15/3/23.
 */
public class StorageUtils {

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String INDIVIDUAL_DIR_NAME = "calendar-images";

    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState = null;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) {
        }
        if ((preferExternal) && ("mounted".equals(externalStorageState)) &&
                (hasExternalStoragePermission(context))) {
            appCacheDir = getExternalCacheDir(context, "cache");
        }

        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }

        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            Logger.w("Can't define system cache directory! '%s' will be used." + cacheDirPath);
            appCacheDir = new File(cacheDirPath);
        }

        return appCacheDir;
    }

    public static File getIndividualCacheDirectory(Context context) {
        File cacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(cacheDir, "tifen-images");
        if ((!individualCacheDir.exists()) &&
                (!individualCacheDir.mkdir())) {
            individualCacheDir = cacheDir;
        }

        return individualCacheDir;
    }

    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if (("mounted".equals(Environment.getExternalStorageState())) &&
                (hasExternalStoragePermission(context))) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if ((appCacheDir == null) || ((!appCacheDir.exists()) && (!appCacheDir.mkdirs()))) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context, String type) {
        File appCacheDir =
                new File(new File(Environment.getExternalStorageDirectory(), "tifen"), type);
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Logger.w("Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Logger.i("Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }
}
