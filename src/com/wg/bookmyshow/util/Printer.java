package com.wg.bookmyshow.util;

public class Printer {

    // ANSI escape codes for color
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private static final String BOX_CHAR = "*";
    private static final int BOX_WIDTH = 80; // Default width of the box

    // Method to print a header with default color
    public void printHeader(String header) {
        printHeader(header, ANSI_BLUE);
    }

    // Overloaded method to print a header with specified color
    public void printHeader(String header, String color) {
        printLine(color);
        printCenteredLine(header, color);
        printLine(color);
    }

    // Method to print a line with a specified color
    private void printLine(String color) {
        System.out.println(color + BOX_CHAR.repeat(BOX_WIDTH) + ANSI_RESET);
    }

    // Method to print a centered line with a specified color
    private void printCenteredLine(String text, String color) {
        int padding = (BOX_WIDTH - text.length() - 4) / 2; // Adjusted padding
        String line = BOX_CHAR + " " + " ".repeat(padding) + text + " ".repeat(padding) + " " + BOX_CHAR;
        // Adjust for cases where padding might not be symmetrical
        if (line.length() < BOX_WIDTH) {
            line += " ";
        }
        System.out.println(color + line + ANSI_RESET);
    }

    // Method to print a double-lined header
    public void printDoubleLineHeader(String header) {
        printLine(ANSI_YELLOW);
        printCenteredLine(header, ANSI_YELLOW);
        printLine(ANSI_YELLOW);
        printLine(ANSI_YELLOW); // Additional line for double line effect
    }

    // Method to print an error message in red
    public void printError(String message) {
        printHeader(message, ANSI_RED);
    }

    // Method to print a success message in green
    public void printSuccess(String message) {
        printHeader(message, ANSI_GREEN);
    }

    // Method to print a warning message in yellow
    public void printWarning(String message) {
        printHeader(message, ANSI_YELLOW);
    }

    // Overloaded method to print a custom box width
    public void printCustomWidthHeader(String header, int customWidth) {
        printLine(customWidth, ANSI_BLUE);
        printCenteredLine(header, customWidth, ANSI_BLUE);
        printLine(customWidth, ANSI_BLUE);
    }

    // Method to print a line with custom width and color
    private void printLine(int width, String color) {
        System.out.println(color + BOX_CHAR.repeat(width) + ANSI_RESET);
    }

    // Method to print a centered line with custom width and color
    private void printCenteredLine(String text, int width, String color) {
        int padding = (width - text.length() - 4) / 2;
        String line = BOX_CHAR + " " + " ".repeat(padding) + text + " ".repeat(padding) + " " + BOX_CHAR;
        if (line.length() < width) {
            line += " ";
        }
        System.out.println(color + line + ANSI_RESET);
    }



}

