package com.hackday.springer.timetrack;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by iso3097 on 29.09.17.
 */

public class TimeCalculator {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String calcDateFromMilliseconds(long timestampInMilliseconds) {
        return convertLocalDateTimeFrom(timestampInMilliseconds).format(dateFormatter);
    }

    public static String calcTimeFromMilliseconds(long timestampInMilliseconds) {
        return convertLocalDateTimeFrom(timestampInMilliseconds).format(timeFormatter);
    }

    private static LocalDateTime convertLocalDateTimeFrom(long timestampMilliseconds) {
        Instant instant = Instant.ofEpochMilli(timestampMilliseconds);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, zone);
        return dateTime;
    }
}
