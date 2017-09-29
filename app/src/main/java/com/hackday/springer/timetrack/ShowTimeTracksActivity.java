package com.hackday.springer.timetrack;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hackday.springer.timetrack.database.TimeTrackContract;
import com.hackday.springer.timetrack.database.TimeTrackDTO;
import com.hackday.springer.timetrack.database.TimeTrackReaderDbHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by iso3097 on 29.09.17.
 */

public class ShowTimeTracksActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final File contextPath = getApplicationContext().getFilesDir();
        final TimeFileManager fileWriter = new TimeFileManager(contextPath.getAbsolutePath());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView textView = (TextView) findViewById(R.id.textView_showTracks);

        StringBuilder textBuilder = new StringBuilder("Found tracks:\n");
        for(TimeTrackDTO track : findTimeTracksInDb()) {
            textBuilder.append(track.getId() + ": ");
            textBuilder.append(track.getStartWorkingDateTime() + "\n");
        }

        textView.setText(textBuilder.toString());
        /*
        try {
            textView.setText(fileWriter.readFile());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private List<TimeTrackDTO> findTimeTracksInDb() {

        final TimeTrackReaderDbHelper dbHelper = new TimeTrackReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {TimeTrackContract.TimeTrackEntry._ID, TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME};
        //String sortOrder = TimeTrackContract.TimeTrackEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                TimeTrackContract.TimeTrackEntry.TABLE_NAME,                     // The table to query
                projection,                                                     // The columns to return
                //selection,                                // The columns for the WHERE clause
                null,
                //selectionArgs,                            // The values for the WHERE clause
                null,
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                //sortOrder                                 // The sort order
                null
        );

        List<TimeTrackDTO> tracks = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(TimeTrackContract.TimeTrackEntry._ID));
            String itemDate = cursor.getString(cursor.getColumnIndexOrThrow(TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME));
            TimeTrackDTO track = new TimeTrackDTO();
            track.setId(itemId);
            track.setStartWorkingDateTime(itemDate);
            tracks.add(track);
        }
        return tracks;
    }
}
