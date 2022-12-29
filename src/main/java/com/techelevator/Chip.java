package com.techelevator;

public class Chip extends  Item {
    public Chip(String name, Double price, String type) {
        super(name, price, type);
    }

    @Override
    public void printSound() {
        System.out.println("Crunch Crunch, Yum!");
    }
}
