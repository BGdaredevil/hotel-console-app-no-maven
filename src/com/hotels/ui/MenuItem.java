package com.hotels.ui;

public class MenuItem implements Comparable<MenuItem> {

    private String message;
    public void action(){}

    public MenuItem(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }

    @Override
    public int compareTo(MenuItem o) {
        return this.message.compareTo(o.getMessage());
    }
}
