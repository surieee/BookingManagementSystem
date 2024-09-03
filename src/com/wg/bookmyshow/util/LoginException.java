 package com.wg.bookmyshow.util;

public class LoginException extends RuntimeException {
	public LoginException() {
        super("User is not authenticated.");
    }
	public LoginException(String message) {
        super(message);
    }
}
