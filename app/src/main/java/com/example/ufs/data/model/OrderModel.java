package com.example.ufs.data.model;

import java.util.Date;

public class OrderModel {
    private int id;
    private Float totalPrice;
    private Boolean isDelivered;
    private Boolean isPickup;
    private String address;
    //private int paymentOption;
    private boolean isCreditCard;
    private int restaurantID;
    private int userID;

    private String timestamp;

    private String orderName;
    private String orderRestaurantName;

    // Can be get user of order
    // private int User;

    // This is used the fetching a order from the DB
    public OrderModel(
            int id,
            float totalPrice,
            boolean isDelivered,
            boolean isPickup,
            String address,
            boolean isCreditCard,
            int restaurantID,
            int userID
    ) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.isDelivered = isDelivered;
        this.isPickup = isPickup;
        this.address = address;
        this.isCreditCard = isCreditCard;
        this.restaurantID = restaurantID;
        this.userID = userID;
    }

    // Used when creating an order
    public OrderModel(
            float totalPrice,
            boolean isDelivered,
            boolean isPickup,
            String address,
            boolean isCreditCard,
            int restaurantID,
            int userID
    ) {
        this.totalPrice = totalPrice;
        this.isDelivered = isDelivered;
        this.isPickup = isPickup;
        this.address = address;
        this.isCreditCard = isCreditCard;
        this.restaurantID = restaurantID;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "totalPrice=" + totalPrice +
                ", isDelivered=" + isDelivered +
                ", isPickup=" + isPickup +
                ", address='" + address +
                ", isCreditCard='" + isCreditCard +
                ", restaurantID='" + restaurantID +
                ", userID='" + userID + '\'' +
                '}';
    }

    public Float getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getIsDelivered() {
        return isDelivered;
    }
    public void setIsDelivered(Boolean isDelivered) { this.isDelivered = isDelivered; }

    public Boolean getIsPickup() {
        return isPickup;
    }
    public void setIsPickup(Boolean isPickup) { this.isPickup = isPickup; }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) { this.address = address; }

    public boolean getIsCreditCard() { return isCreditCard; }
    public void setPaymentOption(boolean isCreditCard) { this.isCreditCard = isCreditCard; }

    public int getRestaurantID() { return restaurantID; }
    public void setRestaurantID(int restaurantID) { this.restaurantID = restaurantID; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public int getId() {
        return id;
    }
}
