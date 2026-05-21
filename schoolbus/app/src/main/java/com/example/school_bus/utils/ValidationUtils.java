package com.example.school_bus.utils;

import android.util.Patterns;

public class ValidationUtils {

    private static final int PASSWORD_MIN_LENGTH = 6;

    private ValidationUtils() {}

    /** Comprueba si un campo de texto está vacío. */
    public static boolean isFieldEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    /** Comprueba si el email tiene formato válido. */
    public static boolean isValidEmail(String email) {
        if (isFieldEmpty(email)) return false;
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

    /** Comprueba si la contraseña tiene al menos 6 caracteres. */
    public static boolean isValidPassword(String password) {
        if (isFieldEmpty(password)) return false;
        return password.length() >= PASSWORD_MIN_LENGTH;
    }

    /** Comprueba si el nombre tiene al menos 2 caracteres. */
    public static boolean isValidName(String name) {
        if (isFieldEmpty(name)) return false;
        return name.trim().length() >= 2;
    }

    /** Comprueba si el teléfono tiene formato válido (9 dígitos). */
    public static boolean isValidPhone(String phone) {
        if (isFieldEmpty(phone)) return false;
        return phone.trim().matches("^[0-9]{9}$");
    }

    /** Valida todos los campos del formulario de registro. */
    public static String validateRegistration(String name, String surname,
                                              String email, String password) {
        if (isFieldEmpty(name))         return "Name is required";
        if (!isValidName(name))         return "Name must be at least 2 characters";
        if (isFieldEmpty(surname))      return "Surname is required";
        if (!isValidEmail(email))       return "Invalid email address";
        if (!isValidPassword(password)) return "Password must be at least 6 characters";
        return null;
    }

    /** Valida los campos del formulario de login. */
    public static String validateLogin(String email, String password) {
        if (!isValidEmail(email))   return "Invalid email address";
        if (isFieldEmpty(password)) return "Password is required";
        return null;
    }
}