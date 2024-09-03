package com.wg.bookmyshow.model;

import java.util.UUID;

public class TicketModel {
    private String ticketId;
    private String seatNumber;
    private String ticketStatus;
    private String eventId;

    public TicketModel() {
        this.ticketId = UUID.randomUUID().toString(); // Generate unique ticket ID
        this.seatNumber = ""; // Default seat number
        this.ticketStatus = "available"; // Default ticket status
        this.eventId = ""; // Default event ID
    }
    // Getters and Setters
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}

