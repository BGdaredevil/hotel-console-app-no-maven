package com.hotels.ui;

import com.hotels.auth.AuthActions;
import com.hotels.auth.User;
import com.hotels.forms.FormDataItem;
import com.hotels.utils.Color;
import com.hotels.utils.Validators;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Menu {
    public Menu mainMenu(Scanner sc, Menu parrentMenu) {
        String[] mainMenuGuest = {
                "====== MAIN MENU ======",
                "1. View Rooms",
                "2. Book a Room",
                "3. Cancel booking",
                "4. Login",
                "5. Register",
                "9. Exit",
                "Please select an item: "
        };

        String[] mainMenuUser = {
                "====== MAIN MENU ======",
                "1. View Rooms",
                "2. Book a Room",
                "3. Cancel booking",
                "4. Personal profile",
                "5. Admin portal",
                "6. Logout",
                "9. Exit",
                "Please select an item: "
        };
        User person = AuthActions.getInstance().getLoggedInUser();
        boolean isGuest = person == null;

        System.out.print(String.join("\n", isGuest ? mainMenuGuest : mainMenuUser));
        int selection;

        do {
            selection = this.getSelection(sc);

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
                            ? parrentMenu.login(sc, parrentMenu, new HashMap<>())
//                "4. Personal profile"
                            : parrentMenu.subMenuTwo(sc, parrentMenu);

                case 5:
                    return isGuest
//                "5. Register"
                            ? parrentMenu.register(sc, parrentMenu, new HashMap<>())
//                "5. Admin portal"
                            : parrentMenu.subMenuTwo(sc, parrentMenu);

//                "6. Logout"
                case 6: {
                    if (isGuest) {
                        System.out.println(Color.color("red", "Invalid selection"));

                        break;
                    }

                    AuthActions.getInstance().logout();

                    return parrentMenu.mainMenu(sc, parrentMenu);
                }
                case 9:
                    return null;
                default:
                    System.out.println(Color.color("red", "Invalid selection"));

            }

        } while (selection != 9);

        return parrentMenu;
    }

    private Menu login(Scanner sc, Menu parrentMenu, Map<String, String> formData) {
        if (formData.isEmpty()) {
            formData.put("username", "");
            formData.put("password", "");
        }

        String[] loginMenu = {
                "====== LOGIN ======",
                String.format("1. Username: %s", Color.color("green", formData.get("username"))),
                String.format("2. Password: %s", Color.color("green", "*".repeat(formData.get("password").length()))),
                "3. Submit",
                "9. Exit",
                "Please select an item: "
        };
        System.out.print(String.join("\n", loginMenu));

        int selection;

        do {
            selection = this.getSelection(sc);

            switch (selection) {
                case 1: {
                    System.out.print("Input: ");
                    String usernameInput = sc.nextLine();
                    formData.put("username", usernameInput);
                    return parrentMenu.login(sc, parrentMenu, formData);
                }
                case 2: {
                    System.out.print("Input: ");
                    String passwordInput = sc.nextLine();
                    formData.put("password", passwordInput);
                    return parrentMenu.login(sc, parrentMenu, formData);
                }
                case 3: {
                    if (formData.get("username").isEmpty()) {
                        System.out.println(Color.color("red", "Please input username"));
                        return parrentMenu.login(sc, parrentMenu, formData);
                    }

                    if (formData.get("password").isEmpty()) {
                        System.out.println(Color.color("red", "Please input password"));
                        return parrentMenu.login(sc, parrentMenu, formData);
                    }

                    System.out.print("Logging in...    ");

                    if (AuthActions.getInstance().loginUser(formData.get("username"), formData.get("password"))) {
                        return parrentMenu.mainMenu(sc, parrentMenu);
                    }

                    parrentMenu.login(sc, parrentMenu, formData);
                }
                case 9:
                    return parrentMenu.mainMenu(sc, parrentMenu);
                default:
                    System.out.println(Color.color("red", "Invalid selection"));
            }

        } while (selection != 9);

        return parrentMenu;
    }

    private Menu register(Scanner sc, Menu parrentMenu, Map<String, FormDataItem<String>> formData) {
        if (formData.isEmpty()) {
            FormDataItem<String> usernameField = new FormDataItem<>("", (v) -> Validators.length(3, 12, v), "Username should be between 3 and 12 characters long.");
            FormDataItem<String> passwordField = new FormDataItem<>("", (v) -> Validators.length(3, 12, v), "Password should be between 3 and 12 characters long and must match repeat password.");
            FormDataItem<String> repeatPasswordField = new FormDataItem<>("", (v) -> passwordField.getValue().equals(v), "Passwords should match");

            formData.put("username", usernameField);
            formData.put("password", passwordField);
            formData.put("repeatPassword", repeatPasswordField);
        }
        String[] registerMenu = {
                "====== REGISTER ======",
                String.format("1. Username: %s", formData.get("username").getState(false)),
                String.format("2. Password: %s", formData.get("password").getState(true)),
                String.format("3. Repeat password: %s", formData.get("repeatPassword").getState(true)),
                "4. Submit",
                "9. Exit",
                "Please select an item: "
        };
        System.out.print(String.join("\n", registerMenu));

        int selection;

        do {
            selection = this.getSelection(sc);

            switch (selection) {
                case 1: {
                    System.out.print("Input: ");
                    String usernameInput = sc.nextLine();
                    formData.get("username").setValue(usernameInput);
                    return parrentMenu.register(sc, parrentMenu, formData);
                }
                case 2: {
                    System.out.print("Input: ");
                    String passwordInput = sc.nextLine();
                    formData.get("password").setValue(passwordInput);
                    formData.get("repeatPassword").isValid();

                    return parrentMenu.register(sc, parrentMenu, formData);
                }
                case 3: {
                    System.out.print("Input: ");
                    String repeatPasswordInput = sc.nextLine();
                    formData.get("repeatPassword").setValue(repeatPasswordInput);

                    return parrentMenu.register(sc, parrentMenu, formData);
                }
                case 4: {
                    boolean usernameIsValid = formData.get("username").isValid();
                    boolean passwordIsValid = formData.get("password").isValid();
                    boolean repeatPasswordIsValid = formData.get("repeatPassword").isValid();

                    if (!usernameIsValid || !passwordIsValid || !repeatPasswordIsValid) {
                        return parrentMenu.register(sc, parrentMenu, formData);
                    }

                    System.out.print("Registering...    ");

                    if (AuthActions.getInstance().registerUser(formData.get("username").getValue(), formData.get("password").getValue())) {
                        return parrentMenu.mainMenu(sc, parrentMenu);
                    }

                    parrentMenu.register(sc, parrentMenu, formData);
                }
                case 9:
                    return parrentMenu.mainMenu(sc, parrentMenu);
                default:
                    System.out.println(Color.color("red", "Invalid selection"));

            }

        } while (selection != 9);

        return parrentMenu;
    }

    private int getSelection(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println(Color.color("red", "Invalid selection"));
            return getSelection(sc);
        }
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

                    System.out.println(Color.color("red", "Invalid selection"));

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
                    System.out.println(Color.color("red", "Invalid selection"));
            }

        } while (selection != 9);

        return parrentMenu;
    }
}
