package com.maham.parking_android_app_maham_zilan.model;

public class User {
    String email;
    String name;
    String password;
    String contactNumber;
    String carPlateNumber;
    String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User() {

        this.email = "";
        this.name = "";
        this.password = "";
        this.contactNumber = "";
        this.carPlateNumber = "";
    }
    public User(String email, String name, String password, String contactNumber, String carPlateNumber) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.contactNumber = contactNumber;
        this.carPlateNumber = carPlateNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }
}

