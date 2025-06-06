package com.tscore.model;

public class Designer extends Worker {
    public Designer(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println("Creating a design...");
    }
}
