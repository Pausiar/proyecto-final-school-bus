package com.example.school_bus.models;

public class User {

    private String id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String role;
    private String registrationDate;
    private boolean active;
    private String profilePhoto;
    private String linkedDriverUid;

    public User() {}

    public User(String id, String name, String surname, String email, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.active = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }

    public String getLinkedDriverUid() { return linkedDriverUid; }
    public void setLinkedDriverUid(String linkedDriverUid) { this.linkedDriverUid = linkedDriverUid; }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", active=" + active +
                '}';
    }
}