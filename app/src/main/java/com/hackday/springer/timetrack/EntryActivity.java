package com.hackday.springer.timetrack;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.hackday.springer.timetrack.database.TimeTrackContract;
import com.hackday.springer.timetrack.database.TimeTrackReaderDbHelper;

import java.io.File;
import java.io.IOException;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final File contextPath = getApplicationContext().getFilesDir();
        final TimeFileManager fileWriter = new TimeFileManager(contextPath.getAbsolutePath());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button buttonStartWork = (Button) findViewById(R.id.btn_startWork);
        buttonStartWork.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                System.out.println("Button to start work pressed.");
                long startedWork = System.currentTimeMillis();
                String lineToWrite = fileWriter.generateLineToWrite(startedWork);

                String date = TimeCalculator.calcDateFromMilliseconds(startedWork);
                String time = TimeCalculator.calcTimeFromMilliseconds(startedWork);
                String dateTime = TimeCalculator.calcDateAndTimeFromMilliseconds(startedWork);
                long id = saveTimeTrackToDb(dateTime);
                System.out.println("Saved id to db: " + id);

                try {
                    fileWriter.writeFile(String.valueOf(lineToWrite.toString()));
                    startActivityShowTracks();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        final Button buttonStopWork = (Button) findViewById(R.id.btn_stopWork);
        buttonStopWork.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                System.out.println("Button to stop work pressed.");
            }
        });

        final Button buttonStartBreak = (Button) findViewById(R.id.btn_startBreak);
        buttonStartBreak.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                System.out.println("Button to start break pressed.");
            }
        });

        final Button buttonStopBreak = (Button) findViewById(R.id.btn_stopBreak);
        buttonStopBreak.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                System.out.println("Button to stop break pressed.");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startActivityShowTracks() {
        Intent intent = new Intent(this, ShowTimeTracksActivity.class);
        startActivity(intent);
    }

    private long saveTimeTrackToDb(String dateTime) {

        final TimeTrackReaderDbHelper dbHelper = new TimeTrackReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TimeTrackContract.TimeTrackEntry.COLUMN_START_WORK_TIME, dateTime);
        long newRowId = db.insert(TimeTrackContract.TimeTrackEntry.TABLE_NAME, null, values);

        return newRowId;
    }
}
