package info.hxq.materialcalendar.proxy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.hxq.materialcalendar.base.MessageToast;
import info.hxq.materialcalendar.db.DatabaseHelper;
import info.hxq.materialcalendar.entity.Memo;

/**
 * Created by haoxiqiang on 15/3/20.
 */
public final class MemoProxy {

    public static final String TABLENAME = "memo";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLENAME + "(" +
                    "date text(8,0) NOT NULL," +
                    "type text," +
                    "title text," +
                    "timestamp text," +
                    "alarm text," +
                    "remarks text," +
                    "PRIMARY KEY(date)" +
                    ");";

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS" + TABLENAME + ";");
            db.execSQL(CREATE_TABLE);
        }
    }


    public static boolean insertMemo(Memo memo) throws JSONException {
        if (memo == null || memo.timestamp == null) {
            MessageToast.show("日程记录失败", MessageToast.Style.ALERT);
            return false;
        }

        SQLiteDatabase db = DatabaseHelper.getDatabase();
        Cursor cursor =
                db.query(TABLENAME, new String[]{"timestamp"}, "timestamp = ?", new String[]{memo.timestamp}, null,
                        null, null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("date", memo.date);
        contentValues.put("type", memo.type);
        contentValues.put("title", memo.title);
        contentValues.put("timestamp", memo.timestamp);
        contentValues.put("alarm", memo.alarm.toString());
        contentValues.put("remarks", memo.remarks);

        if (cursor.getCount() == 0) {
            db.insert(TABLENAME, null, contentValues);
        } else {
            db.update(TABLENAME, contentValues, "timestamp=?", new String[]{memo.timestamp});
        }
        cursor.close();
        MessageToast.show("日程成功记录", MessageToast.Style.INFO);
        return true;
    }

    public static ArrayList<Memo> getTodayMemo() {
        Time time = new Time();
        time.setToNow();
        int month = time.month + 1;
        String todayDate = String.valueOf(time.year) + String.valueOf(month < 10 ? "0" + month : month) + String.valueOf(time.monthDay);
        return getMemoByDate(todayDate);
    }

    public static ArrayList<Memo> getMemoByDate(String dateParam) {
        ArrayList<Memo> memos = new ArrayList<>();
        SQLiteDatabase db = DatabaseHelper.getDatabase();
        Cursor cursor =
                db.query(TABLENAME, new String[]{"date", "type", "title", "timestamp",
                                "alarm", "remarks"}, "date = ?", new String[]{dateParam}, null,
                        null, null);
        if (cursor.getCount() == 0) {
            Logger.e("query todayDate:" + dateParam + "  is  0");
            memos.clear();
        } else {
            try {
                while (cursor.moveToNext()) {
                    Memo memo = new Memo();
                    memo.date = cursor.getString(0);
                    memo.type = cursor.getString(1);
                    memo.title = cursor.getString(2);
                    memo.timestamp = cursor.getString(3);
                    memo.alarm = new JSONObject(cursor.getString(4));
                    memo.remarks = cursor.getString(5);
                    memos.add(memo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return memos;
    }
}
