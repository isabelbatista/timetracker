package com.hackday.springer.timetrack.database;

/**
 * Created by iso3097 on 29.09.17.
 */

public class TimeTrackDTO {

    private long id;
    private String startWorkingDateTime;
    private String stopWorkingDateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartWorkingDateTime() {
        return startWorkingDateTime;
    }

    public void setStartWorkingDateTime(String startWorkingDateTime) {
        this.startWorkingDateTime = startWorkingDateTime;
    }

    public String getStopWorkingDateTime() {
        return stopWorkingDateTime;
    }

    public void setStopWorkingDateTime(String stopWorkingDateTime) {
        this.stopWorkingDateTime = stopWorkingDateTime;
    }
}
