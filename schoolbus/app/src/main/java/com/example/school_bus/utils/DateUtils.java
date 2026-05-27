package com.example.school_bus.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String FULL_FORMAT = "dd/MM/yyyy HH:mm";

    private DateUtils() {}

    public static String getCurrentDate() {
        return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
                .format(new Date());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
                .format(new Date());
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat(FULL_FORMAT, Locale.getDefault())
                .format(new Date());
    }

    public static String timestampToDate(long millis) {
        return new SimpleDateFormat(FULL_FORMAT, Locale.getDefault())
                .format(new Date(millis));
    }

    public static boolean isValidTime(String time) {
        if (time == null || time.isEmpty()) return false;
        return time.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
    }
}