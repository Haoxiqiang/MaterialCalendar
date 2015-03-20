package info.hxq.materialcalendar.proxy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.hxq.materialcalendar.base.MainApplication;
import info.hxq.materialcalendar.db.DatabaseHelper;

import static com.android.volley.Request.Method;

/**
 * Created by haoxiqiang on 15/3/20.
 */
public final class TabooProxy {

    public static final String FETCHURL = "http://www.123cha.com/calendar/js/";

    public static final String TABLENAME = "taboo";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLENAME + "(" +
                    "\t \"date\" text(8,0) NOT NULL,\n" +
                    "\t \"nongli\" text,\n" +
                    "\t \"ganzhi\" text,\n" +
                    "\t \"yi\" text,\n" +
                    "\t \"ji\" text,\n" +
                    "\t \"jishenyiqu\" text,\n" +
                    "\t \"xiongshenyiji\" text,\n" +
                    "\t \"taishenzhanfang\" text,\n" +
                    "\t \"wuxing\" text,\n" +
                    "\t \"chong\" text,\n" +
                    "\t \"pengzubaiji\" text,\n" +
                    "PRIMARY KEY(\"date\")\n" +
                    ");";

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("DROP TABLE IF EXISTS" + TABLENAME + ";");
            db.execSQL(CREATE_TABLE);
        }
    }


    public static void fetchJSContent() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainApplication.getApplication());
        for (int i = 2009; i < 2100; i++) {
            final String datePrefix = String.valueOf(i);
            StringRequest stringRequest = new StringRequest(Method.GET, FETCHURL + datePrefix + ".js", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.substring(response.indexOf("=") + 1, response.length());
                    try {
                        JSONArray resultArray = new JSONArray(result);
                        for (int j = 0; j < resultArray.length(); j++) {
                            JSONObject tabooDay = resultArray.getJSONObject(j);
                            if (tabooDay != null) {
                                insertTaboo(tabooDay, datePrefix);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                /* TODO Auto-generated method stub */

                }
            });
            requestQueue.add(stringRequest);
        }
        requestQueue.start();
    }

    public static void insertTaboo(JSONObject tabooObj, String datePrefix) throws JSONException {
        /**
         * <pre>
         "0": "辛卯年、生肖属兔、庚子月、辛酉日",
         "1": "厨灶门外东南",
         "2": "石榴木 收执位",
         "3": "冲兔(乙卯)煞东",
         "4": "辛不合酱主人不尝 酉不宴客醉坐颠狂",
         "5": "母仓 要安 除神 明堂 鸣犬",
         "6": "祭祀 开光 理发 整手足甲 安床 作灶 扫舍 教牛马",
         "7": "五离 河魁 大时 大败 咸池 四耗 地曩",
         "8": "伐木 纳畜 破土 安葬 开生坟 嫁娶 开市 动土 交易 作梁",
         "nongli": "公元2012年01月01日 农历12月(小)08日 星期日 山羊座",
         "riqi": "d0101"
         </pre>
         <pre>
         document.getElementById("nongli").innerHTML=eval(json_name)[i]['nongli'];
         document.getElementById("ganzhi").innerHTML=eval(json_name)[i]['0'];
         document.getElementById("yi").innerHTML=eval(json_name)[i]['6'];
         document.getElementById("ji").innerHTML=eval(json_name)[i]['8'];
         document.getElementById("jsyq").innerHTML=eval(json_name)[i]['5'];
         document.getElementById("xsyj").innerHTML=eval(json_name)[i]['4'];
         document.getElementById("ts").innerHTML=eval(json_name)[i]['7'];
         document.getElementById("wx").innerHTML=eval(json_name)[i]['2'];
         document.getElementById("chong").innerHTML=eval(json_name)[i]['3'];
         document.getElementById("pzbj").innerHTML=eval(json_name)[i]['4'];
         </pre>
         */
        SQLiteDatabase db = DatabaseHelper.getDatabase();
        String riqi = tabooObj.getString("riqi");
        riqi = riqi.substring(1, riqi.length());
        String date = datePrefix + riqi;
        Cursor cursor =
                db.query(TABLENAME, new String[]{"date"}, "date = ?", new String[]{date}, null,
                        null, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("nongli", tabooObj.getString("nongli"));
        contentValues.put("ganzhi", tabooObj.optString("0"));
        contentValues.put("yi", tabooObj.optString("6"));
        contentValues.put("ji", tabooObj.optString("8"));
        contentValues.put("jishenyiqu", tabooObj.optString("5"));
        contentValues.put("xiongshenyiji", tabooObj.optString("4"));
        contentValues.put("taishenzhanfang", tabooObj.optString("7"));
        contentValues.put("wuxing", tabooObj.optString("2"));
        contentValues.put("chong", tabooObj.optString("3"));
        contentValues.put("pengzubaiji", tabooObj.optString("4"));
        if (cursor.getCount() == 0) {
            db.insert(TABLENAME, null, contentValues);
        } else {
            db.update(TABLENAME, contentValues, "date=?", new String[]{date});
        }
        cursor.close();
    }
}
