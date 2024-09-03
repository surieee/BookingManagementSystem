package com.wg.bookmyshow.controller;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.wg.bookmyshow.model.AccountModel;
import com.wg.bookmyshow.model.BookingModel;
import com.wg.bookmyshow.model.EventModel;
import com.wg.bookmyshow.service.BookingService;
import com.wg.bookmyshow.service.EventService;
import com.wg.bookmyshow.util.BookingPrinter;
import com.wg.bookmyshow.util.EventPrinter;
import com.wg.bookmyshow.util.LoggingHelper;

// Initialize TicketController
import java.util.logging.Logger;
public class BookingController {
    private BookingService bookingService;
    private EventService eventService;
    private static PaymentController paymentController;
    private static TicketController ticketController;
    public  BookingController() throws SQLException {
        this.paymentController = new PaymentController(); // Initialize PaymentController
        this.ticketController = new TicketController(); 
        this.bookingService = new BookingService();
    }
    Scanner scanner = new Scanner(System.in);

    private static Logger logger = LoggingHelper.getLogger(BookingController.class);
    public static String processPayment(String userId, double amount, String eventId) throws ClassNotFoundException {
        // Step 1: Make Payment
        String paymentId = paymentController.makePayment(userId, amount);

        if (paymentId==  null) {
            System.err.println("Payment failed. Booking cannot be processed.");
            return null;
        }
		return paymentId;
    }
    public static String processTickets(String userId, double amount, String eventId,int numberOfTickets) throws ClassNotFoundException, SQLException {
        // Step 2: Create Tickets
      
        String ticketId = ticketController.createTickets(userId, eventId,numberOfTickets);;
        if (ticketId==  null) {
            System.err.println("Payment failed. Booking cannot be processed.");
            logger.log(Level.SEVERE, "Ticket creation failed for User ID: " + userId + ", Event ID: " + eventId);
            return null;
           
        }
            
    
        System.out.println("Booking processed successfully. Tickets created.");
        logger.log(Level.INFO, "Tickets created successfully. User ID: " + userId + ", Event ID: " + eventId + ", Ticket ID: " + ticketId + ", Number of Tickets: " + numberOfTickets);
        
        //auto genrated notification
        NotificationController notificationController = new NotificationController();
        String message = "Your booking was successful for event: " + eventId;
        String priority = "HIGH"; // You can adjust priority based on logic
        notificationController.createNotification(message, priority, userId);

        return ticketId;
    }

 

    public static boolean bookTickets() throws ClassNotFoundException, SQLException {
        EventService eventService = new EventService();
        BookingService bookingService = new BookingService();
        Scanner scanner = new Scanner(System.in);

        try {
            // Step 1: Get the event name and number of tickets to book
            System.out.print("Enter the event name: ");
            String eventName = scanner.nextLine().trim();
            
            // Step 2: Check if the event exists and has enough seats
            EventModel event = eventService.getEventByName(eventName);
           
            if (event == null) {
                System.out.println("Event not found.");
                return false;
            }
            
            String eventStatus = event.getEventStatus();
            boolean isBlocked = event.isBlocked(); // Assumes getBlocked() is a method in EventModel

            if (isBlocked) {
                System.out.println("Event is blocked and cannot be booked.");
                return false;
            }

            if ("cancelled".equalsIgnoreCase(eventStatus)) {
                System.out.println("Event is cancelled and cannot be booked.");
                return false;
            }

            
            System.out.print("Enter the number of tickets to book: ");
            int numberOfTickets = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Step 2: Check if the event exists and has enough seats
//            EventModel event = eventService.getEventByName(eventName);
//
//            if (event == null) {
//                System.out.println("Event not found.");
//                return false;
//            }
//
//            // Check event status and blocked status
//            String eventStatus = event.getEventStatus();
//            boolean isBlocked = event.isBlocked(); // Assumes getBlocked() is a method in EventModel
//
//            if (isBlocked) {
//                System.out.println("Event is blocked and cannot be booked.");
//                return false;
//            }
//
//            if ("cancelled".equalsIgnoreCase(eventStatus)) {
//                System.out.println("Event is cancelled and cannot be booked.");
//                return false;
//            }

            if (event.getSeatsAvailable() < numberOfTickets) {
                System.out.println("Not enough seats available for the requested booking.");
                return false;
            }

            double pricePerTicket = event.getPrice(); // Assuming price per ticket is stored in event model
            double totalAmount = pricePerTicket * numberOfTickets;
            System.out.printf("Total bill amount: $%.2f\n", totalAmount);


            System.out.print("Do you want to proceed with the payment? (yes/no): ");
            String paymentChoice = scanner.nextLine().trim().toLowerCase();

            String paymentId = null;
            String ticketId = null;
            if ("yes".equals(paymentChoice)) {
                // Call the PaymentService to create a payment record
                String userId = AccountController.loggedInAccountId;
                String eventId = event.getEventId();
                paymentId = processPayment(userId, totalAmount, eventId);
                ticketId = processTickets(userId, totalAmount, eventId, numberOfTickets);

                if (paymentId == null) {
                    System.out.println("Failed to process payment.");
                    return false;
                }
            }

            // Step 3: Update the number of available seats

            // Step 4: Create booking record 
            if (!bookingService.createBooking(event, numberOfTickets,totalAmount, paymentId, ticketId)) {
                System.out.println("Failed to create booking record.");
                return false;
            }
            event.setSeatsAvailable(event.getSeatsAvailable() - numberOfTickets);
            if (!eventService.updateEvent(event)) {
                System.out.println("Failed to update event seat availability.");
                return false;
            }

            // Step 5: Return success
            System.out.println("Tickets booked successfully!");

            logger.log(Level.INFO, "Tickets booked successfully. Ticket ID: " + ticketId + ", Number of Tickets: " + numberOfTickets);

            String message = "Your booking for the event: " + eventName + " is confirmed!";
            String priority = "HIGH";
            NotificationController notificationController = new NotificationController();
            notificationController.createNotification(eventName, message, priority);

            return true;
        } finally {
            // Ensure the scanner is closed to release resources
        }
    }

    public void cancelBooking() {
        System.out.print("Enter the Booking ID to cancel: ");
        String bookingId = scanner.nextLine().trim();

        // Attempt to cancel the booking
        try {
            if (bookingService.cancelBooking(bookingId)) {
                System.out.println("Booking canceled successfully.");
            } else {
                System.out.println("Failed to cancel the booking. Please check the Booking ID and try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while canceling the booking: " + e.getMessage());
        }
    }

    public void viewBookingsByEventname() {
        System.out.print("Enter the Event Name to view bookings: ");
        String eventName = scanner.nextLine().trim();

        // Retrieve and display bookings by event name
        try {
            List<BookingModel> bookings = bookingService.viewBookingsByEventName(eventName);
            if (bookings.isEmpty()) {
                System.out.println("No bookings found for the event: " + eventName);
            } else {
            	BookingPrinter.printBookings(bookings);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing bookings: " + e.getMessage());
        }
    }

    public void viewBookings() {
        System.out.println("Viewing all bookings:");

        // Retrieve and display all bookings
        try {
            List<BookingModel> bookings = bookingService.viewAllBookings();
            if (bookings.isEmpty()) {
                System.out.println("No bookings found.");
            } else {
            	BookingPrinter.printBookings(bookings);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing all bookings: " + e.getMessage());
        }
    }

    public void viewBookingsById() throws SQLException, ClassNotFoundException {
        String loggedInAccountId = AccountModel.getAccountId();
        
        if (loggedInAccountId != null) {
            List<BookingModel> bookings = bookingService.getBookingsByAccountId(loggedInAccountId);
			System.out.println("Bookings retrieved: " + bookings.size()); // Debug statement
			BookingPrinter.printBookings(bookings);
        } else {
            System.out.println("No user is logged in.");
        }
    }





	
}
