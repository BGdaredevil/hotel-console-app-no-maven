package com.hotels.hotel;

import com.hotels.auth.AuthActions;
import com.hotels.auth.User;
import com.hotels.room.DeluxeRoom;
import com.hotels.room.Room;
import com.hotels.room.StandardRoom;
import com.hotels.utils.Color;
import com.hotels.utils.DbActions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class HotelService {
    private static final String csvSplitRegex = "\\s*,\\s*";
    private static final String amenitiesSplitRegex = "\\s+";

    private static final String hotelsDbAddress = "hotels.json";
    private static final int typesCount = 2;
    private static HotelService instance = null;
    private Map<String, Hotel> hotels = null;
    private Hotel hotelContext = null;

    private HotelService() {
        this.hotels = new HashMap<>();
        try {
            Map<String, Hotel> persistedData = DbActions.getInstance().readObjectMapFromFile(hotelsDbAddress, Hotel.class);

            this.hotels = persistedData;
        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println(Color.backgroundColor("red", e.getMessage()));
        }
    }

    // get one
    public Hotel getHotelByName(String name) {
        Hotel item = hotels.getOrDefault(name, null);

        if (item == null) {
            return item;
        }

        if (Hotel.isDeleted(item)) {
            return null;
        }

        return item;
    }

    // get all for logged in user
    public Set<Hotel> getUserHotels() {
        User currentUser = AuthActions.getInstance().getLoggedInUser();
        Set<String> userHotels = currentUser.getAdminHotels();

        return hotels
                .entrySet()
                .stream()
                .filter((stringHotelEntry -> userHotels.contains(stringHotelEntry.getKey())))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    public Hotel getHotelContext() {
        return this.hotelContext;
    }

    public void setHotelContext(String hotelName) {
        this.hotelContext = this.getHotelByName(hotelName);
    }

    public List<String> listHotels() {
        return this.hotels.values().stream().map(Hotel::getName).toList();
    }

    public Hotel create(String name, String countConfig, String capacityConfig, String priceConfig, String feesConfig, String amenitiesConfig) {
        Hotel hotel = new Hotel(name);
        int[] counts = HotelService.processCountsCapacities(countConfig);
        int[] capacities = HotelService.processCountsCapacities(capacityConfig);
        double[] prices = HotelService.processPricesFees(priceConfig);
        double[] fees = HotelService.processPricesFees(feesConfig);
        String[] amenities = HotelService.processAmenities(amenitiesConfig);

        List<Room> rooms = new ArrayList<>(Arrays.stream(counts).sum());

        int roomNumber = 1;
        int regularCount = counts[0];
        int deluxeCount = counts[1];

        for (int i = 0; i < regularCount; i++) {
            rooms.add(new StandardRoom(prices[0], fees[0], roomNumber++, capacities[0], amenities[0].split(amenitiesSplitRegex)));
        }
        for (int i = 0; i < deluxeCount; i++) {
            rooms.add(new DeluxeRoom(prices[1], fees[1], roomNumber++, capacities[1], amenities[1].split(amenitiesSplitRegex)));
        }

        hotel.setRooms(rooms);
        this.hotels.put(name, hotel);

        try {
            DbActions.getInstance().writeObjectToFile(this.hotels, hotelsDbAddress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return hotel;
    }

    // todo update hotel

    public void deleteHotel(String name) {
        Hotel target = this.getHotelByName(name);
        if (target == null) {
            return;
        }

        User currentUser = AuthActions.getInstance().getLoggedInUser();
        if (currentUser.isAdmin(name)) {
            currentUser.removeAdmin(name);
            if (this.hotelContext.equals(target)) {
                this.hotelContext = null;
            }
            target.setDeleted();
        }
    }

    public static int[] processCountsCapacities(String data) {
        return Arrays.stream(data.split(HotelService.csvSplitRegex)).mapToInt(Integer::parseInt).toArray();
    }

    public static double[] processPricesFees(String data) {
        return Arrays.stream(data.split(HotelService.csvSplitRegex)).mapToDouble(Double::parseDouble).toArray();
    }

    public static String[] processAmenities(String data) {
        return data.split(HotelService.csvSplitRegex);
    }

    public static int getTypesCount() {
        return HotelService.typesCount;
    }

    public static void save() {
        try {
            DbActions.getInstance().writeObjectToFile(HotelService.getInstance().hotels, hotelsDbAddress);
        } catch (IOException e) {
            System.out.println(Color.backgroundColor("red", e.getMessage()));
        }
    }

    public static HotelService getInstance() {
        if (instance == null) {
            instance = new HotelService();
        }

        return instance;
    }
}
