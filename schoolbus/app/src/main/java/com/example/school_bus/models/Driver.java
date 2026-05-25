package com.example.school_bus.models;

public class Driver {

    private String id;
    private String userId;
    private String licenseNumber;
    private String licenseExpiry;
    private String hireDate;
    private int yearsOfExperience;
    private String certifications;

    public Driver() {}

    public Driver(String userId, String licenseNumber,
                  String licenseExpiry, String hireDate) {
        this.userId = userId;
        this.licenseNumber = licenseNumber;
        this.licenseExpiry = licenseExpiry;
        this.hireDate = hireDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getLicenseExpiry() { return licenseExpiry; }
    public void setLicenseExpiry(String licenseExpiry) { this.licenseExpiry = licenseExpiry; }

    public String getHireDate() { return hireDate; }
    public void setHireDate(String hireDate) { this.hireDate = hireDate; }

    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public String getCertifications() { return certifications; }
    public void setCertifications(String certifications) { this.certifications = certifications; }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licenseExpiry='" + licenseExpiry + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                '}';
    }
}