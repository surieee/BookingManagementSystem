package com.wg.bookmyshow.util;

import com.wg.bookmyshow.model.NotificationModel;

import java.util.List;

public class NotificationPrinter {

    // Define the format for headers and rows
    private static final String HEADER_FORMAT = "%-20s | %-50s | %-10s | %-20s";
    private static final String ROW_FORMAT = "%-20s | %-50s | %-10s | %-20s";
    private static final String BOX_BORDER = "===================================================================================================================================================================================================";
    private static final String DIVIDER_LINE = "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

    public static void printNotifications(List<NotificationModel> notifications) {
        // Print table title
        System.out.println(BOX_BORDER);
        System.out.println(centerTextInBox("NOTIFICATION DETAILS"));
        System.out.println(BOX_BORDER);

        // Print header
        System.out.printf(HEADER_FORMAT, "Notification ID", "Message", "Priority", "Booking ID");
        System.out.println();
        System.out.println(DIVIDER_LINE);

        // Print rows
        for (NotificationModel notification : notifications) {
            try {
                // Print each notification row with masked IDs
                System.out.printf(ROW_FORMAT,
                    MaskId.maskId(notification.getNotificationId()),
                    notification.getMessage(),
                    notification.getPriority(),
                    MaskId.maskId(notification.getBookingId())
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

//    // Helper method to mask IDs except for the last 4 digits
//    private static String maskId(String id) {
//        if (id == null || id.length() <= 4) {
//            return "****"; // Return masked value if ID is too short
//        }
//        return "****" + id.substring(id.length() - 4); // Mask all but last 4 digits
//    }
}

    
