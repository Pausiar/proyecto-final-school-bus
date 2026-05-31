package com.example.school_bus.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 50;
    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 50;
    private static final int PHONE_LENGTH = 9;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern WEB_URL_PATTERN = Pattern.compile(
            "^(https?://)?([a-z0-9-]+\\.)+[a-z]{2,}(:[0-9]{1,5})?(/.*)?$",
            Pattern.CASE_INSENSITIVE
    );

    private ValidationUtils() {}

    /** comprueba si un campo de texto está vacío. */
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

    /** comprueba si el email tiene formato válido. */
    public static boolean isValidEmail(String email) {
        if (isFieldEmpty(email)) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /** comprueba si la contraseña tiene longitud válida. */
    public static boolean isValidPassword(String password) {
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

    /** comprueba si el nombre tiene longitud válida. */
    public static boolean isValidName(String name) {
        return isLengthInRange(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    }

    public static boolean isValidNameFormat(String name) {
        if (isFieldEmpty(name)) return false;
        return name.trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    /** comprueba si el teléfono tiene formato válido. */
    public static boolean isValidPhone(String phone) {
        if (isFieldEmpty(phone)) return false;
        return isExactLength(phone, PHONE_LENGTH) && phone.trim().matches("^[0-9]{9}$");
    }

    public static boolean isValidPhoneInternational(String phone) {
        if (isFieldEmpty(phone)) return false;
        String cleaned = phone.trim().replaceAll("[\\s\\-()+]", "");
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(date.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidUrl(String url) {
        if (isFieldEmpty(url)) return false;
        return WEB_URL_PATTERN.matcher(url.trim()).matches();
    }

    public static boolean isValidRouteNumber(String route) {
        if (isFieldEmpty(route)) return false;
        return route.trim().matches("^[Rr]-?[0-9]{1,3}$");
    }

    public static boolean isValidVehiclePlate(String plate) {
        if (isFieldEmpty(plate)) return false;
        return plate.trim().toUpperCase(Locale.ROOT).matches("^[0-9]{4}[A-Z]{3}$");
    }

    public static boolean isValidAccessCode(String code) {
        if (isFieldEmpty(code)) return false;
        return code.trim().matches("^[A-Za-z0-9]{6}$");
    }

    public static boolean isValidTime(String time) {
        if (isFieldEmpty(time)) return false;
        return time.trim().matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    public static int compareTime(String time1, String time2) {
        try {
            String[] parts1 = time1.split(":");
            String[] parts2 = time2.split(":");
            int totalMinutes1 = Integer.parseInt(parts1[0]) * 60 + Integer.parseInt(parts1[1]);
            int totalMinutes2 = Integer.parseInt(parts2[0]) * 60 + Integer.parseInt(parts2[1]);
            return Integer.compare(totalMinutes1, totalMinutes2);
        } catch (Exception e) {
            return 0;
        }
    }

    /** valida todos los campos del formulario de registro. */
    public static String validateRegistration(String name, String surname,
                                              String email, String password) {
        if (isFieldEmpty(name)) return "el nombre es obligatorio";
        if (!isValidName(name)) return "el nombre debe tener entre 2 y 50 caracteres";
        if (!isValidNameFormat(name)) return "el nombre solo puede contener letras";
        if (isFieldEmpty(surname)) return "el apellido es obligatorio";
        if (!isValidName(surname)) return "el apellido debe tener entre 2 y 50 caracteres";
        if (!isValidNameFormat(surname)) return "el apellido solo puede contener letras";
        if (!isValidEmail(email)) return "email no válido";
        if (!isValidPassword(password)) return "la contraseña debe tener entre 6 y 50 caracteres";
        return null;
    }

    /** valida los campos del formulario de login. */
    public static String validateLogin(String email, String password) {
        if (!isValidEmail(email)) return "email no válido";
        if (isFieldEmpty(password)) return "la contraseña es obligatoria";
        return null;
    }

    public static String validateProfile(String name, String surname, String phone) {
        if (isFieldEmpty(name)) return "el nombre es obligatorio";
        if (!isValidName(name)) return "el nombre debe tener entre 2 y 50 caracteres";
        if (!isValidNameFormat(name)) return "el nombre solo puede contener letras";
        if (isFieldEmpty(surname)) return "el apellido es obligatorio";
        if (!isValidName(surname)) return "el apellido debe tener entre 2 y 50 caracteres";
        if (!isValidNameFormat(surname)) return "el apellido solo puede contener letras";
        if (!isFieldEmpty(phone) && !isValidPhone(phone)) return "el teléfono debe tener 9 dígitos";
        return null;
    }

    public static String validateRoute(String name, String description, String startTime, String endTime) {
        if (isFieldEmpty(name)) return "el nombre de la ruta es obligatorio";
        if (isFieldEmpty(description)) return "la descripción es obligatoria";
        if (isFieldEmpty(startTime)) return "la hora de inicio es obligatoria";
        if (isFieldEmpty(endTime)) return "la hora de fin es obligatoria";
        if (!isValidTime(startTime)) return "la hora de inicio no es válida";
        if (!isValidTime(endTime)) return "la hora de fin no es válida";
        if (compareTime(startTime, endTime) >= 0) return "la hora de inicio debe ser anterior a la hora de fin";
        return null;
    }
}