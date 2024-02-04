package com.ui;

import java.util.Set;
import java.util.TreeSet;

public class Menu {
    private Set<MenuItem> actions;

    public Menu(MenuItem... items) {
        this.actions = new TreeSet<>(Set.of(items));
    }

    public void printMenu () {
        this.actions.forEach(action -> action.getMessage());
    }

}
