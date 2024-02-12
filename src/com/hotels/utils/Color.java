package com.hotels.utils;

public final class Color {
    //    private Map<String, MenuItem> actions;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static String color(String color, String item) {
        return switch (color) {
            case "black" -> String.format("%s%s%s", ANSI_BLACK, item, ANSI_RESET);
            case "green" -> String.format("%s%s%s", ANSI_GREEN, item, ANSI_RESET);
            case "yellow" -> String.format("%s%s%s", ANSI_YELLOW, item, ANSI_RESET);
            case "blue" -> String.format("%s%s%s", ANSI_BLUE, item, ANSI_RESET);
            case "purple" -> String.format("%s%s%s", ANSI_PURPLE, item, ANSI_RESET);
            case "cyan" -> String.format("%s%s%s", ANSI_CYAN, item, ANSI_RESET);
            case "white" -> String.format("%s%s%s", ANSI_WHITE, item, ANSI_RESET);
            case "red" -> String.format("%s%s%s", ANSI_RED, item, ANSI_RESET);
            default -> item;
        };
    }

    public static String backgroundColor(String color, String item) {
        return switch (color) {
            case "black" -> String.format("%s%s%s", ANSI_BLACK_BACKGROUND, item, ANSI_RESET);
            case "red" -> String.format("%s%s%s", ANSI_RED_BACKGROUND, item, ANSI_RESET);
            case "green" -> String.format("%s%s%s", ANSI_GREEN_BACKGROUND, item, ANSI_RESET);
            case "yellow" -> String.format("%s%s%s", ANSI_YELLOW_BACKGROUND, item, ANSI_RESET);
            case "blue" -> String.format("%s%s%s", ANSI_BLUE_BACKGROUND, item, ANSI_RESET);
            case "purple" -> String.format("%s%s%s", ANSI_PURPLE_BACKGROUND, item, ANSI_RESET);
            case "cyan" -> String.format("%s%s%s", ANSI_CYAN_BACKGROUND, item, ANSI_RESET);
            case "white" -> String.format("%s%s%s", ANSI_WHITE_BACKGROUND, item, ANSI_RESET);
            default -> item;
        };
    }


}
