package com.techelevator;

public class Drink extends Item {
    public Drink(String name, Double price, String type) {
        super(name, price, type);
    }

    @Override
    public void printSound() {
        System.out.println("Glug Glug, Yum!");
    }
}
