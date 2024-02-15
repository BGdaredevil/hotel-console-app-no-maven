package com.hotels.ui;

import com.hotels.auth.AuthActions;
import com.hotels.auth.User;
import com.hotels.forms.FormDataItem;
import com.hotels.hotel.Hotel;
import com.hotels.hotel.HotelService;
import com.hotels.utils.Color;
import com.hotels.utils.Validators;

import java.util.*;

public class Menu {
    public Menu mainMenu(Scanner sc, Menu parrentMenu) {
        User person = AuthActions.getInstance().getLoggedInUser();
        Hotel hotelContext = HotelService.getInstance().getHotelContext();
        boolean isGuest = person == null;
        boolean isHotel = hotelContext != null;
        List<String> mainMenuItems = new ArrayList<>();

        StringBuilder heading = new StringBuilder();
        heading.append("====== MAIN MENU ======");
        String delimiter = " | ";

        if (!isGuest) {
            heading.append(delimiter).append("Logged in as: ").append(person.username);
        }

        if (isHotel) {
            heading.append(delimiter).append("selected hotel: ").append(Hotel.getName(hotelContext));
        }

        String[] mainMenuGuest = {
                "====== MAIN MENU ======",
                "1. Select Hotel",
                "2. Book a Room",
                "3. Cancel booking",
                "4. Login",
                "5. Register",
                "9. Exit",
                "Please select an item: "
        };

        String[] mainMenuUser = {
                "====== MAIN MENU ======",
                "1. Select Hotel",
//                "View Rooms"
                "2. Book a Room",
                "3. Cancel booking",
                "4. Personal profile",
                "5. Admin portal",
                "6. Logout",
                "9. Exit",
                "Please select an item: "
        };

        mainMenuUser[0] = heading.toString();
        mainMenuGuest[0] = heading.toString();

        System.out.print(String.join("\n", isGuest ? mainMenuGuest : mainMenuUser));
        int selection;

        do {
            selection = this.getSelection(sc);

            switch (selection) {
//              "1. Select hotel"
                case 1: {
                    System.out.println("Please select a hotel:");
                    List<String> hotels = HotelService.getInstance().listHotels();

                    if (hotels.isEmpty()) {
                        System.out.println("There are no registered hotels");
                        return parrentMenu.mainMenu(sc, parrentMenu);
                    }

                    hotels.forEach(System.out::println);
                    System.out.print("Input: ");
                    String input = sc.nextLine();

                    if (!hotels.contains(input)) {
                        System.out.println(Color.color("red", "Invalid selection"));
                        return parrentMenu.mainMenu(sc, parrentMenu);
                    }

                    HotelService.getInstance().setHotelContext(input);
                    System.out.println(Color.color("green", "Selected " + input));

                    return parrentMenu.mainMenu(sc, parrentMenu);
                }

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
                            : parrentMenu.adminPortal(sc, parrentMenu);

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

    private Menu login(Scanner sc, Menu parrentMenu, Map<String, FormDataItem<String>> formData) {
        if (formData.isEmpty()) {
            FormDataItem<String> usernameInput = new FormDataItem<>("", (v) -> Validators.length(3, 12, v), "Username should be between 3 and 12 characters long.");
            FormDataItem<String> passwordInput = new FormDataItem<>("", (v) -> Validators.length(3, 12, v), "Password should be between 3 and 12 characters long and must match repeat password.");

            formData.put("username", usernameInput);
            formData.put("password", passwordInput);
        }

        String[] loginMenu = {
                "====== LOGIN ======",
                String.format("1. Username: %s", formData.get("username").getState(false)),
                String.format("2. Password: %s", formData.get("password").getState(true)),
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
                    formData.get("username").setValue(usernameInput);

                    return parrentMenu.login(sc, parrentMenu, formData);
                }
                case 2: {
                    System.out.print("Input: ");
                    String passwordInput = sc.nextLine();
                    formData.get("password").setValue(passwordInput);

                    return parrentMenu.login(sc, parrentMenu, formData);
                }
                case 3: {
                    FormDataItem<String> usernameForm = formData.get("username");
                    FormDataItem<String> passwordForm = formData.get("password");

                    if (!formData.get("username").isValid() || !formData.get("password").isValid()) {
                        return parrentMenu.login(sc, parrentMenu, formData);
                    }

                    System.out.print("Logging in...    ");

                    if (AuthActions.getInstance().loginUser(usernameForm.getValue(), passwordForm.getValue())) {
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

    private Menu adminPortal(Scanner sc, Menu parrentMenu) {
        String[] menuOptions = {
                "====== ADMIN PANEL ======",
                "1. Create hotel",
                "2. Edit hotel",
                "3. Delete hotel",
                "9. Exit",
                "Please select an item: "
        };
        System.out.print(String.join("\n", menuOptions));

        int selection;

        do {
            selection = this.getSelection(sc);

            switch (selection) {
                case 1:
                    return parrentMenu.modifyHotel(sc, parrentMenu, new HashMap<>(), true);
                case 2: {
                    // get hotel data and prepopulate
                    System.out.println("Please select a hotel:");
                    Set<String> userHotels = AuthActions.getInstance().getLoggedInUser().getAdminHotels();
                    userHotels.forEach(System.out::println);
                    System.out.print("Input: ");
                    String input = sc.nextLine();

                    if (!userHotels.contains(input)) {
                        System.out.println(Color.color("red", "Invalid selection"));
                        return parrentMenu.adminPortal(sc, parrentMenu);
                    }

                    Hotel itemToEdit = HotelService.getInstance().getHotelByName(input);
                    System.out.println("selected: " + Hotel.getName(itemToEdit));
                    System.out.println("Editing is not available at this time.");

                    return parrentMenu.adminPortal(sc, parrentMenu);
                }
                case 3: {
                    System.out.println("Please select a hotel:");
                    Set<String> userHotels = AuthActions.getInstance().getLoggedInUser().getAdminHotels();
                    userHotels.forEach(System.out::println);
                    System.out.print("Input: ");
                    String input = sc.nextLine();

                    if (!userHotels.contains(input)) {
                        System.out.println(Color.color("red", "Invalid selection"));
                        return parrentMenu.adminPortal(sc, parrentMenu);
                    }

                    System.out.print("Confirm? y/n: ");

                    String choice = sc.nextLine();

                    if (choice.equals("y")) {
                        HotelService.getInstance().deleteHotel(input);
                        System.out.println(Color.color("green", "deleted " + input));
                    } else {
                        System.out.println(Color.color("green", "cancelled"));
                    }

                    return parrentMenu.adminPortal(sc, parrentMenu);
                }

                case 9:
                    return parrentMenu.mainMenu(sc, parrentMenu);
                default:
                    System.out.println(Color.color("red", "Invalid selection"));
            }

        } while (selection != 9);

        return parrentMenu;
    }

    private Menu modifyHotel(Scanner sc, Menu parrentMenu, Map<String, FormDataItem<?>> formData, boolean createMode) {
        if (formData.isEmpty()) {
            int roomTypesCount = HotelService.getTypesCount();
            formData.put("name", new FormDataItem<>("", (v) -> {
                try {
                    // todo alphanumeric characters regex

                    return true;
                } catch (Exception e) {
                    return false;
                }

            }, "Hotel name should contain only alphanumeric characters."));
            formData.put("counts", new FormDataItem<>("", (v) -> {
                try {
                    int[] items = HotelService.processCountsCapacities(v);
                    if (items.length != roomTypesCount) {
                        return false;
                    }

                    boolean result = true;
                    for (int item : items) {
                        result = result && Validators.moreThan(0, item);
                        if (!result) {
                            return result;
                        }
                    }

                    return result;
                } catch (Exception e) {
                    return false;
                }

            }, "Rooms count should be positive integers"));
            formData.put("capacities", new FormDataItem<>("", (v) -> {
                try {
                    int[] items = HotelService.processCountsCapacities(v);
                    if (items.length != roomTypesCount) {
                        return false;
                    }

                    boolean result = true;
                    for (int item : items) {
                        result = result && Validators.moreThan(0, item);
                        if (!result) {
                            return result;
                        }
                    }

                    return result;
                } catch (Exception e) {
                    return false;
                }
            }, "Rooms capacities should be positive integers"));
            formData.put("prices", new FormDataItem<>("", (v) -> {
                try {
                    double[] items = HotelService.processPricesFees(v);
                    if (items.length != roomTypesCount) {
                        return false;
                    }

                    boolean result = true;
                    for (double item : items) {
                        result = result && Validators.moreThan(0.01, item);
                        if (!result) {
                            return result;
                        }
                    }

                    return result;
                } catch (Exception e) {
                    return false;
                }
            }, "Rooms prices should be positive real numbers"));
            formData.put("cancellationFees", new FormDataItem<>("", (v) -> {
                try {
                    double[] items = HotelService.processPricesFees(v);
                    if (items.length != roomTypesCount) {
                        return false;
                    }

                    boolean result = true;
                    for (double item : items) {
                        result = result && Validators.moreThan(0.01, item);
                        if (!result) {
                            return result;
                        }
                    }

                    return result;
                } catch (Exception e) {
                    return false;
                }
            }, "Rooms cancellation fees should be between 0 and 100 %"));
            formData.put("amenities", new FormDataItem<>("", (v) -> {
                // todo alphanumeric regex validation
                return true;
            }, "Rooms amenities should be separated with a space"));
        }

        FormDataItem<String> nameInput = (FormDataItem<String>) formData.get("name");
        FormDataItem<String> countsInput = (FormDataItem<String>) formData.get("counts");
        FormDataItem<String> capacitiesInput = (FormDataItem<String>) formData.get("capacities");
        FormDataItem<String> pricesInput = (FormDataItem<String>) formData.get("prices");
        FormDataItem<String> cancellationFeesInput = (FormDataItem<String>) formData.get("cancellationFees");
        FormDataItem<String> amenitiesInput = (FormDataItem<String>) formData.get("amenities");

        String[] menuOptions = {
                (createMode ? "====== CREATE HOTEL ======" : "====== EDIT HOTEL ======"),
                String.format("1. Name: %s", formData.get("name").getState(false)),
                String.format("2. Rooms count CSV ({regular}, {deluxe}): %s", formData.get("counts").getState(false)),
                String.format("3. Rooms capacity CSV ({regular}, {deluxe}): %s", formData.get("capacities").getState(false)),
                String.format("4. Pricing CSV ({regular}, {deluxe}): %s", formData.get("prices").getState(false)),
                String.format("5. Cancellation fees CSV ({regular}, {deluxe}): %s", formData.get("cancellationFees").getState(false)),
                String.format("6. Amenities CSV ({regular1 regular2}, {deluxe1 deluxe2}): %s", formData.get("amenities").getState(false)),
                "7. Submit",
                "9. Exit",
                "Please select an item: "
        };
        System.out.print(String.join("\n", menuOptions));

        int selection;

        do {
            selection = this.getSelection(sc);
            switch (selection) {
                case 1:
                    System.out.print("Input ");
                    nameInput.setValue(sc.nextLine());
                    return parrentMenu.modifyHotel(sc, parrentMenu, formData, createMode);
                case 2:
                    System.out.print("Input ");
                    countsInput.setValue(sc.nextLine());
                    return parrentMenu.modifyHotel(sc, parrentMenu, formData, createMode);
                case 3:
                    System.out.print("Input ");
                    capacitiesInput.setValue(sc.nextLine());
                    return parrentMenu.modifyHotel(sc, parrentMenu, formData, createMode);
                case 4:
                    System.out.print("Input ");
                    pricesInput.setValue(sc.nextLine());
                    return parrentMenu.modifyHotel(sc, parrentMenu, formData, createMode);
                case 5:
                    System.out.print("Input ");
                    cancellationFeesInput.setValue(sc.nextLine());
                    return parrentMenu.modifyHotel(sc, parrentMenu, formData, createMode);
                case 6:
                    System.out.print("Input ");
                    amenitiesInput.setValue(sc.nextLine());
                    return parrentMenu.modifyHotel(sc, parrentMenu, formData, createMode);
                case 7: {
                    Hotel hotel = HotelService.getInstance().create(nameInput.getValue(), countsInput.getValue(), capacitiesInput.getValue(), pricesInput.getValue(), cancellationFeesInput.getValue(), amenitiesInput.getValue());
                    AuthActions.getInstance().getLoggedInUser().setAdmin(Hotel.getName(hotel));
                    return parrentMenu.adminPortal(sc, parrentMenu);
                }
                case 9:
                    return parrentMenu.adminPortal(sc, parrentMenu);
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
