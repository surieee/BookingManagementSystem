package com.wg.bookmyshow.util;

import com.wg.bookmyshow.model.PaymentHistoryModel;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentHistoryPrinter {

    // ANSI escape codes for colors (optional, for enhancing console readability)
    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";

    // Define the format for headers and rows
    private static final DecimalFormat AMOUNT_FORMATTER = new DecimalFormat("0.00");

    public static void printPaymentHistory(PaymentHistoryModel paymentHistory) {
        System.out.println(GREEN + "----------------------------------" + RESET);
        System.out.println(centerText("PAYMENT RECEIPT"));
        System.out.println(GREEN + "----------------------------------" + RESET);

        // Print details in a receipt-like format
        printLine("Booking Date", paymentHistory.getBookingDate().toString());
       // printLine("Booking Status", paymentHistory.getBookingStatus());
        printLine("Event Name", paymentHistory.getEventName());
        printLine("Venue", paymentHistory.getVenue());
        printLine("Event Date", paymentHistory.getEventDate().toString());
        printLine("Username", MaskId.maskId(paymentHistory.getUsername()));
        printLine("Email", MaskId.maskId(paymentHistory.getEmail()));
        printLine("Payment ID", MaskId.maskId(paymentHistory.getPaymentId()));
        printLine("Bill Amount", "$" + AMOUNT_FORMATTER.format(paymentHistory.getBillAmount()));
        printLine("Payment Status", paymentHistory.getPaymentStatus());
        printLine("Ticket ID", paymentHistory.getTicketId());
        printLine("Seat Number", paymentHistory.getSeatNumber());
        printLine("Ticket Status", paymentHistory.getTicketStatus());

        System.out.println(GREEN + "----------------------------------\n" + RESET);
    }

    public static void printAllPaymentHistories(List<PaymentHistoryModel> paymentHistories) {
        System.out.println(GREEN + "======== ALL PAYMENT RECEIPTS ========" + RESET);
        for (PaymentHistoryModel paymentHistory : paymentHistories) {
            printPaymentHistory(paymentHistory);
        }
        System.out.println(GREEN + "======================================\n" + RESET);
    }

    // Helper method to print a single line in receipt format
    private static void printLine(String label, String value) {
        System.out.printf("%-15s: %s\n", label, value);
    }

    // Helper method to center text (for title)
    private static String centerText(String text) {
        int width = 34; // Width of the receipt
        int textLength = text.length();
        int padding = (width - textLength) / 2;

        return " ".repeat(padding) + text;
    }
}

