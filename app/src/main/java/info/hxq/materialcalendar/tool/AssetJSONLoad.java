package info.hxq.materialcalendar.tool;

import android.content.res.AssetManager;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import info.hxq.materialcalendar.base.MainApplication;

/**
 * Created by haoxiqiang on 15/3/23.
 */
public class AssetJSONLoad {


    public static JSONArray loadJsonArray(String transId) throws JSONException, IOException {
        AssetManager mAssetManager = MainApplication.getApplication().getAssets();
        InputStream open = mAssetManager.open("calendar/json/" + transId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(open));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = reader.readLine()) != null) {
            sb.append(new String(s.getBytes(), "UTF-8"));
        }
        reader.close();
        return new JSONArray(sb.toString());
    }

    public static JSONObject loadJson(String transId) throws JSONException, IOException {
        AssetManager mAssetManager = MainApplication.getApplication().getAssets();
        InputStream open = mAssetManager.open("calendar/json/" + transId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(open));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = reader.readLine()) != null) {
            sb.append(new String(s.getBytes(), "UTF-8"));
        }
        reader.close();
        return new JSONObject(sb.toString());
    }

    public static void json2file(String transId, String content) throws IOException {

        File f =
                new File(new File(Environment.getExternalStorageDirectory(), "calendar"), "json");
        if (!f.exists()) {
            f.mkdirs();
        }
        File of = new File(f, transId);
        FileOutputStream fos = new FileOutputStream(of);
        fos.write(content.getBytes("utf-8"));
        fos.close();
    }

}
