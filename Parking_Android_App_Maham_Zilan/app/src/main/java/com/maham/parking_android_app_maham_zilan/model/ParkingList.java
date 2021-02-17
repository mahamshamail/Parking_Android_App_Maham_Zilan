//Group 10
//101328732 Saiyeda Maham Shamail
//101226318 Zilan Ouyang
package com.maham.parking_android_app_maham_zilan.model;

import java.util.Calendar;
import java.util.Date;

public class ParkingList {
    String id = "";
    Date date;
    String address;
    String hours;

    String buildingCode;
    String carPlateNumber;
    String suitNumOfHosts;

    public ParkingList( Date date, String address, String hours, String buildingCode, String carPlateNumber, String suitNumOfHosts) {
        this.id = id;
        this.date = date;
        this.address = address;
        this.hours = hours;
        this.buildingCode = buildingCode;
        this.carPlateNumber = carPlateNumber;
        this.suitNumOfHosts = suitNumOfHosts;
    }

    public ParkingList() {
        this.id = "";
        this.date = new Date();
        this.address = "";
        this.hours = "";
        this.buildingCode = "";
        this.carPlateNumber = "";
        this.suitNumOfHosts = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getSuitNumOfHosts() {
        return suitNumOfHosts;
    }

    public void setSuitNumOfHosts(String suitNumOfHosts) {
        this.suitNumOfHosts = suitNumOfHosts;
    }

    @Override
    public String toString() {
        return "ParkingList{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", hours='" + hours + '\'' +
                ", buildingCode='" + buildingCode + '\'' +
                ", carPlateNumber='" + carPlateNumber + '\'' +
                ", suitNumOfHosts='" + suitNumOfHosts + '\'' +
                '}';
    }
}
