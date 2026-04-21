package com.example.school_bus.models;

public class User {

    private String uid;
    private String name;
    private String surname;
    private String email;
    private String role;

    public User() {}

    public User(String uid, String name, String surname, String email, String role) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
