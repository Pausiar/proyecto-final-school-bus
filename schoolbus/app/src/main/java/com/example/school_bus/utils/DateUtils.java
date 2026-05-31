package com.example.school_bus.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String FULL_FORMAT = "dd/MM/yyyy HH:mm";

    private DateUtils() {}

    /** devuelve la fecha actual como string. */
    public static String getCurrentDate() {
        return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());
    }

    /** devuelve la hora actual como string. */
    public static String getCurrentTime() {
        return new SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(new Date());
    }

    /** devuelve fecha y hora actual. */
    public static String getCurrentDateTime() {
        return new SimpleDateFormat(FULL_FORMAT, Locale.getDefault()).format(new Date());
    }

    /** convierte un timestamp en milisegundos a fecha legible. */
    public static String timestampToDate(long millis) {
        return new SimpleDateFormat(FULL_FORMAT, Locale.getDefault()).format(new Date(millis));
    }

    /** formatea un timestamp en milisegundos como fecha legible. */
    public static String formatDate(long millis) {
        return timestampToDate(millis);
    }

    /** comprueba si una hora tiene formato válido. */
    public static boolean isValidTime(String time) {
        if (time == null || time.isEmpty()) return false;
        return time.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
    }
}