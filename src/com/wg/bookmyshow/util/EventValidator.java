package com.wg.bookmyshow.util;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class EventValidator {

    // Method to validate if a string is not empty
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Method to validate if a number is positive
    public static boolean isPositiveNumber(double number) {
        return number > 0;
    }

    // Method to validate if an integer is positive
    public static boolean isPositiveInteger(int number) {
        return number > 0;
    }

//     Method to validate the date format and check if it's in the future
//    public static boolean isFutureDate(String dateStr) {
//        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormatter.setLenient(false);
//        try {
//            Date date = dateFormatter.parse(dateStr);
//            return date.after(new Date());
//        } catch (ParseException e) {
//            return false;
//        }
//    }
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        DATE_TIME_FORMAT.setLenient(false); // Strict date parsing
    }
//
//    // Method to check if the date-time is not in the future
//    public static void isFutureDate(String dateTimeStr) throws FutureDateException, ParseException {
//        Date inputDate = DATE_TIME_FORMAT.parse(dateTimeStr); // Parse input date
//        Timestamp inputTimestamp = new Timestamp(inputDate.getTime());
//        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//
//        // Compare input timestamp with current timestamp
//        if (inputTimestamp.after(currentTimestamp)) {
//            throw new FutureDateException("The event date and time cannot be in the future.");
//        }
//    }
//    public static void isFutureDate(String dateStr) throws FutureDateException, ParseException {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        formatter.setLenient(false); // Ensures strict parsing
//
//        Date inputDate = formatter.parse(dateStr);
//        Date currentDate = new Date();
//
//        // Compare input date with current date
//        if (inputDate.after(currentDate)) {
//            throw new FutureDateException("The event date cannot be in the future.");
//        }
//    }
    public static boolean isFutureDate(String dateStr) throws FutureDateException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setLenient(false);
        try {
            Date date = dateFormatter.parse(dateStr);
            if (date.after(new Date())) {
                return true;
            } else {
                throw new FutureDateException("The date " + dateStr + " is not in the future.");
            }
        } catch (ParseException e) {
            throw new FutureDateException("Invalid date format: " + dateStr);
        }
    }

    // Method to validate the time format
    public static boolean isValidTime(String timeStr) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        timeFormatter.setLenient(false);
        try {
            timeFormatter.parse(timeStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Method to validate event type
    public static boolean isValidEventType(String eventType) {
        String[] validTypes = {"concert", "conference", "meetup", "workshop", "stand-up comedy", "seminar"};
        for (String type : validTypes) {
            if (type.equalsIgnoreCase(eventType)) {
                return true;
            }
        }
        return false;
    }
    public static void validateSeatCounts(int seatsAvailable, int totalSeats) throws InvalidSeatCountException {
        if (seatsAvailable > totalSeats) {
            throw new InvalidSeatCountException("Number of available seats cannot be more than total seats.");
        }
    }

    
}
