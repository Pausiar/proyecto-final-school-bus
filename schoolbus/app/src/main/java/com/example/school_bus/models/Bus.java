package com.example.school_bus.models;

public class Bus {

    private String id;
    private String busNumber;
    private String licensePlate;
    private String brand;
    private String model;
    private int year;
    private int passengerCapacity;
    private String color;
    private String status; // active, maintenance, out_of_service
    private String lastInspection;
    private String nextInspection;
    private String notes;

    public Bus() {}

    public Bus(String busNumber, String licensePlate, String brand,
               String model, int year, int passengerCapacity) {
        this.busNumber = busNumber;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.passengerCapacity = passengerCapacity;
        this.status = "active";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBusNumber() { return busNumber; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getPassengerCapacity() { return passengerCapacity; }
    public void setPassengerCapacity(int passengerCapacity) { this.passengerCapacity = passengerCapacity; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLastInspection() { return lastInspection; }
    public void setLastInspection(String lastInspection) { this.lastInspection = lastInspection; }

    public String getNextInspection() { return nextInspection; }
    public void setNextInspection(String nextInspection) { this.nextInspection = nextInspection; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "Bus{" +
                "id='" + id + '\'' +
                ", busNumber='" + busNumber + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", passengerCapacity=" + passengerCapacity +
                ", status='" + status + '\'' +
                '}';
    }
}