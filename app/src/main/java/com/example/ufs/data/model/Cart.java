package com.example.ufs.data.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart INSTANCE = new Cart();

    private float total;
    private List<MenuItemModel> menuItems = new ArrayList<>();

    private Cart() {}

    public static Cart getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Cart();
        }
        return INSTANCE;
    }

    public float getTotal() { return total; }
    public void setTotal(float total) { this.total = total; }

    public List<MenuItemModel> getMenuItems() { return menuItems; }
    public void setMenuItems(List<MenuItemModel> menuItems) { this.menuItems = menuItems; }

    public void addToCart(MenuItemModel item) {
        // TODO: update total
        this.menuItems.add(item);
    }
    public void removeFromCart(MenuItemModel item) {
        this.menuItems.remove(item);
    }


    public void clearCart() {
        this.menuItems.clear();
        total = 0;
    }

}
