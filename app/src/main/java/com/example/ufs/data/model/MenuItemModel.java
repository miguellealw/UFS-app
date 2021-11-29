package com.example.ufs.data.model;

public class MenuItemModel {
    private int id;
    private String name;
    private float price;
    private int restaurantId;

    // private int Restaurant;

    public MenuItemModel(int id, String name, float price, int restaurantId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public MenuItemModel(String name, float price, int restaurantId) {
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "MenuItemModel{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", restaurantId=" + restaurantId +
                '}';
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public int getId() {
        return id;
    }
}
