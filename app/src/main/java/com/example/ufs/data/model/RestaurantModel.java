package com.example.ufs.data.model;

public class RestaurantModel {
    private String location;
    private String name;

    //private int User;

    public RestaurantModel(String location, String name) {
        this.location = location;
        this.name = name;
    }

    @Override
    public String toString() {
        return "RestaurantModel{" +
                "location='" + location + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

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

}
