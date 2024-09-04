package com.wg.bookmyshow.controller;

import com.wg.bookmyshow.model.TicketViewModel;
import com.wg.bookmyshow.service.TicketViewService;
import com.wg.bookmyshow.util.TicketViewPrinter;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TicketViewController {
    private TicketViewService ticketViewService;

    public TicketViewController() throws SQLException {
        ticketViewService = new TicketViewService();
    }



    public void showTicketsForUser() {
    	String userId=AccountController.getLoggedInUserId();
        List<TicketViewModel> tickets = ticketViewService.getTicketsForUser(userId);
        if (tickets != null && !tickets.isEmpty()) {
            TicketViewPrinter.printTickets(tickets); // Assumes BookingPrinter has a method to print TicketViewModel objects
        } else {
            System.out.println("No tickets found for user.");
        }
    }
    


}
