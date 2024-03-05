package com.hotels.room;

public class StandardRoom extends Room {
    private String type = "Standard";
    private int number;
    private Double price;
    private Double cancellationFee;
    private String[] amenities;

    public StandardRoom(Double price, Double cancellationFee, int roomNumber, int capacity, String... amenities) {
        super.setCapacity(capacity);
        this.price = price;
        this.cancellationFee = this.setCancellationFee(price, cancellationFee);
        this.number = roomNumber;
        this.amenities = amenities;
    }

    @Override
    public String getSpecification() {
        return String.format("Capacity: %d; Type: %s; Price: %.2f; Cancellation fee: %.2f; Amenities: %s", super.getCapacity(), this.type, this.price, this.cancellationFee, String.join(", ", this.amenities));
    }

    @Override
    Double setCancellationFee(Double price, Double cancelationFee) {
        return price + price * cancelationFee / 100;
    }
}
