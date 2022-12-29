package com.techelevator;

public class Item {

    private String name;
    private Double price;
    private String type;
    private int qty;

    public Item(String name, Double price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
        qty = 5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQty(int qty) { this.qty = qty;}

    public int getQty() {
        return qty;
    }

    public String toString() {
        return name + ", " + price + ", " + type + ", qty: " + qty;
    }

    public void printSound() { }
}
