package com.wg.bookmyshow.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.wg.bookmyshow.model.AccountModel;
import com.wg.bookmyshow.model.EventModel;
import com.wg.bookmyshow.service.EventService;
import com.wg.bookmyshow.util.EventPrinter;
import com.wg.bookmyshow.util.EventValidator;
import com.wg.bookmyshow.util.FutureDateException;
import com.wg.bookmyshow.util.InvalidSeatCountException;

    public class EventController {

    	
        private final EventService eventService;
        private final Scanner scanner = new Scanner(System.in);

        public EventController() throws SQLException {
            this.eventService = new EventService();
        }
        private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

        public void createEvent() throws SQLException, ClassNotFoundException {
            // Get the current account ID from the login session
            String organizerId = AccountModel.getAccountId(); // Assuming AccountModel has a static getAccountId method

            // Continue with event creation process
            System.out.print("Enter event name: ");
            String eventName = scanner.nextLine().trim();
            if (!EventValidator.isNotEmpty(eventName)) {
                System.out.println("Event name cannot be empty.");
                return;
            }

            System.out.print("Enter venue: ");
            String venue = scanner.nextLine().trim();
            if (!EventValidator.isNotEmpty(venue)) {
                System.out.println("Venue cannot be empty.");
                return;
            }

            System.out.print("Enter price: ");
            double price;
            try {
                price = scanner.nextDouble();
                if (!EventValidator.isPositiveNumber(price)) {
                    System.out.println("Price must be a positive number.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid input for price. Please enter a number.");
                scanner.nextLine(); // consume invalid input
                return;
            }
            scanner.nextLine(); // consume the remaining newline

//            System.out.print("Enter event date and time (yyyy-MM-dd HH:mm:ss): ");
//            String eventDateString = scanner.nextLine();
//            Timestamp eventDateSql;
//            
//            try {
//                java.util.Date eventDateUtil = DATE_TIME_FORMAT.parse(eventDateString);
//                eventDateSql = new Timestamp(eventDateUtil.getTime());
//            } catch (ParseException e) {
//                System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm:ss.");
//                return;
//            }

            System.out.print("Enter event date and time (yyyy-MM-dd HH:mm:ss): ");
            String eventDateString = scanner.nextLine().trim();
            Timestamp eventDateSql = null;

            try {
                // Validate and parse the event date and time
            	EventValidator.isFutureDate(eventDateString);
                java.util.Date eventDateUtil = EventValidator.DATE_TIME_FORMAT.parse(eventDateString);
                eventDateSql = new Timestamp(eventDateUtil.getTime());
                
                // If validation and parsing are successful, continue with event creation logic
              //  System.out.println("Event date and time are valid.");
                // Proceed with event creation or other logic
                
            } catch (FutureDateException e) {
                // Handle future date exception
                System.out.println("Error: " + e.getMessage());
                // Optionally, log the exception if you have a logger
                // logger.log(Level.WARNING, "Future date-time input: {0}", e.getMessage());
            } catch (ParseException e) {
                // Handle invalid date format
                System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm:ss.");
                // Optionally, log the exception if you have a logger
                // logger.log(Level.WARNING, "Invalid date format input: {0}", e.getMessage());
            }
            System.out.print("Enter type of event (concert/conference/meetup/workshop/stand-up comedy/seminar): ");
            String typeOfEvent = scanner.nextLine().trim();
            if (!EventValidator.isValidEventType(typeOfEvent)) {
                System.out.println("Invalid event type. Please choose from the given options.");
                return;
            }

            System.out.print("Enter event description: ");
            String eventDescription = scanner.nextLine().trim();
            if (!EventValidator.isNotEmpty(eventDescription)) {
                System.out.println("Event description cannot be empty.");
                return;
            }

            System.out.print("Enter number of seats available: ");
            int seatsAvailable;
            try {
                seatsAvailable = scanner.nextInt();
                if (!EventValidator.isPositiveInteger(seatsAvailable)) {
                    System.out.println("Number of seats available must be a positive integer.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid input for seats available. Please enter a number.");
                scanner.nextLine(); // consume invalid input
                return;
            }
            scanner.nextLine(); // consume the remaining newline

            System.out.print("Enter total number of seats: ");
            int totalSeats;
            try {
                totalSeats = scanner.nextInt();
                if (!EventValidator.isPositiveInteger(totalSeats)) {
                    System.out.println("Total number of seats must be a positive integer.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid input for total seats. Please enter a number.");
                scanner.nextLine(); // consume invalid input
                return;
            }
            scanner.nextLine(); // consume the remaining newline

            // Validate seat counts using custom exception
            try {
                EventValidator.validateSeatCounts(seatsAvailable, totalSeats);
                System.out.println("Seat counts are valid. Proceeding with event creation...");
                // Proceed with further processing...
            } catch (InvalidSeatCountException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine(); // consume the remaining newline

            System.out.print("Enter total number of seats: ");
         
            try {
                totalSeats = scanner.nextInt();
                if (!EventValidator.isPositiveInteger(totalSeats)) {
                    System.out.println("Total number of seats must be a positive integer.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid input for total seats. Please enter a number.");
                scanner.nextLine(); // consume invalid input
                return;
            }
            scanner.nextLine(); // consume the remaining newline

            System.out.print("Enter duration in hours: ");
            int durationHours;
            try {
                durationHours = scanner.nextInt();
                if (!EventValidator.isPositiveInteger(durationHours)) {
                    System.out.println("Duration in hours must be a positive integer.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid input for duration in hours. Please enter a number.");
                scanner.nextLine(); // consume invalid input
                return;
            }
            scanner.nextLine(); // consume the remaining newline

            System.out.print("Enter duration in minutes: ");
            int durationMinutes;
            try {
                durationMinutes = scanner.nextInt();
                if (!EventValidator.isPositiveInteger(durationMinutes)) {
                    System.out.println("Duration in minutes must be a positive integer.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid input for duration in minutes. Please enter a number.");
                scanner.nextLine(); // consume invalid input
                return;
            }
            scanner.nextLine(); // consume the remaining newline

            // Create and populate the EventModel instance
            EventModel event = new EventModel();
            event.setEventName(eventName);
            event.setVenue(venue);
            event.setPrice(price);
            event.setEventDate(eventDateSql); // Set Timestamp
            event.setTypeOfEvent(typeOfEvent);
            event.setEventDescription(eventDescription);
            event.setSeatsAvailable(seatsAvailable);
            event.setTotalSeats(totalSeats);
            event.setDurationHours(durationHours);
            event.setDurationMinutes(durationMinutes);
            event.setOrganizerId(organizerId); // Set organizer ID
            event.setBlocked(true); // Set blocked to true by default
            event.setEventStatus("Scheduled"); // Set event status to "Scheduled"

            // Call the service to create the event
            if (eventService.createEvent(event)) {
                System.out.println("Event created successfully.");
            } else {
                System.out.println("Failed to create event.");
            }
        }
    

    // Method to create a new event

//    public void createEvent() throws SQLException, ClassNotFoundException {
//        // Get the current account ID from the login session
//        String organizerId = AccountModel.getAccountId(); 
//
//        // Continue with event creation process
//        System.out.print("Enter event name: ");
//        String eventName = scanner.nextLine().trim();
//        System.out.print("Enter venue: ");
//        String venue = scanner.nextLine().trim();
//        System.out.print("Enter price: ");
//        
//        double price = 0.0;
//        try {
//            price = scanner.nextDouble();
//        } catch (Exception e) {
//            System.out.println("Invalid input for price. Please enter a number.");
//            scanner.nextLine(); // consume invalid input
//            return;
//        }
//        
//        scanner.nextLine(); // consume the remaining newline
//        System.out.print("Enter event date (yyyy-MM-dd): ");
//        String dateInput = scanner.nextLine().trim();
//        System.out.print("Enter type of event (concert/conference/meetup/workshop/stand-up comedy/seminar): ");
//        String typeOfEvent = scanner.nextLine().trim();
//        System.out.print("Enter event description: ");
//        String eventDescription = scanner.nextLine().trim();
//        System.out.print("Enter number of seats available: ");
//        int seatsAvailable = 0;
//        try {
//            seatsAvailable = scanner.nextInt();
//        } catch (Exception e) {
//            System.out.println("Invalid input for seats available. Please enter a number.");
//            scanner.nextLine(); // consume invalid input
//            return;
//        }
//        
//        scanner.nextLine(); // consume the remaining newline
//        System.out.print("Enter total number of seats: ");
//        int totalSeats = 0;
//        try {
//            totalSeats = scanner.nextInt();
//        } catch (Exception e) {
//            System.out.println("Invalid input for total seats. Please enter a number.");
//            scanner.nextLine(); // consume invalid input
//            return;
//        }
//        
//        scanner.nextLine(); // consume the remaining newline
//        System.out.print("Enter duration in hours: ");
//        int durationHours = 0;
//        try {
//            durationHours = scanner.nextInt();
//        } catch (Exception e) {
//            System.out.println("Invalid input for duration in hours. Please enter a number.");
//            scanner.nextLine(); // consume invalid input
//            return;
//        }
//        
//        scanner.nextLine(); // consume the remaining newline
//        System.out.print("Enter duration in minutes: ");
//        int durationMinutes = 0;
//        try {
//            durationMinutes = scanner.nextInt();
//        } catch (Exception e) {
//            System.out.println("Invalid input for duration in minutes. Please enter a number.");
//            scanner.nextLine(); // consume invalid input
//            return;
//        }
//        
//        scanner.nextLine(); // consume the remaining newline
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date eventDate = formatter.parse(dateInput);
//            boolean blocked = true; // Set blocked to true by default
//            String eventStatus = "Scheduled"; // Set event status to "Scheduled"
//            EventModel event = new EventModel(eventName, venue, price, eventDate, typeOfEvent, 
//                                              eventDescription, seatsAvailable, totalSeats,
//                                              durationHours, durationMinutes, blocked, eventStatus,startTime); 
//            event.setOrganizerId(organizerId); // Set organizer ID
//
//            if (eventService.createEvent(event)) {
//                System.out.println("Event created successfully.");
//            } else {
//                System.out.println("Failed to create event.");
//            }
//        } catch (ParseException e) {
//            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
//        }
//    }
//        public void updateEvent() throws SQLException, ClassNotFoundException {
//            System.out.print("Enter the name of the event to update: ");
//            String eventName = scanner.nextLine().trim();
//
//            EventModel event = eventService.getEventByName(eventName);
//            if (event == null) {
//                System.out.println("Event not found. Please check the event name and try again.");
//                return;
//            }
//
//            boolean updating = true;
//            while (updating) {
//                System.out.println("\nWhich field would you like to update?");
//                System.out.println("1. Venue");
//                System.out.println("2. Price");
//                System.out.println("3. Date");
//                System.out.println("4. Type of Event");
//                System.out.println("5. Description");
//                System.out.println("6. Seats Available");
//                System.out.println("7. Total Seats");
//                System.out.println("8. Duration (Hours)");
//                System.out.println("9. Duration (Minutes)");
//                System.out.println("10. Event Status");
//                System.out.println("0. Exit");
//                System.out.print("Select an option (0-11): ");
//
//                int choice = -1;
//                try {
//                    choice = scanner.nextInt();
//                } catch (Exception e) {
//                    System.out.println("Invalid choice. Please select a valid option.");
//                    scanner.nextLine(); // consume invalid input
//                    continue;
//                }
//
//                scanner.nextLine(); // consume the remaining newline
//
//                switch (choice) {
//                    case 1:
//                        System.out.print("Update venue: ");
//                        String venue = scanner.nextLine().trim();
//                        event.setVenue(venue);
//                        break;
//                    case 2:
//                        System.out.print("Update price: ");
//                        double price = scanner.nextDouble();
//                        scanner.nextLine(); // consume the remaining newline
//                        event.setPrice(price);
//                        break;
//                    case 3:
//                        System.out.print("Update event date and time (yyyy-MM-dd HH:mm:ss): ");
//                        String dateTimeInput = scanner.nextLine().trim();
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        try {
//                            java.util.Date date = formatter.parse(dateTimeInput);
//                            Timestamp timestamp = new Timestamp(date.getTime());
//                            event.setEventDate(timestamp); // Use Timestamp instead of Date
//                        } catch (ParseException e) {
//                            System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm:ss.");
//                        }
//                        break;
//                    case 4:
//                        System.out.print("Update type of event: ");
//                        String typeOfEvent = scanner.nextLine().trim();
//                        event.setTypeOfEvent(typeOfEvent);
//                        break;
//                    case 5:
//                        System.out.print("Update event description: ");
//                        String eventDescription = scanner.nextLine().trim();
//                        event.setEventDescription(eventDescription);
//                        break;
//                    case 6:
//                        System.out.print("Update number of seats available: ");
//                        int seatsAvailable = scanner.nextInt();
//                        scanner.nextLine(); // consume the remaining newline
//                        event.setSeatsAvailable(seatsAvailable);
//                        break;
//                    case 7:
//                        System.out.print("Update total number of seats: ");
//                        int totalSeats = scanner.nextInt();
//                        scanner.nextLine(); // consume the remaining newline
//                        event.setTotalSeats(totalSeats);
//                        break;
//                    case 8:
//                        System.out.print("Update duration in hours: ");
//                        int durationHours = scanner.nextInt();
//                        scanner.nextLine(); // consume the remaining newline
//                        event.setDurationHours(durationHours);
//                        break;
//                    case 9:
//                        System.out.print("Update duration in minutes: ");
//                        int durationMinutes = scanner.nextInt();
//                        scanner.nextLine(); // consume the remaining newline
//                        event.setDurationMinutes(durationMinutes);
//                        break;
////                    case 10:
////                        System.out.print("Update blocked status (true/false): ");
////                        boolean blocked = scanner.nextBoolean();
////                        scanner.nextLine(); // consume the remaining newline
////                        event.setBlocked(blocked);
////                        break;
//                    case 10:
//                        System.out.print("Update event status (scheduled/cancelled/completed): ");
//                        String status = scanner.nextLine().trim();
//                        if (status.equals("scheduled") || status.equals("cancelled") || status.equals("completed")) {
//                            event.setEventStatus(status);
//                        } else {
//                            System.out.println("Invalid status. Please enter 'scheduled', 'cancelled', or 'completed'.");
//                        }
//                        break;
//                    case 0:
//                        updating = false;
//                        break;
//                    default:
//                        System.out.println("Invalid choice. Please select a valid option.");
//                }
//
//                if (updating) {
//                    System.out.print("Do you want to update another field? (yes/no): ");
//                    String continueChoice = scanner.nextLine().trim().toLowerCase();
//                    if (!continueChoice.equals("yes")) {
//                        updating = false;
//                    }
//                }
//            }
//
//            if (eventService.updateEvent(event)) {
//                System.out.println("Event updated successfully.");
//            } else {
//                System.out.println("Failed to update event.");
//            }
//        }
            
        public void updateEvent() throws SQLException, ClassNotFoundException {
            System.out.print("Enter the name of the event to update: ");
            String eventName = scanner.nextLine().trim();

            EventModel event = eventService.getEventByName(eventName);
            if (event == null) {
                System.out.println("Event not found. Please check the event name and try again.");
                return;
            }

            boolean updating = true;
            while (updating) {
                System.out.println("\nWhich field would you like to update?");
                System.out.println("1. Venue");
                System.out.println("2. Price");
                System.out.println("3. Date");
                System.out.println("4. Type of Event");
                System.out.println("5. Description");
                System.out.println("6. Seats Available");
                System.out.println("7. Total Seats");
                System.out.println("8. Duration (Hours)");
                System.out.println("9. Duration (Minutes)");
                System.out.println("10. Event Status");
                System.out.println("0. Exit");
                System.out.print("Select an option (0-10): ");

                int choice = -1;
                try {
                    choice = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid choice. Please select a valid option.");
                    scanner.nextLine(); // consume invalid input
                    continue;
                }

                scanner.nextLine(); // consume the remaining newline

                switch (choice) {
                    case 1:
                        System.out.print("Update venue: ");
                        String venue = scanner.nextLine().trim();
                        if (EventValidator.isNotEmpty(venue)) {
                            event.setVenue(venue);
                        } else {
                            System.out.println("Venue cannot be empty.");
                        }
                        break;
                    case 2:
                        System.out.print("Update price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine(); // consume the remaining newline
                        if (EventValidator.isPositiveNumber(price)) {
                            event.setPrice(price);
                        } else {
                            System.out.println("Price must be a positive number.");
                        }
                        break;
                    case 3:
                        System.out.print("Update event date and time (yyyy-MM-dd HH:mm:ss): ");
                        String dateTimeInput = scanner.nextLine().trim();
                        try {
                            EventValidator.DATE_TIME_FORMAT.parse(dateTimeInput);
                            java.util.Date date = EventValidator.DATE_TIME_FORMAT.parse(dateTimeInput);
                            Timestamp timestamp = new Timestamp(date.getTime());
                            if (timestamp.after(new Timestamp(System.currentTimeMillis()))) {
                                event.setEventDate(timestamp);
                            } else {
                                System.out.println("The date and time must be in the future.");
                            }
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm:ss.");
                        }
                        break;
                    case 4:
                        System.out.print("Update type of event: ");
                        String typeOfEvent = scanner.nextLine().trim();
                        if (EventValidator.isValidEventType(typeOfEvent)) {
                            event.setTypeOfEvent(typeOfEvent);
                        } else {
                            System.out.println("Invalid event type.");
                        }
                        break;
                    case 5:
                        System.out.print("Update event description: ");
                        String eventDescription = scanner.nextLine().trim();
                        if (EventValidator.isNotEmpty(eventDescription)) {
                            event.setEventDescription(eventDescription);
                        } else {
                            System.out.println("Event description cannot be empty.");
                        }
                        break;
                    case 6:
                        System.out.print("Update number of seats available: ");
                        int seatsAvailable = scanner.nextInt();
                        scanner.nextLine(); // consume the remaining newline
                        try {
                            EventValidator.validateSeatCounts(seatsAvailable, event.getTotalSeats());
                            event.setSeatsAvailable(seatsAvailable);
                        } catch (InvalidSeatCountException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 7:
                        System.out.print("Update total number of seats: ");
                        int totalSeats = scanner.nextInt();
                        scanner.nextLine(); // consume the remaining newline
                        try {
                            EventValidator.validateSeatCounts(event.getSeatsAvailable(), totalSeats);
                            event.setTotalSeats(totalSeats);
                        } catch (InvalidSeatCountException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 8:
                        System.out.print("Update duration in hours: ");
                        int durationHours = scanner.nextInt();
                        scanner.nextLine(); // consume the remaining newline
                        if (EventValidator.isPositiveInteger(durationHours)) {
                            event.setDurationHours(durationHours);
                        } else {
                            System.out.println("Duration hours must be a positive integer.");
                        }
                        break;
                    case 9:
                        System.out.print("Update duration in minutes: ");
                        int durationMinutes = scanner.nextInt();
                        scanner.nextLine(); // consume the remaining newline
                        if (EventValidator.isPositiveInteger(durationMinutes)) {
                            event.setDurationMinutes(durationMinutes);
                        } else {
                            System.out.println("Duration minutes must be a positive integer.");
                        }
                        break;
                    case 10:
                        System.out.print("Update event status (scheduled/cancelled/completed): ");
                        String status = scanner.nextLine().trim();
                        if (status.equals("scheduled") || status.equals("cancelled") || status.equals("completed")) {
                            event.setEventStatus(status);
                        } else {
                            System.out.println("Invalid status. Please enter 'scheduled', 'cancelled', or 'completed'.");
                        }
                        break;
                    case 0:
                        updating = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }

                if (updating) {
                    System.out.print("Do you want to update another field? (yes/no): ");
                    String continueChoice = scanner.nextLine().trim().toLowerCase();
                    if (!continueChoice.equals("yes")) {
                        updating = false;
                    }
                }
            }

            if (eventService.updateEvent(event)) {
                System.out.println("Event updated successfully.");
            } else {
                System.out.println("Failed to update event.");
            }
        }

//    public void viewEvents() throws ClassNotFoundException {
//        System.out.println("Viewing events...");
//        List<EventModel> events = eventService.getAllEvents();
//        if (events.isEmpty()) {
//            System.out.println("No events available.");
//        } else {
//            EventPrinter.printEvents(events);
//        }
//    }
        public List<EventModel> viewEvents() throws ClassNotFoundException {
            System.out.println("Viewing events...");
            List<EventModel> events = eventService.getAllEvents();
            if (events.isEmpty()) {
                System.out.println("No events available.");
            } else {
                EventPrinter.printEvents(events);
            }
            return events;
        }

   


    public void deleteEvent() throws SQLException, ClassNotFoundException {
        System.out.print("Enter the name of the event to delete: ");
        String eventName = scanner.nextLine().trim();

        if (eventService.deleteEvent(eventName)) {
            System.out.println("Event deleted successfully.");
        } else {
            System.out.println("Failed to delete event. Please check the event name and try again.");
        }
    }

    
    
	
    public void blockEvent() throws ClassNotFoundException {
        System.out.print("Enter the event name to block: ");
        String eventName = scanner.nextLine().trim();

        // Call service method to block the event
        try {
            boolean success = eventService.blockEvent(eventName);
            if (success) {
                System.out.println("Event blocked successfully.");
            } else {
                System.out.println("Failed to block the event.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while blocking the event: " + e.getMessage());
        }
    }


    // Method to approve (unblock) an event
    public void approveEvents() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the event name to approve: ");
        String eventName = scanner.nextLine();

        boolean success = eventService.approveEvent(eventName);
		if (success) {
		    System.out.println("Event " + eventName + " has been approved successfully.");
		} else {
		    System.out.println("Failed to approve the event. Please check the event name and try again.");
		}
    }

    // Method to view all blocked events
//    public void viewBlockedEvents() throws ClassNotFoundException {
//        List<EventModel> blockedEvents = eventService.getBlockedEvents();
//        if (blockedEvents.isEmpty()) {
//            System.out.println("No blocked events found.");
//        } else {
//        	EventPrinter.printEvents(blockedEvents);
//        }
//    }
    public List<EventModel> viewBlockedEvents() throws ClassNotFoundException {
        List<EventModel> blockedEvents = eventService.getBlockedEvents();
        if (blockedEvents.isEmpty()) {
            System.out.println("No blocked events found.");
        } else {
            EventPrinter.printEvents(blockedEvents);
        }
        return blockedEvents;
    }

    public void searchEvents() {
        Map<String, Object> searchCriteria = new HashMap<>();
        
        System.out.println("Enter search criteria (leave blank to skip a field):");
        
        System.out.print("Event Name: ");
        String eventName = scanner.nextLine().trim();
        if (!eventName.isEmpty()) searchCriteria.put("event_name", eventName);

        System.out.print("Venue: ");
        String venue = scanner.nextLine().trim();
        if (!venue.isEmpty()) searchCriteria.put("venue", venue);

        System.out.print("Price: ");
        String price = scanner.nextLine().trim();
        if (!price.isEmpty()) searchCriteria.put("price", Double.parseDouble(price));

        System.out.print("Event Date (yyyy-MM-dd): ");
        String date = scanner.nextLine().trim();
        if (!date.isEmpty()) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = formatter.parse(date);  // Parse input to java.util.Date
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  // Convert to java.sql.Date
                searchCriteria.put("event_date", sqlDate);  // Use java.sql.Date for database queries
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                return;  // Exit if the date format is incorrect
            }
        }

        System.out.print("Type of Event: ");
        String typeOfEvent = scanner.nextLine().trim();
        if (!typeOfEvent.isEmpty()) searchCriteria.put("type_of_event", typeOfEvent);

        System.out.print("Event Description: ");
        String eventDescription = scanner.nextLine().trim();
        if (!eventDescription.isEmpty()) searchCriteria.put("event_description", eventDescription);

//        System.out.print("Seats Available: ");
//        String seatsAvailable = scanner.nextLine().trim();
//        if (!seatsAvailable.isEmpty()) searchCriteria.put("seats_available", Integer.parseInt(seatsAvailable));
//
//        System.out.print("Total Seats: ");
//        String totalSeats = scanner.nextLine().trim();
//        if (!totalSeats.isEmpty()) searchCriteria.put("total_seats", Integer.parseInt(totalSeats));

        System.out.print("Blocked Status (true/false): ");
        String blockedStatus = scanner.nextLine().trim();
        if (!blockedStatus.isEmpty()) {
            searchCriteria.put("blocked", blockedStatus.equalsIgnoreCase("true"));
        }

        // Fetch events based on the search criteria
        List<EventModel> foundEvents = eventService.searchEvents(searchCriteria);
        if (foundEvents == null || foundEvents.isEmpty()) {
            System.out.println("No events match the search criteria.");
        } else {
            EventPrinter.printEvents(foundEvents);  // Print the details of the found events
        }
    }
   
//    public void searchEvents() throws ParseException {
//        Map<String, Object> searchCriteria = new HashMap<>();
//        
//        System.out.println("Enter search criteria (leave blank to skip a field):");
////        System.out.print("Event ID: ");
////        String eventId = scanner.nextLine().trim();
////        if (!eventId.isEmpty()) searchCriteria.put("event_id", eventId);
//
//        System.out.print("Event Name: ");
//        String eventName = scanner.nextLine().trim();
//        if (!eventName.isEmpty()) searchCriteria.put("event_name", eventName);
//
//        System.out.print("Venue: ");
//        String venue = scanner.nextLine().trim();
//        if (!venue.isEmpty()) searchCriteria.put("venue", venue);
//
//        System.out.print("Price: ");
//        String price = scanner.nextLine().trim();
//        if (!price.isEmpty()) searchCriteria.put("price", Double.parseDouble(price));
//
//        System.out.print("Event Date (yyyy-MM-dd): ");
//        String date = scanner.nextLine().trim();
//        if (!date.isEmpty()) {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        }
//
//        System.out.print("Type of Event: ");
//        String typeOfEvent = scanner.nextLine().trim();
//        if (!typeOfEvent.isEmpty()) searchCriteria.put("type_of_event", typeOfEvent);
//
//        System.out.print("Event Description: ");
//        String eventDescription = scanner.nextLine().trim();
//        if (!eventDescription.isEmpty()) searchCriteria.put("event_description", eventDescription);
//
//        System.out.print("Seats Available: ");
//        String seatsAvailable = scanner.nextLine().trim();
//        if (!seatsAvailable.isEmpty()) searchCriteria.put("seats_available", Integer.parseInt(seatsAvailable));
//
//        System.out.print("Total Seats: ");
//        String totalSeats = scanner.nextLine().trim();
//        if (!totalSeats.isEmpty()) searchCriteria.put("total_seats", Integer.parseInt(totalSeats));
////
////        System.out.print("Organizer ID: ");
////        String organizerId = scanner.nextLine().trim();
////        if (!organizerId.isEmpty()) searchCriteria.put("organizer_id", organizerId);
//
////        System.out.print("Duration Hours: ");
////        String durationHours = scanner.nextLine().trim();
////        if (!durationHours.isEmpty()) searchCriteria.put("duration_hours", Integer.parseInt(durationHours));
////
////        System.out.print("Duration Minutes: ");
////        String durationMinutes = scanner.nextLine().trim();
////        if (!durationMinutes.isEmpty()) searchCriteria.put("duration_minutes", Integer.parseInt(durationMinutes));
//
//        System.out.print("Blocked Status (true/false): ");
//        String blockedStatus = scanner.nextLine().trim();
//        if (!blockedStatus.isEmpty()) {
//            searchCriteria.put("blocked", blockedStatus.equalsIgnoreCase("true"));
//        }
//
//        List<EventModel> foundEvents = eventService.searchEvents(searchCriteria);
//        if (foundEvents.isEmpty()) {
//            System.out.println("No events match the search criteria.");
//        } else {
//            EventPrinter.printEvents(foundEvents);
//        }
//    }

//    public void viewMyEvents() {
//        
//        String organizerId = AccountController.loggedInAccountId;
//
//        List<EventModel> events = eventService.viewMyEvents(organizerId);
//
//        if (events == null || events.isEmpty()) {
//            System.out.println("No events found for the given organizer ID.");
//        } else {
//            System.out.println("Your Events:");
//            EventPrinter.printEvents(events);
//        }
//    }
    
    public List<EventModel> viewMyEvents() {
        String organizerId = AccountController.loggedInAccountId;
        List<EventModel> events = eventService.viewMyEvents(organizerId);

        if (events == null || events.isEmpty()) {
            System.out.println("No events found for the given organizer ID.");
        } else {
            System.out.println("Your Events:");
            EventPrinter.printEvents(events);
        }

        return events;  // Return the list of events to allow for further checks
    }

    public void viewCancelledEvents() {
        List<EventModel> cancelledEvents = eventService.viewCancelledEvents();
        if (cancelledEvents != null && !cancelledEvents.isEmpty()) {
            System.out.println("Cancelled Events:");
            for (EventModel event : cancelledEvents) {
                System.out.println(event.getEventName() + " at " + event.getVenue() + " on " + event.getEventDate());
            }
        } else {
            System.out.println("No cancelled events found.");
        }
    }
    public void cancelEvent() throws ClassNotFoundException {
        System.out.print("Enter the name of the event to cancel: ");
        String eventName = scanner.nextLine().trim();

        // Retrieve the event by its name (you could use ID instead if you prefer)
        EventModel event = eventService.getEventByName(eventName);
        if (event == null) {
            System.out.println("Event not found. Please check the event name and try again.");
            return;
        }

        // Update the event status to indicate cancellation
        event.setEventStatus("cancelled");

        // Update the event in the database
        try {
            if (eventService.updateEvent(event)) {
                System.out.println("Event canceled successfully.");
            } else {
                System.out.println("Failed to cancel the event.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("An error occurred while canceling the event: " + e.getMessage());
        }
    }

		
//    public void cancelEvent() {
//        System.out.print("Enter event ID to cancel: ");
//        String eventId = scanner.nextLine();
//
//        boolean result = eventService.cancelEvent(eventId);
//        if (result) {
//            System.out.println("Event cancelled successfully.");
//        } else {
//            System.out.println("Failed to cancel event. Please try again.");
//        }
//    }
}




