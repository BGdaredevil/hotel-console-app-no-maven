package com.hotels.ui;

import com.hotels.auth.AuthActions;
import com.hotels.auth.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Menu {
    //    private Map<String, MenuItem> actions;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private String[] loginMenu = {
            "1. Username",
            "2. Password",
            "3. Submit",
            "9. Exit"
    };
    private String[] registerMenu = {
            "1. Username",
            "2. Password",
            "3. Repeat password",
            "4. Submit",
            "9. Exit"
    };

    private String[] mainMenuGuest = {
            "1. View Rooms",
            "2. Book a Room",
            "3. Cancel booking",
            "4. Login",
            "5. Register",
            "9. Exit"
    };
    private String[] mainMenuUser = {
            "1. View Rooms",
            "2. Book a Room",
            "3. Cancel booking",
            "4. Personal profile",
            "5. Admin portal",
            "6. Logout",
            "9. Exit"
    };

    public Menu mainMenu(Scanner sc, Menu parrentMenu) {
        User person = AuthActions.getInstance().getLoggedInUser();
        boolean isGuest = person == null;

        System.out.println("====== MAIN MENU ======");
        System.out.println(String.join("\n", isGuest ? mainMenuGuest : mainMenuUser));
        System.out.println("Please select an item: ");
        int selection;

        do {
            selection = Integer.parseInt(sc.nextLine());

            switch (selection) {
//              "1. View Rooms"
                case 1:
                    return parrentMenu.subMenuOne(sc, parrentMenu);

//              "2. Book a Room"
                case 2:
                    return parrentMenu.subMenuOne(sc, parrentMenu);

//                "3. Cancel booking"
                case 3:
                    return parrentMenu.subMenuOne(sc, parrentMenu);
                case 4:
                    return isGuest
//                "4. Login"
                            ? parrentMenu.login(sc, parrentMenu, "", "")
//                "4. Personal profile"
                            : parrentMenu.subMenuTwo(sc, parrentMenu);

                case 5:
                    return isGuest
//                "5. Register"
                            ? parrentMenu.register(sc, parrentMenu, "", "", "")
//                "5. Admin portal"
                            : parrentMenu.subMenuTwo(sc, parrentMenu);

//                "6. Logout"
                case 6: {
                    if (isGuest) {
                        System.out.println("Invalid selection");
                        break;
                    }

                    AuthActions.getInstance().logout();

                    return parrentMenu.mainMenu(sc, parrentMenu);
                }
                case 9:
                    return null;
                default:
                    System.out.println("Invalid selection");
            }

        } while (selection != 9);

        return parrentMenu;
    }

    private Menu login(Scanner sc, Menu parrentMenu, String username, String password) {
        System.out.println("====== LOGIN ======");

        System.out.println(String.join("\n", loginMenu));
        int selection;

        do {
            selection = Integer.parseInt(sc.nextLine());

            switch (selection) {
                case 1: {
                    System.out.print("Input: ");
                    String usernameInput = sc.nextLine();

                    return parrentMenu.login(sc, parrentMenu, usernameInput, password);
                }
                case 2: {
                    System.out.print("Input: ");
                    String passwordInput = sc.nextLine();

                    return parrentMenu.login(sc, parrentMenu, username, passwordInput);
                }
                case 3: {
                    if (username.isEmpty()) {
                        System.out.println("Please input username");
                        return parrentMenu.login(sc, parrentMenu, username, password);
                    }

                    if (password.isEmpty()) {
                        System.out.println("Please input password");
                        return parrentMenu.login(sc, parrentMenu, username, password);
                    }

                    System.out.print("Logging in...    ");

                    if (AuthActions.getInstance().loginUser(username, password)) {
                        return parrentMenu.mainMenu(sc, parrentMenu);
                    }

                    parrentMenu.login(sc, parrentMenu, username, password);
                }
                case 9:
                    return parrentMenu.mainMenu(sc, parrentMenu);
                default:
                    System.out.println("Invalid selection");
            }

        } while (selection != 9);

        return parrentMenu;
    }

    private Menu register(Scanner sc, Menu parrentMenu, String username, String password, String repeatPassword) {
        System.out.println("====== REGISTER ======");

        System.out.println(String.join("\n", registerMenu));
        int selection;

        do {
            selection = Integer.parseInt(sc.nextLine());

            switch (selection) {
                case 1: {
                    System.out.print("Input: ");
                    String usernameInput = sc.nextLine();

                    return parrentMenu.register(sc, parrentMenu, usernameInput, password, repeatPassword);
                }
                case 2: {
                    System.out.print("Input: ");
                    String passwordInput = sc.nextLine();

                    return parrentMenu.register(sc, parrentMenu, username, passwordInput, repeatPassword);
                }
                case 3: {
                    System.out.print("Input: ");
                    String repeatPasswordInput = sc.nextLine();

                    return parrentMenu.register(sc, parrentMenu, username, password, repeatPasswordInput);
                }
                case 4: {
                    if (username.isEmpty()) {
                        System.out.println("Please input username");
                        return parrentMenu.register(sc, parrentMenu, username, password, repeatPassword);
                    }

                    if (password.isEmpty()) {
                        System.out.println("Please input password");
                        return parrentMenu.register(sc, parrentMenu, username, password, repeatPassword);
                    }
                    if (!password.equals(repeatPassword)) {
                        System.out.println("Passwords do not match");
                        return parrentMenu.register(sc, parrentMenu, username, password, repeatPassword);
                    }

                    System.out.print("Registering...    ");

                    if (AuthActions.getInstance().registerUser(username, password)) {
                        return parrentMenu.mainMenu(sc, parrentMenu);
                    }

                    parrentMenu.register(sc, parrentMenu, username, password, repeatPassword);
                }
                case 9:
                    return parrentMenu.mainMenu(sc, parrentMenu);
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
