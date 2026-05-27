package com.example.school_bus.utils;

import android.util.Patterns;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ValidationUtils {

    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 50;
    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 50;
    private static final int PHONE_LENGTH = 9;

    private ValidationUtils() {}

    public static boolean isFieldEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean isExactLength(String text, int length) {
        return !isFieldEmpty(text) && text.trim().length() == length;
    }

    public static boolean isLengthInRange(String text, int min, int max) {
        if (isFieldEmpty(text)) return false;
        int length = text.trim().length();
        return length >= min && length <= max;
    }

    public static boolean isValidEmail(String email) {
        if (isFieldEmpty(email)) return false;
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        if (isFieldEmpty(password)) return false;
        return isLengthInRange(password, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
    }

    public static boolean isStrongPassword(String password) {
        if (!isValidPassword(password)) return false;

        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>?/\\\\|`~].*");

        return hasUppercase && hasLowercase && hasNumber && hasSpecial;
    }

    public static boolean isValidName(String name) {
        if (isFieldEmpty(name)) return false;
        return isLengthInRange(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    }

    public static boolean isValidNameFormat(String name) {
        if (isFieldEmpty(name)) return false;
        return name.trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    public static boolean isValidPhone(String phone) {
        if (isFieldEmpty(phone)) return false;
        return isExactLength(phone, PHONE_LENGTH) && phone.trim().matches("^[0-9]{9}$");
    }

    public static boolean isValidPhoneInternational(String phone) {
        if (isFieldEmpty(phone)) return false;
        String cleaned = phone.trim().replaceAll("[\\s\\-()\\+]", "");
        return cleaned.matches("^[0-9]{7,15}$");
    }

    public static boolean isValidInteger(String text) {
        if (isFieldEmpty(text)) return false;
        try {
            Integer.parseInt(text.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDouble(String text) {
        if (isFieldEmpty(text)) return false;
        try {
            Double.parseDouble(text.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumberInRange(String number, int min, int max) {
        if (!isValidInteger(number)) return false;
        int value = Integer.parseInt(number.trim());
        return value >= min && value <= max;
    }

    public static boolean isValidDate(String date) {
        if (isFieldEmpty(date)) return false;
        return isValidDateFormat(date, "dd/MM/yyyy");
    }

    public static boolean isValidDateFormat(String date, String format) {
        if (isFieldEmpty(date)) return false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            sdf.setLenient(false);
            sdf.parse(date.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidUrl(String url) {
        if (isFieldEmpty(url)) return false;
        return Patterns.WEB_URL.matcher(url.trim()).matches();
    }

    public static boolean isValidRouteNumber(String route) {
        if (isFieldEmpty(route)) return false;
        return route.trim().matches("^[Rr]-?[0-9]{1,3}$");
    }

    public static boolean isValidVehiclePlate(String plate) {
        if (isFieldEmpty(plate)) return false;
        return plate.trim().toUpperCase().matches("^[0-9]{4}[A-Z]{3}$");
    }

    public static boolean isValidAccessCode(String code) {
        if (isFieldEmpty(code)) return false;
        return code.trim().matches("^[A-Za-z0-9]{6}$");
    }

    public static String validateRegistration(String name, String surname,
                                               String email, String password) {
        if (isFieldEmpty(name))         return "El nombre es obligatorio";
        if (!isValidName(name))         return "El nombre debe tener entre 2 y 50 caracteres";
        if (!isValidNameFormat(name))   return "El nombre solo puede contener letras";
        if (isFieldEmpty(surname))      return "Los apellidos son obligatorios";
        if (!isValidNameFormat(surname)) return "Los apellidos solo pueden contener letras";
        if (!isValidEmail(email))       return "El correo electrónico no es válido";
        if (!isValidPassword(password)) return "La contraseña debe tener entre 6 y 50 caracteres";
        return null;
    }

    public static String validateLogin(String email, String password) {
        if (!isValidEmail(email))   return "El correo electrónico no es válido";
        if (isFieldEmpty(password)) return "La contraseña es obligatoria";
        return null;
    }

    public static String validateProfile(String name, String surname, String phone) {
        if (isFieldEmpty(name)) return "El nombre es obligatorio";
        if (!isValidNameFormat(name)) return "El nombre solo puede contener letras";
        if (isFieldEmpty(surname)) return "Los apellidos son obligatorios";
        if (!isValidNameFormat(surname)) return "Los apellidos solo pueden contener letras";
        if (!isValidPhone(phone)) return "El teléfono debe tener 9 dígitos";
        return null;
    }

    public static String validateRoute(String routeNumber, String departure, String destination) {
        if (!isValidRouteNumber(routeNumber)) return "El número de ruta es inválido";
        if (isFieldEmpty(departure)) return "El punto de partida es obligatorio";
        if (isFieldEmpty(destination)) return "El punto de destino es obligatorio";
        if (departure.equalsIgnoreCase(destination)) return "La partida y destino no pueden ser iguales";
        return null;
    }
}