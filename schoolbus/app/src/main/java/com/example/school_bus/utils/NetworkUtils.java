package com.example.school_bus.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

public class NetworkUtils {

    private NetworkUtils() {}

    /** devuelve true si hay conexión a internet activa. */
    public static boolean hasConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) return false;

        android.net.Network network = cm.getActiveNetwork();
        if (network == null) return false;
        NetworkCapabilities caps = cm.getNetworkCapabilities(network);
        return caps != null && (
                caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        );
    }

    /** devuelve true si la conexión es wifi. */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) return false;

        android.net.Network network = cm.getActiveNetwork();
        if (network == null) return false;
        NetworkCapabilities caps = cm.getNetworkCapabilities(network);
        return caps != null && caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
    }
}