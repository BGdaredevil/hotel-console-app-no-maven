package com.hotels.room;

public abstract class Room {
    private String status;
    private int capacity;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    abstract Double setCancellationFee(Double price, Double cancelationFee);
}
