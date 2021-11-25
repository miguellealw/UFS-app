package com.example.ufs.data.model;

public class ReviewModel {
    private int rating;
    private String message;

    public ReviewModel(int rating, String message) {
        this.rating = rating;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ReviewModel{" +
                "rating=" + rating +
                ", message='" + message + '\'' +
                '}';
    }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

}
