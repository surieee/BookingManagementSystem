package com.wg.bookmyshow.util;

public class ConsoleUtils {



    public static void printRoundedBox(String message) {
        int width = message.length() + 4; // Adjust width for padding and border
        String topBottomBorder = "═".repeat(width);
        
        System.out.println("╔" + topBottomBorder + "╗");
        System.out.println("║ " + message + " ║");
        System.out.println("╚" + topBottomBorder + "╝");
    }
}

