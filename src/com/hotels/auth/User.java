package com.hotels.auth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hotels.hotel.Booking;
import com.hotels.hotel.Hotel;

import java.util.*;

public class User {
    public final String username;
    private final String password;
    private Map<String, String> adminAccessLevel;
    private Map<String, ArrayDeque<Booking>> bookingHistory;

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