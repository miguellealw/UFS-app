package com.example.ufs.data.model;

public class MenuItemModel {
    private String name;
    private Float price;

    // private int Restaurant;

    public MenuItemModel(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "MenuItemModel{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Float getPrice() { return price; }
    public void setPrice(Float price) { this.price = price; }
}
