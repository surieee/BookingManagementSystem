//package com.wg.bookmyshow.service;
//
//import com.wg.bookmyshow.dao.TicketDao;
//import com.wg.bookmyshow.model.TicketModel;
//
//import java.util.List;
//import java.util.UUID;
//
//public class TicketService {
//    private TicketDao ticketDao;
//
//    public TicketService() {
//        this.ticketDao = new TicketDao(); // Initialize the TicketDao
//    }
//
//    // Generate tickets for a specific user and event
//    public String generateTickets(String userId, String eventId) throws ClassNotFoundException {
//        TicketModel ticket = new TicketModel();
//    
//        ticket.setSeatNumber(generateSeatNumber()); // Generate seat number
//        ticket.setTicketStatus("booked");
//        ticket.setEventId(eventId);
//
//        return ticketDao.saveTicket(ticket);
//    }
//
//    // Get tickets for a specific user
//    public List<String> getUserTickets(String userId) throws ClassNotFoundException {
//        return ticketDao.findTicketsByUserId(userId);
//    }
//
//    // Helper method to generate seat numbers
//    private String generateSeatNumber() {
//        // Simple logic for seat number generation, can be improved
//        return "S" + ((int) (Math.random() * 100) + 1);
//    }
//}
package com.wg.bookmyshow.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.wg.bookmyshow.dao.PaymentDao;
import com.wg.bookmyshow.dao.TicketDao;
import com.wg.bookmyshow.model.TicketModel;

public class TicketService {
	private final TicketDao ticketDao;
	
	public TicketService() throws SQLException {
        this.ticketDao = new TicketDao(); // Initialize the PaymentDao
    }


    private int seatCounter = 1; // To track seat number allocation

    public List<TicketModel> getTicketsByUsername(String username) throws ClassNotFoundException {
        return ticketDao.getTicketsByUsername(username);
    }

//    public void generateTicket(String seatNumber, String ticketStatus, String eventId) throws ClassNotFoundException {
//        // Generate a unique ticket ID using UUID
//        String ticketId = UUID.randomUUID().toString();
//
//        // Create a TicketModel instance
//        TicketModel ticket = new TicketModel();
//        ticket.setTicketId(ticketId);
//        ticket.setSeatNumber(seatNumber);
//        ticket.setTicketStatus(ticketStatus);
//        ticket.setEventId(eventId);
//
//        // Save the ticket using TicketDao
//        if (ticketDao.createTicket(ticket)!=null) {
//            System.out.println("Failed to create ticket with ID: " + ticketId);
//        }
//    }

    public String generateTickets(String eventId, int numberOfTickets) throws ClassNotFoundException {
    	TicketModel ticket = new TicketModel();
    	for (int i = 0; i < numberOfTickets; i++) {
   
            ticket.setTicketId(UUID.randomUUID().toString());
            ticket.setSeatNumber(generateSeatNumber()); // Generate seat number for each ticket
            ticket.setTicketStatus("booked"); // Assuming tickets start as available
            ticket.setEventId(eventId);

//            if (ticketDao.createTicket(ticket)!=null) {
//                System.out.println("Failed to create ticket for seat number " + ticket.getSeatNumber());
//                return null;
            }
            try {
                return ticketDao.createTicket(ticket);
            } catch (Exception e) {
                System.err.println("Error creating ticket: " + e.getMessage());
                return null; // Return false if there is an error
            }
        }
       
    

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

    public void cancelTicket(String ticketId) {
        try {
            // Directly call the cancelTicket method which handles everything
            ticketDao.cancelTicket(ticketId);
            System.out.println("Ticket has been canceled and payment refunded successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error occurred while canceling ticket: " + e.getMessage());
        }
    }
}

