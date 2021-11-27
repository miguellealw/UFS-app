package com.example.ufs.data.model;

public class OrderModel {
    private Float totalPrice;
    private Boolean isDelivered;
    private Boolean isPickup;
    private String address;
    private int paymentOption;
    private int restaurantID;
    private int userID;

    // Can be get user of order
    // private int User;

    public OrderModel(
        Float totalPrice,
        Boolean isDelivered,
        Boolean isPickup,
        String address,
        int paymentOption,
        int restaurantID,
        int userID
    ) {
        this.totalPrice = totalPrice;
        this.isDelivered = isDelivered;
        this.isPickup = isPickup;
        this.address = address;
        this.paymentOption = paymentOption;
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
                ", paymentOption='" + paymentOption +
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

    public Boolean getDelivered() {
        return isDelivered;
    }
    public void setDelivered(Boolean delivered) {
        isDelivered = delivered;
    }

    public Boolean getPickup() {
        return isPickup;
    }
    public void setPickup(Boolean pickup) {
        isPickup = pickup;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) { this.address = address; }

    public int getPaymentOption() { return paymentOption; }
    public void setPaymentOption(int paymentOption) { this.paymentOption = paymentOption; }

    public int getRestaurantID() { return restaurantID; }
    public void setRestaurantID(int restaurantID) { this.restaurantID = restaurantID; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }
}
