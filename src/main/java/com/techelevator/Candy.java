package com.techelevator;

import com.techelevator.Item;

public class Candy extends Item {
    public Candy(String name, Double price, String type) {
        super(name, price, type);
    }

    @Override
    public void printSound() {
        System.out.println("Munch Munch, Yum!");
    }
}
