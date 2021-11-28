package com.example.ufs.data.model;

public class ReviewModel {
    private int rating;
    private String message;


    private int userId;
    private int restaurantId;

    public ReviewModel(int rating, String message, int userId, int restaurantId) {
        this.rating = rating;
        this.message = message;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "ReviewModel{" +
                "rating=" + rating +
                ", message='" + message + '\'' +
                ", userId='" + userId + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                '}';
    }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

}
