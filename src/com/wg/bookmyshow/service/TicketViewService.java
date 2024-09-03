

package com.wg.bookmyshow.service;

import com.wg.bookmyshow.dao.TicketViewDao;
import com.wg.bookmyshow.model.TicketViewModel;

import java.sql.SQLException;
import java.util.List;

public class TicketViewService {

    private TicketViewDao ticketViewDao = new TicketViewDao();

    public List<TicketViewModel> getTicketsForUser(String userId) {
        List<TicketViewModel> tickets = null;
        try {
            tickets = ticketViewDao.getTicketsByUserId(userId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error occurred while fetching tickets: " + e.getMessage());
        }
        return tickets;
    }
}


