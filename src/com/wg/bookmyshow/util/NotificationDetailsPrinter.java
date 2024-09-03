package com.wg.bookmyshow.util;

import com.wg.bookmyshow.model.NotificationDetails;

import java.util.List;

public class NotificationDetailsPrinter {

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    // Define the format for headers and rows
    private static final String HEADER_FORMAT = CYAN + "%-100s" + " | " + "%-10s" + " | " + "%-10s" + " | " + "%-20s" + " | " + "%-15s" + RESET;
    private static final String ROW_FORMAT = "%-100s" + " | " + "%-10s" + " | " + "%-10s" + " | " + "%-20s" + " | " + "%-15s";
    private static final String BOX_BORDER = CYAN + "===========================================================================================================================================================" + RESET;
    private static final String DIVIDER_LINE = CYAN + "----------------------------------------------------------------------------------------------------------------------------------------------------------" + RESET;

    public static void printNotificationDetails(List<NotificationDetails> notifications) {
        // Print table title with enhanced color
        System.out.println(BOX_BORDER);
        System.out.println(centerTextInBox("NOTIFICATION DETAILS"));
        System.out.println(BOX_BORDER);

        // Print header
        System.out.printf(HEADER_FORMAT, "Message", "Priority", "User ID", "Event Name", "Event Status");
        System.out.println();
        System.out.println(DIVIDER_LINE);

        // Print rows
        for (NotificationDetails notification : notifications) {
            try {
                // Determine color based on priority
                String priorityColor = getPriorityColor(notification.getNotificationPriority());

                // Print each notification row with color coding
                System.out.printf(priorityColor + ROW_FORMAT + RESET,
                        notification.getNotificationMessage(),
                        notification.getNotificationPriority(),
                        MaskId.maskId(notification.getNotifiedUserId()),
                        notification.getEventName(),
                        notification.getEventStatus()
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

//    // Utility method to truncate long messages to fit into the table
//    private static String truncate(String value, int length) {
//        if (value == null || value.length() <= length) {
//            return value != null ? value : ""; // Return value or empty if null
//        } else {
//            return value.substring(0, length) + "...";
//        }
//    }

    // Method to mask user ID and show only the last 5 digits
//    private static String maskUserId(String userId) {
//        if (userId == null || userId.length() < 5) {
//            return "*****"; // Return default mask if userId is too short or null
//        }
//        return "*****" + userId.substring(userId.length() - 5); // Mask all but last 5 digits
//    }
}

