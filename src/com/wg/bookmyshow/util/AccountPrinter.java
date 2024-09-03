package com.wg.bookmyshow.util;

import com.wg.bookmyshow.model.AccountModel;

import java.util.List;

public class AccountPrinter {

    // Define the format for headers and rows
    private static final String HEADER_FORMAT = "%-30s | %-25s | %-5s | %-15s | %-15s | %-35s | %-15s | %-10s";
    private static final String ROW_FORMAT = "%-30s | %-25s | %-5d | %-15s | %-15s | %-35s | %-15s | %-10s";
    private static final String BOX_BORDER = "===============================================================================================================================================================================";
    private static final String DIVIDER_LINE = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------1";

    public static void printAccounts(List<AccountModel> accounts) {
        // Print table title
        System.out.println(BOX_BORDER);
        System.out.println(centerTextInBox("ACCOUNT DETAILS"));
        System.out.println(BOX_BORDER);

        // Print header
        System.out.printf(HEADER_FORMAT, "Username", "Name", "Age", "Role", "Phone Number", "Email", "Blocked", "Password");
        System.out.println();
        System.out.println(DIVIDER_LINE);

        // Print rows
        for (AccountModel account : accounts) {
            try {
                // Print each account row
                System.out.printf(ROW_FORMAT,
                    account.getUsername(),
                    account.getName(),
                    account.getAge(),
                    account.getRole(),
                    account.getPhoneNumber(),
                    account.getEmail(),
                    account.isBlocked() ? "Yes" : "No", // Blocked status as Yes/No
                    maskPassword(account.getPassword()) // Masking password
                );
                System.out.println();
                System.out.println(DIVIDER_LINE);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while printing account: " + account);
            }
        }
        System.out.println(BOX_BORDER); // Close the box border after listing all accounts
    }

    public static void printAccount(AccountModel account) {
        if (account == null) {
            System.out.println("No account details to display.");
            return;
        }

        // Print table title
        System.out.println(BOX_BORDER);
        System.out.println(centerTextInBox("ACCOUNT DETAIL"));
        System.out.println(BOX_BORDER);

        // Print header
        System.out.printf(HEADER_FORMAT, "Username", "Account Name", "Age", "Role", "Phone Number", "Email", "Blocked", "Password");
        System.out.println();
        System.out.println(DIVIDER_LINE);

        try {
            // Print the account row
            System.out.printf(ROW_FORMAT,
                account.getUsername(),
                account.getName(),
                account.getAge(),
                account.getRole(),
                account.getPhoneNumber(),
                account.getEmail(),
                account.isBlocked() ? "Yes" : "No", // Blocked status as Yes/No
                maskPassword(account.getPassword()) // Masking password
            );
            System.out.println();
            System.out.println(DIVIDER_LINE);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while printing account: " + account);
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

    // Utility method to mask passwords (e.g., replace with asterisks)
    private static String maskPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "*****"; // Default mask for null or empty passwords
        }
        return "*****"; // Mask password with asterisks
    }
}


