package com.wg.bookmyshow.service;

import java.sql.SQLException;

import com.wg.bookmyshow.dao.CancelTicketDao;
import com.wg.bookmyshow.model.CancelTicketModel;

public class CancelTicketService {

    private CancelTicketDao cancelTicketDao;

    public CancelTicketService() {
        this.cancelTicketDao = new CancelTicketDao();
    }

    public boolean cancelTicket(String ticketId) {
        try {
            // Fetch the current state before canceling (optional for business logic)
            CancelTicketModel cancelTicket = cancelTicketDao.executeGetQuery("SELECT * FROM CancelTicketView WHERE ticket_id = ?", ticketId);
            if (cancelTicket == null || !"booked".equals(cancelTicket.getTicketStatus())) {
                return false; // Cannot cancel non-booked tickets
            }

            // Proceed with cancellation
            return cancelTicketDao.cancelTicketAndRelatedEntities(ticketId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}