package com.hackday.springer.timetrack.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iso3097 on 29.09.17.
 */

public class TimeTrackReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "timetrack.db";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TimeTrackContract.TimeTrackEntry.TABLE_NAME + " (" +
            TimeTrackContract.TimeTrackEntry._ID + " INTEGER PRIMARY KEY," +
            TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TimeTrackContract.TimeTrackEntry.TABLE_NAME;

    public TimeTrackReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
