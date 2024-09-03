package com.wg.bookmyshow.controller;

import java.util.Scanner;

import com.wg.bookmyshow.service.CancelTicketService;

public class CancelTicketController {

    private CancelTicketService cancelTicketService;

    public CancelTicketController() {
        this.cancelTicketService = new CancelTicketService();
    }


    public void cancelTicket() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Prompt the user for ticket ID
            System.out.print("Enter the Ticket ID to cancel: ");
            String ticketId = scanner.nextLine();
            
            // Call the service method to cancel the ticket
            boolean isCancelled = cancelTicketService.cancelTicket(ticketId);

            if (isCancelled) {
                System.out.println("Ticket with ID " + ticketId + " has been successfully cancelled.");
            } else {
                System.out.println("Failed to cancel the ticket. Please check the ticket ID and try again.");
            }
        } finally {
            // Optionally, close the scanner if it's not used elsewhere
            scanner.close();
        }
    }
}
