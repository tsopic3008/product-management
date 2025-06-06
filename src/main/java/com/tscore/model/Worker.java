package com.tscore.model;

public abstract class Worker {
    public String name;

    public Worker(String name) {
        this.name = name;
    }
    abstract public void work();

    public void logStart(){ System.out.println("Worker " + name + "is starting work");}

}
