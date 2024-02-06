package com.hotels.ui;

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

    public void getSelection(int index) {
        System.out.printf("%d item selected", index);
    }

}