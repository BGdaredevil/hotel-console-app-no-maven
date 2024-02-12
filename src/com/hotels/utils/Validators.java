package com.hotels.utils;

public final class Validators {

    public static boolean length(int min, int max, String value) {
        return value.length() >= min && value.length() <= max;
    }

    public static boolean minLength(int min, String value) {
        return value.length() >= min;
    }

    public static boolean maxLength(int max, String value) {
        return value.length() <= max;
    }
}
