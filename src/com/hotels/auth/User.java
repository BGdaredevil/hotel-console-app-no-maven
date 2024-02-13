package com.hotels.auth;

import com.hotels.hotel.Booking;
import com.hotels.hotel.Hotel;

import java.util.*;

public class User {
    public final String username;
    private final String password;
    private Set<String> adminAccessLevel;
    private Map<String, ArrayDeque<Booking>> bookingHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.bookingHistory = new HashMap<>();
        this.adminAccessLevel = new HashSet<>();
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

    public Set<String> getAdminHotels() {
        return this.adminAccessLevel;
    }

    public void setAdmin(String hotelName) {
        this.adminAccessLevel.add(hotelName);
    }

    public void removeAdmin(String hotelName) {
        this.adminAccessLevel.remove(hotelName);
    }

    public boolean isAdmin(String hotelName) {
        return this.adminAccessLevel.contains(hotelName);
    }
}