package com.example.ufs.data.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart INSTANCE = new Cart();

    private float total = 0;
    private List<MenuItemModel> menuItems = new ArrayList<>();
    private boolean isPickup = false;
    private boolean isCreditCard = false;


    private String address;
    private int restaurantID;

    private Cart() {}

    public static Cart getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Cart();
        }
        return INSTANCE;
    }

    public float getTotal() { return total; }
    public void setTotal(float total) { this.total = total; }

    public boolean getIsCreditCard() { return isCreditCard; }
    public void setIsCreditCard(boolean isCreditCard) { this.isCreditCard = isCreditCard; }

    public boolean getIsPickup() { return isPickup; }
    public void setIsPickup(boolean isPickup) {
        // If delivery add 5 dollar fee to total
        this.isPickup = isPickup;
    }

    public List<MenuItemModel> getMenuItems() { return menuItems; }
    public void setMenuItems(List<MenuItemModel> menuItems) { this.menuItems = menuItems; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getRestaurantID() { return restaurantID; }
    public void setRestaurantID(int restaurantID) { this.restaurantID = restaurantID; }

    public void addToCart(MenuItemModel item) {
        // update total
        total += item.getPrice();
        this.menuItems.add(item);
    }

    public void removeFromCart(MenuItemModel item) {
        total -= item.getPrice();
        this.menuItems.remove(item);
    }

    public void clearCart() {
        this.menuItems.clear();
        total = 0;
        address = "";
        restaurantID = -1;
    }

}
