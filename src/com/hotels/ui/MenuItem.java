package com.hotels.ui;

public class MenuItem implements Comparable<MenuItem> {

    private final Menu subMenu;
    private String message;
    public void action(){}

    public MenuItem(String message, Menu subMenu) {
        this.message = message;
        this.subMenu = subMenu;
    }
    public String getMessage() {
        return this.message;
    }

    public static Menu getSubMenu(MenuItem instance) {
        return instance.subMenu;
    }

    @Override
    public int compareTo(MenuItem o) {
        return this.message.compareTo(o.getMessage());
    }
}
