package com.wg.bookmyshow.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHelper2 {

    // Hash the password without salting
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPasswordBytes = md.digest(password.getBytes()); // Hash the password
        return Base64.getEncoder().encodeToString(hashedPasswordBytes); // Encode hashed password to Base64
    }

    // Check if the given password matches the stored hash
    public static boolean checkPassword(String password, String storedHash) throws NoSuchAlgorithmException {
        // Hash the input password
        String hashedPassword = hashPassword(password);
        // Compare hashed passwords
        return hashedPassword.equals(storedHash);
    }
}