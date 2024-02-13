package com.hotels.auth;

import com.hotels.utils.Color;
import com.hotels.utils.DbActions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class AuthActions {
    private static AuthActions instance = null;

    private User loggedInUser = null;

    private Map<String, User> users;

    private AuthActions() {
        this.users = new HashMap<>();

        try {
            Map<String, User> persitedData = DbActions.getInstance().readObjectMapFromFile("users.json", User.class);
            this.users = persitedData;

        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println(Color.backgroundColor("red", e.getMessage()));
        }
    }

    public boolean registerUser(String userName, String password) {
        if (this.loggedInUser != null) {
            System.out.println(Color.color("red", "please log out first"));
            return false;
        }

        if (users.containsKey(userName)) {
            System.out.println(Color.color("red", "Invalid input (username taken)"));

            return false;
        }
        String hashedPassword = String.valueOf(password.hashCode());
        User person = new User(userName, hashedPassword);
        users.put(userName, person);
        this.loggedInUser = person;

        try {
            DbActions.getInstance().writeObjectToFile(this.users, "users.json");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(Color.color("green", String.format("Welcome, %s\n", userName)));
        return true;
    }

    public boolean loginUser(String userName, String password) {
        if (this.loggedInUser != null) {
            System.out.println(Color.color("red", "please log out first"));
            return false;
        }

        if (!users.containsKey(userName)) {
            System.out.println(Color.color("red", "Invalid username or password"));
            return false;
        }

        String hashedPassword = String.valueOf(password.hashCode());
        User person = users.get(userName);
        if (User.verifyPassword(person, hashedPassword)) {
            System.out.println(Color.color("green", String.format("Welcome, %s\n", userName)));
            this.loggedInUser = person;
            return true;
        }

        System.out.println(Color.color("red", "Invalid username or password"));
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
