package com.hotels.hotel;

import com.hotels.auth.User;
import com.hotels.room.Room;

import java.util.*;

public class Hotel {
    private String name;
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

    // todo:
    // edit rooms
    // income
    // booking list
    // search by room number
    // search by username
    // search by dates
    // getFreeRooms
}
