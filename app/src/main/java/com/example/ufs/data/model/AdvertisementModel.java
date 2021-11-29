package com.example.ufs.data.model;

public class AdvertisementModel {
    private int id;
    private String companyName;
    private String message;

    @Override
    public String toString() {
        return "AdvertisementModel{" +
                "companyName='" + companyName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public AdvertisementModel(int id, String companyName, String message) {
        this.id = id;
        this.companyName = companyName;
        this.message = message;
    }

    public AdvertisementModel(String companyName, String message) {
        this.companyName = companyName;
        this.message = message;
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getId() {
        return id;
    }
}
