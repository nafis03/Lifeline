package ui;

import java.io.FileNotFoundException;

// main method that starts (instantiates a new task manager)
public class Main {
    public static void main(String[] args) {
        try {
            new TaskManager();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}