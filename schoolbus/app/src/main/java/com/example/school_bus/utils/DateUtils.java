package com.example.school_bus.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String FORMATO_HORA  = "HH:mm";
    private static final String FORMATO_COMPLETO = "dd/MM/yyyy HH:mm";

    private DateUtils() {}

    /** Devuelve la fecha actual como String.*/
    public static String getFechaActual() {
        return new SimpleDateFormat(FORMATO_FECHA, Locale.getDefault())
                .format(new Date());
    }

    /** Devuelve la hora actual como String. */
    public static String getHoraActual() {
        return new SimpleDateFormat(FORMATO_HORA, Locale.getDefault())
                .format(new Date());
    }

    /** Devuelve fecha y hora actual.*/
    public static String getFechaHoraActual() {
        return new SimpleDateFormat(FORMATO_COMPLETO, Locale.getDefault())
                .format(new Date());
    }

    /** Convierte un timestamp en milisegundos a fecha legible */
    public static String timestampAFecha(long millis) {
        return new SimpleDateFormat(FORMATO_COMPLETO, Locale.getDefault())
                .format(new Date(millis));
    }

    /** Comprueba si una hora tiene formato válido */
    public static boolean esHoraValida(String hora) {
        if (hora == null || hora.isEmpty()) return false;
        return hora.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
    }
}