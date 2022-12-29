package com.techelevator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class SalesReport {

    public static void report(TreeMap<String, Item> items) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTime = localDateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss"));
        double totalSale = 0;

        try (PrintWriter reportWriter = new PrintWriter("src/main/" + dateTime)) {
            for (Map.Entry<String, Item> item : items.entrySet()) {
                reportWriter.println(item.getValue().getName() + "|" + (5 - item.getValue().getQty()));
                if (item.getValue().getQty() < 5) {
                    totalSale += (5 - item.getValue().getQty()) * item.getValue().getPrice();
                }
            }
            reportWriter.println("**TOTAL SALES**" + VendingMachineCLI.dollarFormat(totalSale));
        } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
        }  

    }
}
