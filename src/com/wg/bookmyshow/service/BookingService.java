package com.wg.bookmyshow.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.wg.bookmyshow.controller.AccountController;
import com.wg.bookmyshow.dao.BookingDao;
import com.wg.bookmyshow.dao.EventDao;
import com.wg.bookmyshow.dao.TicketDao;
import com.wg.bookmyshow.model.AccountModel;
import com.wg.bookmyshow.model.BookingModel;
import com.wg.bookmyshow.model.EventModel;
import com.wg.bookmyshow.model.PaymentModel;
import com.wg.bookmyshow.model.TicketModel;

public class BookingService {

	  private EventDao eventDao;
	    private TicketDao ticketDao;
	    private BookingDao bookingDao;
private PaymentModel payment;
private  TicketModel ticket;
	    public BookingService() {
	        try {
	            this.eventDao = new EventDao();
	            this.ticketDao = new TicketDao();
	            this.bookingDao = new BookingDao();
	            this.payment=new PaymentModel();
	            this.ticket=new TicketModel();
	        } catch (Exception e) {
	            // Handle exception if instantiation fails
	            e.printStackTrace();
	            System.out.println("Failed to initialize DAOs.");
	        }
	    }
    // View all bookings
    public List<BookingModel> viewAllBookings() {
        List<BookingModel> bookings = bookingDao.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            return bookings;
        }
        return bookings;
    }

    // Book tickets
//    public boolean bookTickets() throws ClassNotFoundException {
//        System.out.println("Booking tickets...");
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter Event ID to book: ");
//        String eventId = scanner.nextLine().trim();
//        System.out.print("Enter number of tickets: ");
//        int numberOfTickets = scanner.nextInt();
//        scanner.nextLine(); // Consume newline
//
//        // Fetch the event to verify
//        EventModel event = eventDao.getEventById(eventId);
//        if (event == null) {
//            System.out.println("Event not found.");
//            return false;
//        }
//
//        // Call createBooking with the event and number of tickets
//       // return createBooking(event, numberOfTickets,);
//    }

    // Create booking
    public boolean createBooking(EventModel event, int numberOfTickets,double totalAmount,String paymentId,String ticketId) throws SQLException {
        if (numberOfTickets <= 0) {
            System.out.println("Number of tickets must be greater than 0.");
            return false;
        }

        try {
            // Check if there are enough available seats
            if (numberOfTickets > event.getSeatsAvailable()) {
                System.out.println("Not enough seats available.");
                return false;
            }

            // Create a new booking
            BookingModel booking = new BookingModel();
            booking.setBookingId(UUID.randomUUID().toString());
            booking.setBookingDate(new Timestamp(new Date().getTime()));
            booking.setTotalAmount(totalAmount);
         
            booking.setEventId(event.getEventId());
            // Assuming payment and ticket IDs are handled elsewhere
            booking.setPaymentId(paymentId);
            booking.setTicketId(ticketId);
            try {
				booking.setUserId(AccountController.loggedInAccountId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        

            // Save the booking to the database
            if (!bookingDao.saveBooking(booking)) {
                System.out.println("Failed to create booking.");
                return false;
            }

            

            // Update event's available seats
            event.setSeatsAvailable(event.getSeatsAvailable() - numberOfTickets);
            if (!eventDao.updateEvent(event)) {
                System.out.println("Failed to update event seats.");
                return false;
            }

            System.out.println("Booking created successfully.");
            return true;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("An error occurred while creating the booking.");
            return false;
        }
    }

    private double calculateTotalAmount(double pricePerTicket, int numberOfTickets) {
        return pricePerTicket * numberOfTickets;
    }

    private int seatCounter = 1; // To track seat number allocation

    private String generateSeatNumber() {
        // Assuming seats are allocated in a format like "A1", "A2", "B1", "B2", etc.
        String[] rows = {"A", "B", "C", "D", "E"}; // Example row identifiers
        int rowIndex = (seatCounter - 1) / 10; // Assuming 10 seats per row
        int seatNumber = seatCounter % 10 == 0 ? 10 : seatCounter % 10; // Ensure seat number is between 1 and 10

        // If we run out of row indices, start over (for simplicity)
        if (rowIndex >= rows.length) {
            rowIndex = 0;
        }

        String seatRow = rows[rowIndex];
        String seatLabel = seatNumber < 10 ? "0" + seatNumber : Integer.toString(seatNumber); // Format seat number

        // Update the seatCounter for the next ticket
        seatCounter++;

        return seatRow + seatLabel;
    }
    public List<BookingModel> getBookingsByAccountId(String accountId) throws SQLException, ClassNotFoundException {
        return bookingDao.getBookingsByAccountId(accountId);
    }

  

    // Method to cancel a booking
    public boolean cancelBooking(String bookingId) {
        return bookingDao.cancelBooking(bookingId);
    }

    // Method to get all bookings
    public List<BookingModel> getAllBookings() {
        return bookingDao.getAllBookings();
    }

  
    public List<BookingModel> viewBookingsByEventName(String eventName) {
        return bookingDao.viewBookingsByEventName(eventName);
    }

    // Method to book tickets
    public boolean bookTickets(String accountId, String eventId, int numberOfTickets, double totalAmount, String paymentId) throws SQLException {
        // Construct a new BookingModel instance
        BookingModel booking = new BookingModel();
        // Set booking details
        booking.setBookingId(booking.getBookingId()); // You should implement this method to generate unique IDs
        booking.setBookingDate(new java.sql.Timestamp(System.currentTimeMillis()));
        booking.setTotalAmount(booking.getTotalAmount());


        booking.setUserId(accountId);
        booking.setEventId(eventId);
        booking.setPaymentId(paymentId);
        // You may need to retrieve and set the appropriate ticket ID here

        try {
            // Save the booking and handle any exceptions
            return bookingDao.saveBooking(booking);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


}

