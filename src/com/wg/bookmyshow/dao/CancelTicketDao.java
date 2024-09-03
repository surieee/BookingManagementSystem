package com.wg.bookmyshow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wg.bookmyshow.model.CancelTicketModel;

public class CancelTicketDao extends GenericDao<CancelTicketModel> {

    @Override
    protected CancelTicketModel mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        CancelTicketModel cancelTicket = new CancelTicketModel();
        cancelTicket.setTicketId(resultSet.getString("ticket_id"));
        cancelTicket.setSeatNumber(resultSet.getString("seat_number"));
        cancelTicket.setTicketStatus(resultSet.getString("ticket_status"));
        cancelTicket.setBookingId(resultSet.getString("booking_id"));
        cancelTicket.setBookingStatus(resultSet.getString("booking_status"));
        cancelTicket.setPaymentId(resultSet.getString("payment_id"));
        cancelTicket.setPaymentStatus(resultSet.getString("payment_status"));
        cancelTicket.setBillAmount(resultSet.getDouble("bill_amount"));
        return cancelTicket;
    }

    public boolean cancelTicketAndRelatedEntities(String ticketId) throws SQLException {
        // Update ticket status
        String updateTicketQuery = "UPDATE Ticket SET ticket_status = 'canceled' WHERE ticket_id = ?";
        boolean ticketUpdated = executeUpdateQuery(updateTicketQuery, ticketId);

        // Update booking status
        String updateBookingQuery = "UPDATE Booking SET booking_status = 'canceled' WHERE ticket_id = ?";
        boolean bookingUpdated = executeUpdateQuery(updateBookingQuery, ticketId);

        // Update payment status to refunded
        String updatePaymentQuery = "UPDATE Payment p JOIN Booking b ON p.payment_id = b.payment_id " +
                                    "SET p.payment_status = 'refunded' WHERE b.ticket_id = ?";
        boolean paymentUpdated = executeUpdateQuery(updatePaymentQuery, ticketId);

        return ticketUpdated && bookingUpdated && paymentUpdated;
    }
}
