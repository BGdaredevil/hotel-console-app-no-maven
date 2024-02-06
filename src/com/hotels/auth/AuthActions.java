package com.hotels.auth;

import java.util.Map;

public final class AuthActions {
    private static AuthActions instance = null;
    private User loggedInUser;
    private Map<String, User> users;
    private AuthActions() {
    }
    public void registerUser(String userName, String password) {
        if (this.loggedInUser != null) {
            System.out.println("please log out first");
            return;
        }

        if (users.containsKey(userName)) {
            System.out.println("Invalid input (username taken)");
            return;
        }
        String hashedPassword = String.valueOf(password.hashCode());
        User person = new User(userName, hashedPassword);
        users.put(userName, person);
        this.loggedInUser = person;
        System.out.printf("Welcome, %s\n", userName);
    }
    public void loginUser(String userName, String password) {
        if (this.loggedInUser != null) {
            System.out.println("please log out first");
            return;
        }

        if (!users.containsKey(userName)) {
            System.out.println("Invalid username or password");
            return;
        }
        String hashedPassword = String.valueOf(password.hashCode());
        User person = users.get(userName);

        if (User.verifyPassword(person, hashedPassword)) {
            System.out.printf("Welcome, %s\n", userName);
            this.loggedInUser = person;
            return;
        }

        System.out.println("Invalid username or password");
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
