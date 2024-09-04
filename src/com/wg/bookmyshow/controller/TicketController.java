//package com.wg.bookmyshow.controller;
//
//import com.wg.bookmyshow.model.TicketModel;
//import com.wg.bookmyshow.service.TicketService;
//
//import java.util.List;
//
//public class TicketController {
//    private TicketService ticketService;
//
//    public TicketController() {
//        this.ticketService = new TicketService(); // Initialize the TicketService
//    }
//
//    // Create tickets for a given user and event
//    public String createTickets(String userId, String eventId) throws ClassNotFoundException {
//        return ticketService.generateTickets(userId, eventId);
//    }
//
//    // View tickets for a specific user
//    public void viewMyTickets() throws ClassNotFoundException {
//    	String userId = AccountController.loggedInAccountId;
//        List<String> tickets = ticketService.getUserTickets(userId);
//        if (tickets.isEmpty()) {
//            System.out.println("No tickets found for user: " + userId);
//        } else {
//            System.out.println("Tickets for user: " + userId);
//            for (String ticket : tickets) {
//                System.out.println(ticket);
//            }
//        }
//    }
//}
package com.wg.bookmyshow.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.wg.bookmyshow.model.TicketModel;
import com.wg.bookmyshow.service.TicketService;
import com.wg.bookmyshow.util.TicketPrinter;

public class TicketController {

    Scanner scanner = new Scanner(System.in);
    private  TicketService ticketService; // Initialize TicketService

    public TicketController() throws SQLException {
    	this.ticketService=new TicketService();
    }
    
    public List<TicketModel> viewMyTicketsToCancel() throws ClassNotFoundException {
        // Fetch tickets for the logged-in user
        List<TicketModel> tickets = ticketService.getTicketsByUsername(AccountController.loggedInUsername);
        Scanner scanner = new Scanner(System.in);

        if (tickets == null || tickets.isEmpty()) {
            // Print a message if no tickets are found
            System.out.println("No tickets found for your account.");
            return tickets;  // Return an empty list
        } else {
            // Print tickets using TicketPrinter
            TicketPrinter.printTickets(tickets);
            
            // Prompt user to select a ticket by index
            int selectedIndex = -1;
            while (true) {
                System.out.print("Enter the index of the ticket you want to select (or 0 to cancel): ");
                try {
                    selectedIndex = Integer.parseInt(scanner.nextLine().trim());
                    if (selectedIndex == 0) {
                        System.out.println("Selection cancelled.");
                        return tickets; // Return the list of tickets
                    } else if (selectedIndex > 0 && selectedIndex <= tickets.size()) {
                        // Valid index
                        TicketModel selectedTicket = tickets.get(selectedIndex - 1); // Adjust index for 0-based list
                        System.out.println("You selected ticket ID: " + selectedTicket.getTicketId());
                        if ("cancelled".equalsIgnoreCase(selectedTicket.getTicketStatus())) {
                            System.out.println("Ticket is already cancelled.");
                            return null;
                        }
                        ticketService.cancelTicket(selectedTicket.getTicketId());
                        return tickets; // Return the list of tickets
                    } else {
                        System.out.println("Invalid index. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }}
        }
    
    public List<TicketModel> viewMyTickets() throws ClassNotFoundException {
    List<TicketModel> tickets = ticketService.getTicketsByUsername(AccountController.loggedInUsername);
    if (tickets == null || tickets.isEmpty()) {
        // Print a message if no tickets are found
        System.out.println("No tickets found for your account.");
        return tickets;  // Return an empty list
    } else {
        // Print tickets using TicketPrinter
        TicketPrinter.printTickets(tickets);
    }
	return tickets;}
    // Method to generate a new ticket
    public String createTickets( String userId,String eventId, int noOfTickets) {
        try {
            // Generate a ticket with provided details
           
            System.out.println("Ticket generated successfully for seat: " + noOfTickets);
            return ticketService.generateTickets(eventId,noOfTickets);
        } catch (ClassNotFoundException e) {
            System.out.println("Error generating ticket: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}




