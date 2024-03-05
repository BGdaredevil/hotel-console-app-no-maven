package com.hotels.hotel;

import com.hotels.auth.User;
import com.hotels.room.Room;

import java.util.*;

public class Hotel {
    private String name;
    private boolean deleted = false;
    private List<Room> rooms;
    private Map<String, ArrayDeque<Booking>> bookings;

    public Hotel(String name) {
        this.name = name;
        this.bookings = new HashMap<>();
        this.rooms = new ArrayList<>();
    }

    public static String getName(Hotel item) {
        return item.name;
    }

    public void addBooking(User user, Booking booking) {
        String userName = user.username;

        if (!this.bookings.containsKey(userName)) {
            this.bookings.put(userName, new ArrayDeque<>());
        }

        this.bookings.get(userName).push(booking);
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void setDeleted() {
        this.deleted = true;
    }

    public static boolean isDeleted(Hotel item) {
        return item.deleted;
    }

    public Map<String, String> viewRooms() {
        String[] types = this.rooms.stream().map(r -> r.getClass().getName()).distinct().toArray(String[]::new);
        Map<String, String> result = new HashMap<>(types.length);

        for (String type : types) {
            Optional<String> item = this.rooms.stream().filter(e -> e.getClass().getName().equals(type)).map(e -> String.format("%s", e.getSpecification())).findFirst();

            item.ifPresent(s -> result.put(type, s));
        }

        return result;
    }

    // todo:
    // edit rooms
    // income
    // booking list
    // search by room number
    // search by username
    // search by dates
    // getFreeRooms
}
