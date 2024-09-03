package com.wg.bookmyshow.util;

import com.wg.bookmyshow.model.NotificationModel;

import java.util.List;

public class NotificationPrinter2 {

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    // Define the format for headers and rows
    private static final String HEADER_FORMAT = CYAN + "%-90s" + " | " + "%-10s" + RESET;
    private static final String ROW_FORMAT = "%-90s" + " | " + "%-10s";
    private static final String BOX_BORDER = CYAN + "==========================================================================================================" + RESET;
    private static final String DIVIDER_LINE = CYAN + "----------------------------------------------------------------------------------------------------------" + RESET;

    public static void printNotifications(List<NotificationModel> notifications) {
        // Print table title with enhanced color
        System.out.println(BOX_BORDER);
        System.out.println(centerTextInBox("NOTIFICATION DETAILS"));
        System.out.println(BOX_BORDER);

        // Print header
        System.out.printf(HEADER_FORMAT, "Message", "Priority");
        System.out.println();
        System.out.println(DIVIDER_LINE);

        // Print rows
        for (NotificationModel notification : notifications) {
            try {
                // Determine color based on priority
                String priorityColor = getPriorityColor(notification.getPriority());

                // Print each notification row with color coding
                System.out.printf(priorityColor + ROW_FORMAT + RESET,
                		 notification.getMessage(), // Mask sensitive data in message
                        notification.getPriority()
                );
                System.out.println();
                System.out.println(DIVIDER_LINE);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while printing notification: " + notification);
            }
        }
        System.out.println(BOX_BORDER); // Close the box border after listing all notifications
    }

    // Helper method to center the text within a box
    private static String centerTextInBox(String text) {
        int boxWidth = BOX_BORDER.length();
        int textLength = text.length();
        int padding = (boxWidth - textLength) / 2;

        // Creating a line with centered text surrounded by spaces
        StringBuilder centeredText = new StringBuilder();
        centeredText.append(" ".repeat(padding));
        centeredText.append(text);
        centeredText.append(" ".repeat(padding));

        // Ensure the line is exactly as wide as the box, accounting for odd width
        while (centeredText.length() < boxWidth) {
            centeredText.append(" ");
        }

        return centeredText.toString();
    }

    // Method to get color based on priority
    private static String getPriorityColor(String priority) {
        switch (priority) {
            case "HIGH":
                return RED; // High priority notifications in red
            case "MEDIUM":
                return YELLOW; // Medium priority notifications in yellow
            case "LOW":
                return GREEN; // Low priority notifications in green
            default:
                return RESET; // Default color
        }
    }

    // Helper method to mask sensitive data in the message
//    private static String maskSensitiveData(String message) {
//        if (message == null) {
//            return "N/A";
//        }
//
//        // For example, if the message contains an ID, mask it:
//        return message.replaceAll("\\b(\\d{4})\\d+\\b", "****$1"); // Mask IDs except last 4 digits
//    }
}
