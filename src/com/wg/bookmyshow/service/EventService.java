package com.wg.bookmyshow.service;

import com.wg.bookmyshow.dao.EventDao;
import com.wg.bookmyshow.model.EventModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EventService {

    private final EventDao eventDao;

    public EventService() throws SQLException {
        this.eventDao = new EventDao();
    }

    // Method to create a new event
    public boolean createEvent(EventModel event) throws ClassNotFoundException {
        return eventDao.insertEvent(event);
    }

    // Method to update an existing event
    public boolean updateEvent(EventModel event) throws ClassNotFoundException {
        return eventDao.updateEvent(event);
    }

    // Method to delete an event by name
    public boolean deleteEvent(String eventName) throws SQLException, ClassNotFoundException {
        return eventDao.deleteEvent(eventName);
    }

    // Method to get all events
    public List<EventModel> getAllEvents() throws ClassNotFoundException {
        return eventDao.getAllEvents();
    }

    // Method to get an event by its ID
    public EventModel getEventById(String eventId) throws ClassNotFoundException {
        return eventDao.getEventById(eventId);
    }

    // Method to get an event by its name
    public EventModel getEventByName(String eventName) throws ClassNotFoundException {
        return eventDao.getEventByName(eventName);
    }

    // Method to get events by organizer's username
    public List<EventModel> getEventsByOrganizerUsername(String username) throws SQLException, ClassNotFoundException {
        return eventDao.findEventsByOrganizerUsername(username);
    }

    // Method to search for events based on various filters
    public List<EventModel> searchEvents(Map<String, Object> filters) {
        try {
            // Check for blocked status and event status in filters and handle accordingly
//            if (!filters.containsKey("blocked")) {
//                filters.put("blocked", false); // Default to not blocked if not specified
////            }
//            if (!filters.containsKey("event_status")) {
//                filters.put("event_status", "Scheduled"); // Default to 'Scheduled' if not specified
//            }
            return eventDao.searchEvents(filters);
        } catch (Exception e) {
            System.err.println("An error occurred while searching events: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean blockEvent(String eventName) throws SQLException, ClassNotFoundException {
        // Retrieve the event by name
        EventModel event = eventDao.getEventByName(eventName);
        if (event == null) {
            System.out.println("Event not found.");
            return false;
        }

        // Update the event status to blocked
        event.setBlocked(true);
        event.setEventStatus("Cancelled"); // Assuming blocking the event means canceling it

        // Update the event in the database
        return eventDao.updateEvent(event);
    }

    public boolean approveEvent(String eventName) throws SQLException, ClassNotFoundException {
        // Retrieve the event by name
        EventModel event = eventDao.getEventByName(eventName);
        if (event == null) {
            System.out.println("Event not found.");
            return false;
        }

        // Update the event status to approved
        event.setBlocked(false);
        event.setEventStatus("Scheduled"); // Assuming approving the event means scheduling it

        // Update the event in the database
        return eventDao.updateEvent(event);
    }

    public List<EventModel> getBlockedEvents() {
        try {
            return eventDao.getBlockedEvents();
        } catch (SQLException e) {
            e.printStackTrace();
            // You can add logging here
            return null;
        }
    }

   
    public List<EventModel> viewMyEvents(String organizerId) {
        try {
            return eventDao.viewMyEvents(organizerId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                eventDao.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<EventModel> viewCancelledEvents() {
        try {
            return eventDao.viewCancelledEvents();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            return null;
        }
    }
    public boolean cancelEvent(String eventId) {
        try {
            return eventDao.cancelEvent(eventId);
        } catch (SQLException e) {
            e.printStackTrace();
            // You can add logging here
            return false;
        }
    }
   
	 public int getCountOfBlockedEvents() throws SQLException {
	        return eventDao.countBlockedEvents();
	    }
}





