package com.wg.bookmyshow.util;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class PasswordMasking {

    // Method to read password using java.io.Console
    public static String readPasswordUsingConsole() {
        Console console = System.console();
        if (console != null) {
            char[] passwordArray = console.readPassword("Enter your password: ");
            return new String(passwordArray);
        } else {
            // Fallback to custom masking function if Console is not available
            return readPasswordUsingSystemIn();
        }
    }

    // Method to read password using System.in with manual masking
    public static String readPasswordUsingSystemIn() {
        StringBuilder password = new StringBuilder();
        try {
            System.out.print("Enter your password: ");
            while (true) {
                char inputChar = (char) System.in.read(); // Read character
                if (inputChar == '\n' || inputChar == '\r') {
                    break; // Break on Enter key
                }
                password.append(inputChar);
                System.out.print("\b*"); // Mask the character
            }
        } catch (IOException e) {
            System.err.println("Error reading password: " + e.getMessage());
        }
        System.out.println(); // Move to the next line after password input
        return password.toString();
    }

    public static void main(String[] args) {
        String password = readPasswordUsingConsole();
        System.out.println("Your password is: " + password); // Only for demonstration; avoid printing passwords in real apps.
    }
}


