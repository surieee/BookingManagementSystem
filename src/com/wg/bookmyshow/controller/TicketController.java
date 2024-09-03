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
    // Method to view tickets of the logged-in user
//    public void viewMyTickets() throws ClassNotFoundException {
//        // Fetch tickets for the logged-in user
//        List<TicketModel> tickets = ticketService.getTicketsByUsername(AccountController.loggedInUsername);
//
//        if (tickets.isEmpty()) {
//            System.out.println("No tickets found for your account.");
//        } else {
//            // Print tickets using TicketPrinter
//            TicketPrinter.printTickets(tickets);
//        }
//    }
//
//    public  viewMyTickets() throws ClassNotFoundException {
//        // Fetch tickets for the logged-in user
//        List<TicketModel> tickets = ticketService.getTicketsByUsername(AccountController.loggedInUsername);
//
//        if (tickets == null || tickets.isEmpty()) {
//            System.out.println("No tickets found for your account.");
//        } else {
//            // Print tickets using TicketPrinter
//            TicketPrinter.printTickets(tickets);
//        }
//    }

//    public List<TicketModel> viewMyTickets() throws ClassNotFoundException {
//        // Fetch tickets for the logged-in user
//        List<TicketModel> tickets = ticketService.getTicketsByUsername(AccountController.loggedInUsername);
//
//        if (tickets == null || tickets.isEmpty()) {
//            // Print a message if no tickets are found
//            System.out.println("No tickets found for your account.");
//            return tickets;  // Return an empty list
//        } else {
//            // Print tickets using TicketPrinter
//            TicketPrinter.printTickets(tickets);
//            return tickets; // Return the list of tickets
//        }
//    }
    public List<TicketModel> viewMyTickets() throws ClassNotFoundException {
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
                        System.out.println("Selection canceled.");
                        return tickets; // Return the list of tickets
                    } else if (selectedIndex > 0 && selectedIndex <= tickets.size()) {
                        // Valid index
                        TicketModel selectedTicket = tickets.get(selectedIndex - 1); // Adjust index for 0-based list
                        System.out.println("You selected ticket ID: " + selectedTicket.getTicketId());
                        // Additional processing for the selected ticket can be done here
                        return tickets; // Return the list of tickets
                    } else {
                        System.out.println("Invalid index. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }}
        }
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
    public void cancelTicket() {
        try {
            // Fetch the list of tickets for the logged-in user
            List<TicketModel> tickets = ticketService.getTicketsByUsername(AccountController.loggedInUsername);
            
            // Check if there are any tickets to cancel
            if (tickets == null || tickets.isEmpty()) {
                System.out.println("No tickets found for your account.");
                return;
            }

            // Print the list of tickets with indexes
            TicketPrinter.printTickets(tickets);

            // Prompt the user to select a ticket by index
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the index of the ticket you want to cancel: ");
            int index = scanner.nextInt();

            // Validate the input
            if (index < 1 || index > tickets.size()) {
                System.out.println("Invalid index. Please try again.");
                return;
            }

            // Retrieve the ticket ID based on the selected index
            TicketModel selectedTicket = tickets.get(index - 1); // Adjusting index as list is zero-based
            String ticketId = selectedTicket.getTicketId();

            // Cancel the ticket using the retrieved ticket ID
            ticketService.cancelTicket(ticketId);
            //System.out.println("Ticket has been canceled successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while canceling the ticket: " + e.getMessage());
            e.printStackTrace();
        }}
}



