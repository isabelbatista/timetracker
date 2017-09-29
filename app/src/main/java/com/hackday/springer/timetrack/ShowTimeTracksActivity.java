package com.hackday.springer.timetrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

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
        try {
            textView.setText(fileWriter.readFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
