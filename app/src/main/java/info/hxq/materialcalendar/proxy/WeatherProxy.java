package info.hxq.materialcalendar.proxy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.hxq.materialcalendar.db.DatabaseHelper;
import info.hxq.materialcalendar.entity.Weather;
import info.hxq.materialcalendar.web.RQManager;

import static com.android.volley.Request.Method;

/**
 * Created by haoxiqiang on 15/3/20.
 */
public final class WeatherProxy {

    public static final String TABLENAME = "weather";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLENAME + "(" +
                    "'date' text(8,0) PRIMARY KEY NOT NULL," +
                    "'currentCity' text," +
                    "'pm25' text," +
                    "'info' text," +
                    "'weather_data' text" +
                    ");";
    private static final String WEATHERURL = "http://api.map.baidu.com/telematics/v3/weather?" +
            "location=北京&output=json&ak=2gbq3O7pe0QC5tuFnURSG6ZF" +
            "&mcode=AF:81:5F:33:1B:83:07:21:E4:AB:9F:4D:31:BB:BE:FE:42:B3:E4:31;info.hxq.materialcalendar";

    private static final String WE = "http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&output=json" +
            "&ak=2gbq3O7pe0QC5tuFnURSG6ZF&mcode=AF:81:5F:33:1B:83:07:21:E4:AB:9F:4D:31:BB:BE:FE:42:B3:E4:31;" +
            "info.hxq.materialcalendar&date=20150325";

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion < DatabaseHelper.DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLENAME + ";");
            db.execSQL(CREATE_TABLE);
        }
    }


    public static void fetchWeatherContent(String dateParam) {
        StringRequest stringRequest = new StringRequest(Method.GET, WE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Logger.json(response);
                    JSONObject result = new JSONObject(response);
                    insertWeather(result);
                } catch (JSONException e) {
                    Logger.d(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                /* TODO Auto-generated method stub */

            }
        });

        RequestQueue requestQueue = RQManager.getInstance().getRequestQueue();
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    public static void insertWeather(JSONObject weatherResult) {
        SQLiteDatabase db = DatabaseHelper.getDatabase();
        Cursor cursor = null;
        try {
            String date = weatherResult.getString("date").replace("-", "");
            cursor =
                    db.query(TABLENAME, new String[]{"date"}, "date = ?", new String[]{date}, null,
                            null, null);
            if (cursor.getCount() == 0) {
                JSONObject result = weatherResult.getJSONArray("results").getJSONObject(0);
                String currentCity = result.getString("currentCity");
                String pm25 = result.getString("pm25");
                JSONArray index = result.getJSONArray("index");
                JSONArray weather_data = result.getJSONArray("weather_data");

                ContentValues contentValues = new ContentValues();
                contentValues.put("date", date);
                contentValues.put("currentCity", currentCity);
                contentValues.put("pm25", pm25);
                contentValues.put("info", index.toString());
                contentValues.put("weather_data", weather_data.getJSONObject(0).toString());

                db.insert(TABLENAME, null, contentValues);
            }
        } catch (Exception e) {
            Logger.d(e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

    }

    public static Weather getTodayWeather() {
        Time time = new Time();
        time.setToNow();
        int month = time.month + 1;
        String todayDate = String.valueOf(time.year)
                + String.valueOf(month < 10 ? "0" + month : month)
                + String.valueOf(time.monthDay < 10 ? "0" + time.monthDay : time.monthDay);
        return getWeatherByDate(todayDate);
    }

    public static Weather getWeatherByDate(String dateParam) {
        Weather weather;
        SQLiteDatabase db = DatabaseHelper.getDatabase();
        Cursor cursor =
                db.query(TABLENAME, new String[]{"date", "currentCity", "pm25", "info",
                                "weather_data"}, "date = ?", new String[]{dateParam}, null,
                        null, null);
        if (cursor.getCount() == 0) {
            Logger.d("query todayDate:" + dateParam + "  is  0");
            weather = null;
            fetchWeatherContent(dateParam);
        } else {
            cursor.moveToFirst();
            weather = new Weather();
            weather.date = cursor.getString(0);
            weather.currentCity = cursor.getString(1);
            weather.pm25 = cursor.getString(2);
            try {
                weather.index = new JSONArray(cursor.getString(3));
                weather.weather_data = new JSONObject(cursor.getString(4));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return weather;
    }
}
