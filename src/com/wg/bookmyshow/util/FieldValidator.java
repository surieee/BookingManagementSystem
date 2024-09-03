package com.wg.bookmyshow.util;

import java.util.regex.Pattern;

public class FieldValidator {

    // Validate username (non-empty and alphanumeric with length between 3 and 20)
    public static boolean validateUsername(String username) {
        String usernameRegex = "^[a-zA-Z0-9_]{3,20}$";
        if (username == null || username.trim().isEmpty() || !username.matches(usernameRegex)) {
            throw new ValidationException("Invalid username: must be alphanumeric with length between 3 and 20.");
        }
        return true;
    }

    // Validate password (minimum 8 characters, at least one uppercase, one lowercase, one digit)
    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Invalid password: must be at least 8 characters long.");
        }
     
        if (!Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-\\[\\]{}|;:'\",.<>/?]).{8,}$").matcher(password).matches()) {
            throw new ValidationException("Invalid password: must contain at least one uppercase letter, one lowercase letter, and one digit.");
        }
        return true;
    }

    // Validate email
    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (email == null || !email.matches(emailRegex)) {
            throw new ValidationException("Invalid email format.");
        }
        return true;
    }

    // Validate phone number (digits only, length between 10 and 12)
    public static boolean validatePhoneNumber(String phoneNumber) {
        String phoneNumberRegex = "^\\d{10,12}$";
        if (phoneNumber == null || !phoneNumber.matches(phoneNumberRegex)) {
            throw new ValidationException("Invalid phone number: must be between 10 and 12 digits.");
        }
        return true;
    }

    // Validate role (should be one of the specified values)
    public static boolean validateRole(String role) {
        String roleRegex = "^(admin|user|organizer)$";
        if (role == null || !role.matches(roleRegex)) {
            throw new ValidationException("Invalid role: must be 'admin', 'user', or 'organizer'.");
        }
        return true;
    }

    // Validate age (positive integer)
    public static boolean validateAge(int age) {
        if (age <= 0 || age>100) {
            throw new ValidationException("Invalid age: must be a positive integer.");
        }
        return true;
    }

    // Validate name (non-empty and alphabetic with optional spaces)
    public static boolean validateName(String name) {
        String nameRegex = "^[a-zA-Z\\s]+$";
        if (name == null || name.trim().isEmpty() || !name.matches(nameRegex)) {
            throw new ValidationException("Invalid name: must contain only alphabetic characters and spaces.");
        }
        return true;
    }
}
