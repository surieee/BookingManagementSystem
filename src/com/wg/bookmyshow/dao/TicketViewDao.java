package com.wg.bookmyshow.dao;

import com.wg.bookmyshow.model.TicketViewModel;
import com.wg.bookmyshow.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketViewDao {

    public List<TicketViewModel> getTicketsByUserId(String userId) throws SQLException, ClassNotFoundException {
        List<TicketViewModel> tickets = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        
        String query = "SELECT ticket_id, seat_number, ticket_status, booking_id, booking_date, total_amount, " +
                       "booking_event_id, user_id, payment_id, bill_amount, date_of_payment, payment_status " +
                       "FROM ticketbookingpaymentview WHERE user_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TicketViewModel ticket = new TicketViewModel();
                ticket.setTicketId(resultSet.getString("ticket_id"));
                ticket.setSeatNumber(resultSet.getString("seat_number"));
                ticket.setTicketStatus(resultSet.getString("ticket_status"));
                ticket.setBookingId(resultSet.getString("booking_id"));
                ticket.setBookingDate(resultSet.getString("booking_date"));
                ticket.setTotalAmount(resultSet.getDouble("total_amount"));
                ticket.setBookingEventId(resultSet.getString("booking_event_id"));
                ticket.setUserId(resultSet.getString("user_id"));
                ticket.setPaymentId(resultSet.getString("payment_id"));
                ticket.setBillAmount(resultSet.getDouble("bill_amount"));
                ticket.setDateOfPayment(resultSet.getString("date_of_payment"));
                ticket.setPaymentStatus(resultSet.getString("payment_status"));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving tickets for user", e);
        }

        return tickets;
    }
}

    


