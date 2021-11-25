package com.example.ufs.data.model;

public class OrderModel {
    private Float totalPrice;
    private Boolean isDelivered;
    private Boolean isPickup;
    private String address;

    // Can be get user of order
    // private int User;

    public OrderModel(Float totalPrice, Boolean isDelivered, Boolean isPickup, String address) {
        this.totalPrice = totalPrice;
        this.isDelivered = isDelivered;
        this.isPickup = isPickup;
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "totalPrice=" + totalPrice +
                ", isDelivered=" + isDelivered +
                ", isPickup=" + isPickup +
                ", address='" + address + '\'' +
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

    public void setAddress(String address) {
        this.address = address;
    }

}
