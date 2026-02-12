package com.example.school_bus.models;

public class Notification {

    private int id;
    private String title;
    private String message;
    private String date;

    // 🔹 Constructor vacío (IMPORTANTE para algunos usos)
    public Notification() {
    }

    // 🔹 Constructor completo
    public Notification(int id, String title, String message, String date) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.date = date;
    }

    // 🔹 Constructor sin id (para insertar en SQLite)
    public Notification(String title, String message, String date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }

    // 🔹 Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
