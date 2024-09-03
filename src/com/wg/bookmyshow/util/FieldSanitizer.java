package com.wg.bookmyshow.util;

public class FieldSanitizer {

    // Sanitize username (remove unwanted characters)
    public static String sanitizeUsername(String username) {
        if (username != null) {
            // Remove non-alphanumeric characters, keeping underscores
            return username.replaceAll("[^a-zA-Z0-9_]", "");
        }
        return "";
    }

    // Sanitize name (remove unwanted characters, allowing spaces)
    public static String sanitizeName(String name) {
        if (name != null) {
            // Remove non-alphabetic characters, allowing spaces
            return name.replaceAll("[^a-zA-Z\\s]", "");
        }
        return "";
    }

    // Sanitize email (basic sanitization, could be expanded)
    public static String sanitizeEmail(String email) {
        if (email != null) {
            // Remove characters not typically used in email addresses
            return email.replaceAll("[^a-zA-Z0-9_+&*-@.]", "");
        }
        return "";
    }

    // Sanitize phone number (remove non-digit characters)
    public static String sanitizePhoneNumber(String phoneNumber) {
        if (phoneNumber != null) {
            // Remove non-digit characters
            return phoneNumber.replaceAll("\\D", "");
        }
        return "";
    }

    // Sanitize role (lowercase and trim)
    public static String sanitizeRole(String role) {
        if (role != null) {
            // Convert to lowercase and remove extra spaces
            return role.trim().toLowerCase();
        }
        return "";
    }
}
