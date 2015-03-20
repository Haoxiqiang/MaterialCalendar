package info.hxq.materialcalendar.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import info.hxq.materialcalendar.ILog;
import info.hxq.materialcalendar.base.MainApplication;
import info.hxq.materialcalendar.proxy.TabooProxy;

/**
 * Created by haoxiqiang on 15/3/20.
 */
//public class DatabaseHelper extends SQLiteOpenHelper {
public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "calendar.db";
    private static final int DATABASE_VERSION = 2;
    private static SQLiteDatabase mSQLiteDatabase = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteDatabase getDatabase() {
        return mSQLiteDatabase;
    }

    public synchronized static void init() {
        if (mSQLiteDatabase != null) {
            return;
        }
        try {
            DatabaseHelper helper = new DatabaseHelper(MainApplication.getApplication());
            mSQLiteDatabase = helper.getWritableDatabase();
        } catch (Exception e) {

        }
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.beginTransaction();
//        try {
//            db.execSQL(TabooProxy.CREATE_TABLE);
//            ILog.e();
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.endTransaction();
//        }
//
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            ILog.e();
            TabooProxy.onUpgrade(db, oldVersion, newVersion);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public static void closeDatabase() {
        if (mSQLiteDatabase != null) {
            mSQLiteDatabase.close();
        }
    }
}
