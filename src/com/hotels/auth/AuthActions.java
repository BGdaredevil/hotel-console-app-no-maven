package com.hotels.auth;

import java.util.HashMap;
import java.util.Map;

public final class AuthActions {
    private static AuthActions instance = null;

    private User loggedInUser = null;

    private Map<String, User> users;

    private AuthActions() {
        this.users = new HashMap<>();
    }
    public boolean registerUser(String userName, String password) {
        if (this.loggedInUser != null) {
            System.out.println("please log out first");
            return false;
        }

        if (users.containsKey(userName)) {
            System.out.println("Invalid input (username taken)");
            return false;
        }
        String hashedPassword = String.valueOf(password.hashCode());
        User person = new User(userName, hashedPassword);
        users.put(userName, person);
        this.loggedInUser = person;

        System.out.printf("Welcome, %s\n", userName);

        return true;
    }

    public boolean loginUser(String userName, String password) {
        if (this.loggedInUser != null) {
            System.out.println("please log out first");
            return false;
        }

        if (!users.containsKey(userName)) {
            System.out.println("Invalid username or password");
            return false;
        }
        String hashedPassword = String.valueOf(password.hashCode());
        User person = users.get(userName);

        if (User.verifyPassword(person, hashedPassword)) {
            System.out.printf("Welcome, %s\n", userName);
            this.loggedInUser = person;
            return true;
        }

        System.out.println("Invalid username or password");
        return false;
    }

    public void logout() {
        this.loggedInUser = null;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public static AuthActions getInstance() {
        if (instance == null) {
            instance = new AuthActions();
        }

        return instance;
    }
}
