package com.hotels.utils;

import java.util.Comparator;

public final class Validators {
    /* strings */
    public static boolean length(int min, int max, String value) {
        return Validators.minLength(min, value) && Validators.maxLength(max, value);
    }

    public static boolean minLength(int min, String value) {
        return value.length() >= min;
    }

    public static boolean maxLength(int max, String value) {
        return value.length() <= max;
    }

    /* numbers */
    public static <T extends Number & Comparable<T>> boolean moreThan(T min, T value) {
        return value.compareTo(min) >= 0;
    }

    public static <T extends Number & Comparable<T>> boolean lessThan(T max, T value) {
        return value.compareTo(max) <= 0;
    }

    public static <T extends Number & Comparable<T>> boolean between(T min, T max, T value) {
        return Validators.lessThan(max, value) && Validators.moreThan(min, value);
    }

    public static <T extends String> boolean verifyInteger(T value) {
        try {
            int item = Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
