package com.wg.bookmyshow.model;
import java.sql.Timestamp;
import java.util.UUID;

public class EventModel {

    private String eventId;
    private String eventName;
    private String venue;
    private double price;
    private Timestamp eventDate; // Changed to Timestamp to include both date and time
    private String typeOfEvent;
    private String eventDescription;
    private int seatsAvailable;
    private int totalSeats;
    private String organizerId;
    private int durationHours;
    private int durationMinutes;
    private boolean blocked;
    private String eventStatus;

    public EventModel() {
        this.eventId = UUID.randomUUID().toString();
    }

    public EventModel(String eventName, String venue, double price, Timestamp eventDate, String typeOfEvent,
            String eventDescription, int seatsAvailable, int totalSeats, String organizerId,
            int durationHours, int durationMinutes, boolean blocked, String eventStatus) {
        this.eventId = UUID.randomUUID().toString();
        this.eventName = eventName;
        this.venue = venue;
        this.price = price;
        this.eventDate = eventDate;
        this.typeOfEvent = typeOfEvent;
        this.eventDescription = eventDescription;
        this.seatsAvailable = seatsAvailable;
        this.totalSeats = totalSeats;
        this.organizerId = organizerId;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;
        this.blocked = blocked;
        this.eventStatus = eventStatus;
    }

    // Getters and setters...

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getEventDate() {
        return eventDate;
    }

    public void setEventDate(Timestamp eventDate) {
        this.eventDate = eventDate;
    }

    public String getTypeOfEvent() {
        return typeOfEvent;
    }

    public void setTypeOfEvent(String typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
}





