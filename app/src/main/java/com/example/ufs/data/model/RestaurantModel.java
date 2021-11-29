package com.example.ufs.data.model;

public class RestaurantModel {
    private int id;
    private String name;
    private String location;
    private int userID;

    //private int User;
    public RestaurantModel(int id, String name, String location, int userID) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.userID = userID;
    }

    public RestaurantModel(String name, String location, int userID) {
        this.name = name;
        this.location = location;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "RestaurantModel{" +
                "location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", userID='" + userID + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public int getId() { return id; }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }
}
