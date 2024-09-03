package com.wg.bookmyshow.util;

import com.wg.bookmyshow.model.TicketModel;

import java.text.DecimalFormat;
import java.util.List;

public class TicketPrinter {

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLUE = "\u001B[34m";

    // Define the format for headers and rows with an index
    private static final String HEADER_FORMAT = CYAN + "%-5s | %-40s | %-30s | %-15s | %-15s" + RESET;
    private static final String ROW_FORMAT = "%-5d | %-40s | %-30s | %-15s | %-15s";
    private static final String BOX_BORDER = CYAN + "===========================================================================================================================================" + RESET;
    private static final String DIVIDER_LINE = CYAN + "---------------------------------------------------------------------------------------------------------------------------" + RESET;
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("0.00");

    public static void printTickets(List<TicketModel> tickets) {
        // Print table title
        System.out.println(BOX_BORDER);
        System.out.println(centerTextInBox("TICKET DETAILS"));
        System.out.println(BOX_BORDER);

        // Print header
        System.out.printf(HEADER_FORMAT, "Index", "Ticket ID", "Event ID", "Seat Number", "Status");
        System.out.println();
        System.out.println(DIVIDER_LINE);

        // Print rows with index
        for (int i = 0; i < tickets.size(); i++) {
            TicketModel ticket = tickets.get(i);
            try {
                // Determine color based on ticket status
                String statusColor = getStatusColor(ticket.getTicketStatus());

                // Print each ticket row with index and color coding
                System.out.printf(statusColor + ROW_FORMAT + RESET,
                    (i + 1), // Index starts from 1
                    ticket.getTicketId(),
                    MaskId.maskId(ticket.getEventId()),
                    ticket.getSeatNumber(),
                    ticket.getTicketStatus()
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

    // Method to get color based on ticket status
    private static String getStatusColor(String status) {
        switch (status.toUpperCase()) {
            case "BOOKED":
                return GREEN; // Booked status in green
            case "AVAILABLE":
                return BLUE; // Available status in blue
            case "CANCELLED":
                return RED; // Cancelled status in red
            case "PENDING":
                return YELLOW; // Pending status in yellow
            default:
                return RESET; // Default color for unknown status
        }
    }
}

