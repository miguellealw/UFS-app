package com.example.ufs.data.model;

public class AdModel {
    private String restaurantName;
    private String restaurantImage;

    public AdModel(String restaurantName, String restaurantImage) {
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getRestaurantImage() { return restaurantImage; }
    public void setRestaurantImage(String restaurantImage) { this.restaurantImage = restaurantImage; }

}
