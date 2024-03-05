package com.hotels.room;

public abstract class Room {
    private String status;
    private int capacity;

    public String getStatus() {
        return this.status;
    }

    public String getSpecification(Room item) {
        return String.format("Capacity: %d", item.capacity);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public abstract String getSpecification();

    abstract Double setCancellationFee(Double price, Double cancelationFee);
}
