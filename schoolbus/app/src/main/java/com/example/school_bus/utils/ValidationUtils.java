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

    // ===== VALIDACIONES BÁSICAS =====

    /** Comprueba si un campo de texto está vacío. */
    public static boolean isFieldEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    /** Comprueba si un campo tiene exactamente la longitud especificada. */
    public static boolean isExactLength(String text, int length) {
        return !isFieldEmpty(text) && text.trim().length() == length;
    }

    /** Comprueba si un campo está dentro del rango de longitud especificado. */
    public static boolean isLengthInRange(String text, int min, int max) {
        if (isFieldEmpty(text)) return false;
        int length = text.trim().length();
        return length >= min && length <= max;
    }

    // ===== VALIDACIONES DE EMAIL Y CONTRASEÑA =====

    /** Comprueba si el email tiene formato válido. */
    public static boolean isValidEmail(String email) {
        if (isFieldEmpty(email)) return false;
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

    /** Comprueba si la contraseña tiene al menos 6 caracteres. */
    public static boolean isValidPassword(String password) {
        if (isFieldEmpty(password)) return false;
        return isLengthInRange(password, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
    }

    /** Valida contraseña fuerte (mayúscula, minúscula, número, caracteres especiales). */
    public static boolean isStrongPassword(String password) {
        if (!isValidPassword(password)) return false;

        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>?/\\\\|`~].*");

        return hasUppercase && hasLowercase && hasNumber && hasSpecial;
    }

    // ===== VALIDACIONES DE NOMBRES =====

    /** Comprueba si el nombre tiene al menos 2 caracteres. */
    public static boolean isValidName(String name) {
        if (isFieldEmpty(name)) return false;
        return isLengthInRange(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    }

    /** Comprueba si el nombre contiene solo letras y espacios. */
    public static boolean isValidNameFormat(String name) {
        if (isFieldEmpty(name)) return false;
        return name.trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    // ===== VALIDACIONES DE TELÉFONO =====

    /** Comprueba si el teléfono tiene formato válido (9 dígitos). */
    public static boolean isValidPhone(String phone) {
        if (isFieldEmpty(phone)) return false;
        return isExactLength(phone, PHONE_LENGTH) && phone.trim().matches("^[0-9]{9}$");
    }

    /** Valida teléfono con formatos internacionales. */
    public static boolean isValidPhoneInternational(String phone) {
        if (isFieldEmpty(phone)) return false;
        String cleaned = phone.trim().replaceAll("[\\s\\-()\\+]", "");
        return cleaned.matches("^[0-9]{7,15}$");
    }

    // ===== VALIDACIONES NUMÉRICAS =====

    /** Comprueba si el texto es un número entero válido. */
    public static boolean isValidInteger(String text) {
        if (isFieldEmpty(text)) return false;
        try {
            Integer.parseInt(text.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Comprueba si el texto es un número decimal válido. */
    public static boolean isValidDouble(String text) {
        if (isFieldEmpty(text)) return false;
        try {
            Double.parseDouble(text.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Comprueba si el número está dentro de un rango. */
    public static boolean isNumberInRange(String number, int min, int max) {
        if (!isValidInteger(number)) return false;
        int value = Integer.parseInt(number.trim());
        return value >= min && value <= max;
    }

    // ===== VALIDACIONES DE FECHA =====

    /** Comprueba si la fecha tiene formato válido (dd/MM/yyyy). */
    public static boolean isValidDate(String date) {
        if (isFieldEmpty(date)) return false;
        return isValidDateFormat(date, "dd/MM/yyyy");
    }

    /** Valida fecha con formato personalizado. */
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

    // ===== VALIDACIONES DE URL Y DOMINIO =====

    /** Comprueba si es una URL válida. */
    public static boolean isValidUrl(String url) {
        if (isFieldEmpty(url)) return false;
        return Patterns.WEB_URL.matcher(url.trim()).matches();
    }

    // ===== VALIDACIONES DE CONTEXTO (SCHOOL BUS) =====

    /** Valida número de ruta (ej: R-001, R-999). */
    public static boolean isValidRouteNumber(String route) {
        if (isFieldEmpty(route)) return false;
        return route.trim().matches("^[Rr]-?[0-9]{1,3}$");
    }

    /** Valida matrícula de vehículo (formato español: 1234ABC). */
    public static boolean isValidVehiclePlate(String plate) {
        if (isFieldEmpty(plate)) return false;
        return plate.trim().toUpperCase().matches("^[0-9]{4}[A-Z]{3}$");
    }

    /** Valida código de acceso (alfanumérico, 6 caracteres). */
    public static boolean isValidAccessCode(String code) {
        if (isFieldEmpty(code)) return false;
        return code.trim().matches("^[A-Za-z0-9]{6}$");
    }

    // ===== VALIDACIONES DE FORMULARIOS COMPLETOS =====

    /** Valida todos los campos del formulario de registro. */
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

    /** Valida los campos del formulario de login. */
    public static String validateLogin(String email, String password) {
        if (!isValidEmail(email))   return "El correo electrónico no es válido";
        if (isFieldEmpty(password)) return "La contraseña es obligatoria";
        return null;
    }

    /** Valida formulario de perfil con teléfono. */
    public static String validateProfile(String name, String surname, String phone) {
        if (isFieldEmpty(name)) return "El nombre es obligatorio";
        if (!isValidNameFormat(name)) return "El nombre solo puede contener letras";
        if (isFieldEmpty(surname)) return "Los apellidos son obligatorios";
        if (!isValidNameFormat(surname)) return "Los apellidos solo pueden contener letras";
        if (!isValidPhone(phone)) return "El teléfono debe tener 9 dígitos";
        return null;
    }

    /** Valida formulario de creación de ruta. */
    public static String validateRoute(String routeNumber, String departure, String destination) {
        if (!isValidRouteNumber(routeNumber)) return "El número de ruta es inválido";
        if (isFieldEmpty(departure)) return "El punto de partida es obligatorio";
        if (isFieldEmpty(destination)) return "El punto de destino es obligatorio";
        if (departure.equalsIgnoreCase(destination)) return "La partida y destino no pueden ser iguales";
        return null;
    }
}