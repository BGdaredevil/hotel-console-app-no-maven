package com.hotels.auth;

import com.hotels.hotel.Booking;
import com.hotels.hotel.Hotel;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class User {
    public final String username;
    private final String password;

    private Map<String, String> adminAccessLevel;
    private final Map<String, ArrayDeque<Booking>> bookingHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.bookingHistory = new HashMap<>();
        this.adminAccessLevel = new HashMap<>();
    }

    public static boolean verifyPassword(User person, String givenPassword) {
        return person.password.equals(givenPassword);
    }
    public void addBookingHistory(Booking booking, Hotel hotel) {
        String hotelName = Hotel.getName(hotel);

        if (!this.bookingHistory.containsKey(hotelName)) {
            this.bookingHistory.put(hotelName, new ArrayDeque<>());
        }

        this.bookingHistory.get(hotelName).push(booking);
    }
}
