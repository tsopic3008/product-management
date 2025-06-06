package com.tscore.model;

public class Developer extends Worker {
    public Developer(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println("Writing code...");
    }
}
