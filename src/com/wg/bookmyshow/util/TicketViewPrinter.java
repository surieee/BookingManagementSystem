package com.wg.bookmyshow.util;

import com.wg.bookmyshow.model.TicketViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class TicketViewPrinter {

    // Define the format for headers and rows
    private static final String HEADER_FORMAT = "%-40s | %-15s | %-20s | %-40s | %-30s | %-20s | %-40s | %-40s | %-40s | %-30s | %-20s | %-20s";
    private static final String ROW_FORMAT = "%-40s | %-15s | %-20s | %-40s | %-30s | %-20s | %-40s | %-40s | %-40s | %-30s | %-20s | %-20s";
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("0.00");
    private static final String BOX_BORDER = "============================================================================================================================================================================================================================================================================================================================================================================================================";
    private static final String DIVIDER_LINE = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

    public static void printTickets(List<TicketViewModel> tickets) {
        // Print table title
        System.out.println(BOX_BORDER);
        System.out.println(centerTextInBox("USER TICKETS"));
        System.out.println(BOX_BORDER);

        // Print header
        System.out.printf(HEADER_FORMAT, "Ticket ID", "Seat", "Ticket Status", "Booking ID", "Booking Date", "Price", "Event ID", "User ID", "Payment ID", "Bill", "Payment Date", "Payment Status");
        System.out.println();
        System.out.println(DIVIDER_LINE);

        // Print rows
        for (TicketViewModel ticket : tickets) {
            try {
                // Print each ticket row with masked IDs
                System.out.printf(ROW_FORMAT,
                    ticket.getTicketId(), // Ticket ID is shown fully
                    ticket.getSeatNumber(),
                    ticket.getTicketStatus(),
                    MaskId.maskId(ticket.getBookingId()), // Mask Booking ID
                    ticket.getBookingDate() != null ? ticket.getBookingDate() : "N/A",
                    PRICE_FORMATTER.format(ticket.getTotalAmount()),
                    MaskId.maskId(ticket.getBookingEventId()), // Mask Event ID
                    MaskId.maskId(ticket.getUserId()), // Mask User ID
                    MaskId.maskId(ticket.getPaymentId()), // Mask Payment ID
                    PRICE_FORMATTER.format(ticket.getBillAmount()),
                    ticket.getDateOfPayment() != null ? ticket.getDateOfPayment() : "N/A",
                    ticket.getPaymentStatus()
                );
                System.out.println();
                System.out.println(DIVIDER_LINE);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while printing ticket: " + ticket);
            }
        }
    }

    // Helper method to center the text within a box
    private static String centerTextInBox(String text) {
        int boxWidth = BOX_BORDER.length();
        int textLength = text.length();
        int padding = (boxWidth - textLength) / 2;

        StringBuilder centeredText = new StringBuilder();
        centeredText.append(" ".repeat(padding));
        centeredText.append(text);
        centeredText.append(" ".repeat(padding));

        while (centeredText.length() < boxWidth) {
            centeredText.append(" ");
        }

        return centeredText.toString();
    }

//    // Helper method to mask IDs, showing only the last 4 digits
//    private static String maskId(String id) {
//        if (id == null || id.length() <= 4) {
//            return id; // If the ID is null or too short, return as is
//        }
//        // Mask all but the last 4 characters of the ID
//        int length = id.length();
//        return "****" + id.substring(length - 4);
//    }
}

