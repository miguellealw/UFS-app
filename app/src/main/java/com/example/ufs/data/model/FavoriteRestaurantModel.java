package com.example.ufs.data.model;

public class FavoriteRestaurantModel {

    private int userId;
    private int restaurantId;

    String restaurantName;
    String restaurantLocation;

    // Put
    public FavoriteRestaurantModel(int userId, int restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    // Get
    public FavoriteRestaurantModel(int id, int userId, int restaurantId, String restaurantName, String restaurantLocation) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
    }

    @Override
    public String toString() {
        return "FavoriteRestaurantModel{" +
                "userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantLocation='" + restaurantLocation + '\'' +
                '}';
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getRestaurantLocation() { return restaurantLocation; }
    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

}
