package com.example.jennifer.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jennifer on 2017-12-06.
 */

class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Alarm.db";
    public static int VERSION_NUM = 1;
    static final String name = "ALARM_TABLE";
    static final String KEY_ID = "_id";
    static final String KEY_DAY = "day";
    static final String KEY_TIME = "time";
    static final String KEY_TEMP = "temperature";
    static final String KEY_TYPE = "type";
    SQLiteDatabase db;
    private static final String CREATE_TABLE_MSG = "create table " + name + "(" + KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
            KEY_DAY + " text, " + KEY_TIME + " text, " + KEY_TEMP + " text, " + KEY_TYPE + " text);";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MSG);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + name);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }
}
