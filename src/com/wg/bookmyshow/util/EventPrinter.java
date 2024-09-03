package com.wg.bookmyshow.util;

import com.wg.bookmyshow.model.EventModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventPrinter {

    private static final String HEADER_FORMAT = "%-40s | %-30s | %-20s | %-10s | %-20s | %-25s | %-40s | %-15s | %-20s | %-20s | %-15s | %-15s";
    private static final String ROW_FORMAT = "%-40s | %-30s | %-20s | %-10s | %-20s | %-25s | %-40s | %-15d | %-20s | %-20s | %-15s | %-15s";
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("0.00");
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final String BOX_BORDER = "===================================================================================================================================================================" +
                                             "===================================================================================================================================================================";
    private static final String DIVIDER_LINE = "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" +
                                               "-----------------------------------------------------------------------------------------------------------------------------------------------------------------";

    public static void printEvents(List<EventModel> events) {
        // Print table title
        System.out.println(BOX_BORDER);
        System.out.println(centerTextInBox("EVENT DETAILS"));
        System.out.println(BOX_BORDER);

        // Print header
        System.out.printf(HEADER_FORMAT, "Event ID", "Event Name", "Venue", "Price", "Date", "Type", "Description", "Seats Avail.", "Total Seats", "Duration", "Blocked", "Event Status");
        System.out.println();
        System.out.println(DIVIDER_LINE);

        // Print rows
        for (EventModel event : events) {
            try {
                // Print each event row
                System.out.printf(ROW_FORMAT,
                    MaskId.maskId(event.getEventId()), // Masking event ID
                    truncate(event.getEventName(), 30),
                    truncate(event.getVenue(), 20),
                    PRICE_FORMATTER.format(event.getPrice()), // Format price
                    event.getEventDate() != null ? DATE_FORMATTER.format(event.getEventDate()) : "N/A",
                    event.getTypeOfEvent(),
                    truncate(event.getEventDescription(), 40),
                    event.getSeatsAvailable(),
                    event.getTotalSeats(),
                    event.getDurationHours() + "h " + event.getDurationMinutes() + "m",
                    event.isBlocked() ? "Yes" : "No",
                    event.getEventStatus() // Adding the event status
                );
                System.out.println();
                System.out.println(DIVIDER_LINE);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while printing event: " + event);
            }
        }
        System.out.println(BOX_BORDER); // Close the box border after listing all events
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

    // Utility method to truncate long text fields
    private static String truncate(String value, int length) {
        if (value == null || value.length() <= length) {
            return value != null ? value : ""; // Return value or empty if null
        } else {
            return value.substring(0, length) + "...";
        }
    }
}
