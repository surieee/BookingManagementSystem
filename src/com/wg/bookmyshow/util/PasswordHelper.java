
package com.wg.bookmyshow.util;
import org.mindrot.jbcrypt.BCrypt;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class PasswordHelper {

	 
	    public static String hashPassword(String password) {
	        return BCrypt.hashpw(password, BCrypt.gensalt());
	    }
	 
	    public static boolean checkPassword(String password, String hashedPassword) {
	        return BCrypt.checkpw(password, hashedPassword);
	    }

	  
	        public static String getPasswordFromUser() {
	            JPasswordField passwordField = new JPasswordField(20);
	            int option = JOptionPane.showConfirmDialog(null, passwordField, "Enter your password",
	                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	            if (option == JOptionPane.OK_OPTION) {
	                char[] passwordChars = passwordField.getPassword();
	                return new String(passwordChars);
	            } else {
	                return null; // User cancelled the operation
	            }
	        }
	    }

	 
