package com.wg.bookmyshow.model;

import java.sql.Timestamp;

public class NotificationDetails {
    private String notificationId;
    private String notificationMessage;
    private String notificationPriority;
    private String notifiedUserId;
    private String eventName;
    private String venue;
    private Timestamp eventDate;
    private String typeOfEvent;
    private boolean isEventBlocked;
    private String eventStatus;

    // Getters and Setters
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getNotificationPriority() {
        return notificationPriority;
    }

    public void setNotificationPriority(String notificationPriority) {
        this.notificationPriority = notificationPriority;
    }

    public String getNotifiedUserId() {
        return notifiedUserId;
    }

    public void setNotifiedUserId(String notifiedUserId) {
        this.notifiedUserId = notifiedUserId;
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

    public boolean isEventBlocked() {
        return isEventBlocked;
    }

    public void setEventBlocked(boolean isEventBlocked) {
        this.isEventBlocked = isEventBlocked;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
}
