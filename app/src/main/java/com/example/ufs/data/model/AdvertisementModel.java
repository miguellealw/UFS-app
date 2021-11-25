package com.example.ufs.data.model;

public class AdvertisementModel {
    private String companyName;
    private String message;

    @Override
    public String toString() {
        return "AdvertisementModel{" +
                "companyName='" + companyName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public AdvertisementModel(String companyName, String message) {
        this.companyName = companyName;
        this.message = message;
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
