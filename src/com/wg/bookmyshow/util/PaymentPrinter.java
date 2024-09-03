package com.wg.bookmyshow.util;

import com.wg.bookmyshow.model.PaymentModel;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentPrinter {

    // Define the format for headers and rows
    private static final String HEADER_FORMAT = "%-20s | %-15s | %-20s | %-15s";
    private static final String ROW_FORMAT = "%-20s | %-15s | %-20s | %-15s";
    private static final DecimalFormat AMOUNT_FORMATTER = new DecimalFormat("0.00");
    private static final String BOX_BORDER = "================================================================================";
    private static final String DIVIDER_LINE = "--------------------------------------------------------------------------------";

    public static void printPayments(List<PaymentModel> payments) {
        // Print table title
        System.out.println(BOX_BORDER);
        System.out.println(centerTextInBox("PAYMENT DETAILS"));
        System.out.println(BOX_BORDER);

        // Print header
        System.out.printf(HEADER_FORMAT, "Payment ID", "Bill Amount", "Date of Payment", "Status");
        System.out.println();
        System.out.println(DIVIDER_LINE);

        // Print rows
        for (PaymentModel payment : payments) {
            try {
                // Print each payment row with masked Payment ID
                System.out.printf(ROW_FORMAT,
                    MaskId.maskId(payment.getPaymentId()),
                    AMOUNT_FORMATTER.format(payment.getBillAmount()), // Format bill amount
                    payment.getDateOfPayment() != null ? payment.getDateOfPayment().toString() : "N/A",
                    payment.getPaymentStatus()
                );
                System.out.println();
                System.out.println(DIVIDER_LINE);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while printing payment: " + payment);
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

    // Helper method to mask the Payment ID, showing only the last 4 digits


}
