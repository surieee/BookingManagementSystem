package com.wg.bookmyshow.util;

import com.wg.bookmyshow.model.BookingModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingPrinter {

    // Define the format for headers and rows
    private static final String HEADER_FORMAT = "%-40s | %-25s | %-20s | %-40s | %-40s | %-40s";
    private static final String ROW_FORMAT = "%-40s | %-25s | %-20s | %-40s | %-40s | %-40s";
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("0.00");
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final String BOX_BORDER = "==========================================================================================================================================================================================================================================================";
    private static final String DIVIDER_LINE = "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

    public static void printBookings(List<BookingModel> bookings) {
        // Print table title
        System.out.println(BOX_BORDER);
        System.out.println(centerTextInBox("BOOKING DETAILS"));
        System.out.println(BOX_BORDER);

        // Print header
        System.out.printf(HEADER_FORMAT, "Booking ID", "Booking Date", "Total Amount", "Payment ID", "Ticket ID", "User ID");
        System.out.println();
        System.out.println(DIVIDER_LINE);

        // Print rows
        for (BookingModel booking : bookings) {
            try {
                // Print each booking row
                System.out.printf(ROW_FORMAT,
                    maskBookingId(booking.getBookingId()), // Masking booking ID
                    booking.getBookingDate() != null ? DATE_FORMATTER.format(booking.getBookingDate()) : "N/A",
                    PRICE_FORMATTER.format(booking.getTotalAmount()), // Format total amount
                    booking.getPaymentId(),
                    booking.getTicketId(),
                    maskUserId(booking.getUserId()) // Masking user ID
                );
                System.out.println();
                System.out.println(DIVIDER_LINE);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while printing booking: " + booking);
            }
        }
        System.out.println(BOX_BORDER); // Close the box border after listing all bookings
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

    // Utility method to mask booking ID (example)
    private static String maskBookingId(String bookingId) {
        if (bookingId == null || bookingId.length() < 5) {
            return "*****"; // Default mask if bookingId is too short or null
        }
        return "*****" + bookingId.substring(bookingId.length() - 5); // Mask all but last 5 characters
    }

    // Utility method to mask user ID (example)
    private static String maskUserId(String userId) {
        if (userId == null || userId.length() < 5) {
            return "*****"; // Default mask if userId is too short or null
        }
        return "*****" + userId.substring(userId.length() - 5); // Mask all but last 5 characters
    }
}


