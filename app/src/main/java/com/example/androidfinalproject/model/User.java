package com.example.androidfinalproject.model;

public class User {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String plate;

    public User() {
        this.fullName = "";
        this.email = "";
        this.password = "";
        this.phoneNumber = "";
        this.plate = "";
    }

    public User(String fullName, String email, String password, String phoneNumber, String plate) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.plate = plate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
