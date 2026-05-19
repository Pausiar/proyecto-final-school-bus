package com.example.school_bus.utils;

import android.util.Patterns;

public class ValidationUtils {

    private static final int PASSWORD_MIN_LENGTH = 6;

    private ValidationUtils() {}

    /** Comprueba si un campo de texto está vacío */
    public static boolean esCampoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    /** Comprueba si el email tiene formato válido */
    public static boolean esEmailValido(String email) {
        if (esCampoVacio(email)) return false;
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

    /** Comprueba si la contraseña tiene al menos 6 caracteres */
    public static boolean esPasswordValida(String password) {
        if (esCampoVacio(password)) return false;
        return password.length() >= PASSWORD_MIN_LENGTH;
    }

    /** Comprueba si el nombre tiene al menos 2 caracteres */
    public static boolean esNombreValido(String nombre) {
        if (esCampoVacio(nombre)) return false;
        return nombre.trim().length() >= 2;
    }

    /** Comprueba si el teléfono tiene formato válido (9 dígitos) */
    public static boolean esTelefonoValido(String telefono) {
        if (esCampoVacio(telefono)) return false;
        return telefono.trim().matches("^[0-9]{9}$");
    }

    /** Valida todos los campos del formulario de registro */
    public static String validarRegistro(String nombre, String apellidos,
                                         String email, String password) {
        if (esCampoVacio(nombre))         return "El nombre es obligatorio";
        if (!esNombreValido(nombre))      return "El nombre debe tener al menos 2 caracteres";
        if (esCampoVacio(apellidos))      return "Los apellidos son obligatorios";
        if (!esEmailValido(email))        return "El email no es válido";
        if (!esPasswordValida(password))  return "La contraseña debe tener al menos 6 caracteres";
        return null; // null significa que todo está correcto
    }

    /** Valida los campos del formulario de login */
    public static String validarLogin(String email, String password) {
        if (!esEmailValido(email))       return "El email no es válido";
        if (esCampoVacio(password))      return "La contraseña es obligatoria";
        return null;
    }
}