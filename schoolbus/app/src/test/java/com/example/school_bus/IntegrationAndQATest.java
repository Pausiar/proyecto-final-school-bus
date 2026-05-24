package com.example.school_bus;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.school_bus.utils.ValidationUtils;
import com.example.school_bus.utils.DateUtils;

public class IntegrationAndQATest {

    @Test
    public void testValidacionEmailOk() {
        assertTrue(ValidationUtils.isValidEmail("test@schoolbus.com"));
        assertTrue(ValidationUtils.isValidEmail("driver.one@gmail.com"));
    }

    @Test
    public void testValidacionEmailInvalido() {
        assertFalse(ValidationUtils.isValidEmail("testschoolbus.com"));
        assertFalse(ValidationUtils.isValidEmail("test@"));
        assertFalse(ValidationUtils.isValidEmail(""));
    }

    @Test
    public void testValidacionPassword() {
        assertTrue(ValidationUtils.isValidPassword("123456"));
        assertFalse(ValidationUtils.isValidPassword("12345"));
        assertFalse(ValidationUtils.isValidPassword(""));
    }

    @Test
    public void testFormatoFecha() {
        long timestamp = 1716249600000L; // 21 may 2024
        String fecha = DateUtils.formatDate(timestamp);
        assertNotNull(fecha);
        assertFalse(fecha.trim().isEmpty());
    }
}
