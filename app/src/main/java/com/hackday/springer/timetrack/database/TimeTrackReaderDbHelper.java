package com.hackday.springer.timetrack.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iso3097 on 29.09.17.
 */

public class TimeTrackReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "timetrack.db";

    private static final String[] PROJECTION = {TimeTrackContract.TimeTrackEntry._ID,
                                                TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME,
                                                TimeTrackContract.TimeTrackEntry.COLUMN_STOP_WORK_TIME};

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TimeTrackContract.TimeTrackEntry.TABLE_NAME + " (" +
            TimeTrackContract.TimeTrackEntry._ID + " INTEGER PRIMARY KEY," +
            TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME + " TEXT" +
            TimeTrackContract.TimeTrackEntry.COLUMN_STOP_WORK_TIME + "TEXT)";

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

    public long saveStartWorkDateTime(String dateTime) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME, dateTime);
        long newRowId = database.insert(TimeTrackContract.TimeTrackEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public long saveStopWorkDateTime(String stopWorkTime) {
        // get the last track where work has been started but not stopped
        final Cursor cursor = findLastOpenTrack();

        List<TimeTrackDTO> tracks = new ArrayList<>();
        while(cursor.moveToNext()) {
            tracks.add(convertCursorToDto(cursor));
        }

        // add the time to the column
        TimeTrackDTO trackToUpdate = tracks.get(0);
        trackToUpdate.setStopWorkingDateTime(stopWorkTime);
        updateTrack(trackToUpdate.getId(), TimeTrackContract.TimeTrackEntry.COLUMN_STOP_WORK_TIME, stopWorkTime);

        // return the id of the updated row
        return trackToUpdate.getId();
    }

    private TimeTrackDTO convertCursorToDto(Cursor cursor) {
        long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(TimeTrackContract.TimeTrackEntry._ID));
        String itemStartDate = cursor.getString(cursor.getColumnIndexOrThrow(TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME));
        String itemStopDate = cursor.getString(cursor.getColumnIndexOrThrow(TimeTrackContract.TimeTrackEntry.COLUMN_STOP_WORK_TIME));

        TimeTrackDTO track = new TimeTrackDTO();
        track.setId(itemId);
        track.setStartWorkingDateTime(itemStartDate);
        track.setStopWorkingDateTime(itemStopDate);
        return track;
    }

    private Cursor findAllTracks() {
        String sortOrder = TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME + " DESC";

        Cursor cursor = getReadableDatabase().query(
                TimeTrackContract.TimeTrackEntry.TABLE_NAME,                     // The table to query
                PROJECTION,                                                     // The columns to return
                //selection,                                // The columns for the WHERE clause
                null,
                //selectionArgs,                            // The values for the WHERE clause
                null,
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return cursor;
    }

    private Cursor findLastOpenTrack() {

        String sortOrder = TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME + " DESC";
        String selection = TimeTrackContract.TimeTrackEntry.COLUMN_STOP_WORK_TIME + " = ?";
        String[] selectionArgs = { null };

        Cursor cursor = getReadableDatabase().query(
                TimeTrackContract.TimeTrackEntry.TABLE_NAME,                     // The table to query
                PROJECTION,                                                     // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return cursor;
    }

    // TODO: not yet needed, but there to use (test before!)
    /*
    private Cursor findTrackWithId(long id) {
        String sortOrder = TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME + " DESC";
        String selection = TimeTrackContract.TimeTrackEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = getReadableDatabase().query(
                TimeTrackContract.TimeTrackEntry.TABLE_NAME,                     // The table to query
                PROJECTION,                                                     // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return cursor;
    }*/

    private int updateTrack(long id, String fieldname, String value) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(fieldname, value);

        // Which row to update
        String selection = TimeTrackContract.TimeTrackEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = getWritableDatabase().update(
                TimeTrackContract.TimeTrackEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }
}
