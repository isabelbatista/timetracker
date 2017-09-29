package com.hackday.springer.timetrack.database;

import android.content.Context;
import android.provider.BaseColumns;

/**
 * Created by iso3097 on 29.09.17.
 */

public final class TimeTrackContract {

    private TimeTrackContract() {
    }

    public static class TimeTrackEntry implements BaseColumns {
        public static final String TABLE_NAME = "timetrack";
        public static final String COLUMN_START_WORK_TIME = "start_work_time";
    }
}
