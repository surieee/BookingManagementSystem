package com.wg.bookmyshow.model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.wg.bookmyshow.controller.AccountController;

public class BookingModel {
	AccountController accountController;
	
	private String bookingId;
    private Timestamp bookingDate;
    private double totalAmount;
    private String bookingStatus; // 'pending', 'confirmed', 'canceled'
    private String eventId;
    private String paymentId;
    private String ticketId;
    private String userId;
    private String eventStatus; // 'available for booking', 'cancelled', 'completely booked', 'upcoming:not available currently'

    public BookingModel() throws SQLException {
    	this.accountController=new AccountController();
        this.bookingId = UUID.randomUUID().toString(); // Generate unique booking ID
        this.bookingDate = new Timestamp(new Date().getTime()); // Default booking date to current timestamp
        this.totalAmount = 0.0; // Default total amount
     
        this.eventId = ""; // Default event ID
        this.paymentId = ""; // Default payment ID
        this.ticketId = ""; // Default ticket ID
        this.userId = ""; // Default user ID
    
        // Default event status
    }
    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }


    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

   
    

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getEventStatus() {
        return eventStatus;
    }

    
    
   
}
