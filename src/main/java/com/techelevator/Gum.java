package com.techelevator;

public class Gum extends  Item {
    public Gum(String name, Double price, String type) {
        super(name, price, type);
    }

    @Override
    public void printSound() {
        System.out.println("Chew Chew, Yum!");
    }
}
