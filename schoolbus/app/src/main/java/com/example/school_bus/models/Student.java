package com.example.school_bus.models;

public class Student {

    private String id;
    private String userId;
    private String name;
    private String grade;
    private String section;
    private String pickupAddress;
    private String dropoffAddress;
    private String pickupSchedule;
    private String specialNotes;

    public Student() {}

    public Student(String userId, String name, String grade,
                   String section, String pickupAddress, String dropoffAddress) {
        this.userId = userId;
        this.name = name;
        this.grade = grade;
        this.section = section;
        this.pickupAddress = pickupAddress;
        this.dropoffAddress = dropoffAddress;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }

    public String getDropoffAddress() { return dropoffAddress; }
    public void setDropoffAddress(String dropoffAddress) { this.dropoffAddress = dropoffAddress; }

    public String getPickupSchedule() { return pickupSchedule; }
    public void setPickupSchedule(String pickupSchedule) { this.pickupSchedule = pickupSchedule; }

    public String getSpecialNotes() { return specialNotes; }
    public void setSpecialNotes(String specialNotes) { this.specialNotes = specialNotes; }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", section='" + section + '\'' +
                ", pickupAddress='" + pickupAddress + '\'' +
                ", dropoffAddress='" + dropoffAddress + '\'' +
                '}';
    }
}