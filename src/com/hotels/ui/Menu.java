package com.hotels.ui;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Menu {
    //    private Map<String, MenuItem> actions;
    private String[] mainMenuText = {
            "1. View Rooms",
            "2. Book a Room",
            "3. Cancel booking",
            "4. Login",
            "5. Register",
            "6. Logout",
            "7. Admin portal",
            "9. Exit"
    };

    public Menu mainMenu(Scanner sc, Menu parrentMenu) {
        System.out.println(String.join("\n", mainMenuText));
        int selection;

        do {
            selection = Integer.parseInt(sc.nextLine());

            switch (selection) {
                case 1:
                    return parrentMenu.subMenuOne(sc, parrentMenu);
                case 2:
                    return parrentMenu.subMenuTwo(sc, parrentMenu);
                case 9:
                    return parrentMenu;
                default:
                    System.out.println("Invalid selection");
            }

        } while (selection != 9);

        return parrentMenu;
    }

    private Menu subMenuOne(Scanner sc, Menu parrentMenu) {
        System.out.println("one");
        System.out.println(String.join("\n", "1. sub one"));
        int selection;

        do {
            selection = Integer.parseInt(sc.nextLine());

            switch (selection) {
                case 1:
                    return parrentMenu.subMenuOne(sc, parrentMenu);
                case 2:
                    return parrentMenu.subMenuTwo(sc, parrentMenu);
                case 9:
                    return parrentMenu.mainMenu(sc, parrentMenu);
                default:
                    System.out.println("Invalid selection");
            }

        } while (selection != 9);

        return parrentMenu;
    }

    private Menu subMenuTwo(Scanner sc, Menu parrentMenu) {
        System.out.println("two");
        System.out.println(String.join("\n", "1. sub two"));

        int selection;

        do {
            selection = Integer.parseInt(sc.nextLine());

            switch (selection) {
                case 1:
                    return parrentMenu.subMenuOne(sc, parrentMenu);
                case 2:
                    return parrentMenu.subMenuTwo(sc, parrentMenu);
                case 9:
                    return parrentMenu.mainMenu(sc, parrentMenu);
                default:
                    System.out.println("Invalid selection");
            }

        } while (selection != 9);

        return parrentMenu;
    }

//    public Menu(MenuItem... items) {
////        this.actions = new TreeMap<>(
////                IntStream.range(1, items.length).boxed().collect(Collectors.toMap(
////                        String::valueOf,
////                        (i) -> items[i - 1]
////                ))
////        );
//
//    }

//    public void printMenu() {
//        String menu = this.actions.values().stream().map(MenuItem::getMessage).collect(Collectors.joining("\n"));
//        System.out.println(menu);
//    }
//
//    public MenuItem getSelection(String index) {
//        System.out.printf("%d item selected", index);
//        return actions.get(index);
//    }

}
