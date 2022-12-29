package com.techelevator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TELog {

    static PrintWriter writer;

    public static void log(String message) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTime = localDateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss a"));

        if (writer == null) {
            try {
                writer = new PrintWriter("src/main/Log.txt");
            } catch(FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
        writer.println(dateTime + " " + message);
        writer.flush();
    }
}
