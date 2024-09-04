package com.wg.bookmyshow.dao;

import com.wg.bookmyshow.config.DBConnection;
import com.wg.bookmyshow.model.TicketModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDao extends GenericDao<TicketModel> {

    public TicketDao() throws SQLException {
        super();
    }

    @Override
    protected TicketModel mapResultSetToEntity(ResultSet rs) throws SQLException {
        TicketModel ticket = new TicketModel();
        ticket.setTicketId(rs.getString("ticket_id"));
        ticket.setSeatNumber(rs.getString("seat_number"));
        ticket.setTicketStatus(rs.getString("ticket_status"));
        ticket.setEventId(rs.getString("event_id"));
        return ticket;
    }

    // Method to create a ticket
    public String createTicket(TicketModel ticket) {
        String query = "INSERT INTO ticket (ticket_id, seat_number, ticket_status, event_id) VALUES (?, ?, ?, ?)";

        try {
            boolean success = executeUpdateQuery(query, ticket.getTicketId(), ticket.getSeatNumber(), ticket.getTicketStatus(), ticket.getEventId());
            return success ? ticket.getTicketId() : null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating ticket", e);
        }
    }

    // Method to get tickets by username
    public List<TicketModel> getTicketsByUsername(String username) {
        List<TicketModel> tickets = new ArrayList<>();

        String query = "SELECT t.ticket_id, t.seat_number, t.ticket_status, t.event_id " +
                       "FROM ticket t " +
                       "JOIN booking b ON t.ticket_id = b.ticket_id " +
                       "JOIN account a ON b.user_id = a.account_id " +
                       "WHERE a.username = ?";

        try {
            tickets = executeGetAllQuery(query, username);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving tickets by username", e);
        }

        return tickets;
    }

    // Method to get a ticket by its ID
    public TicketModel getTicketById(String ticketId) {
        String query = "SELECT * FROM ticket WHERE ticket_id = ?";
        try {
            return executeGetQuery(query, ticketId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving ticket by ID", e);
        }
    }
    public void cancelTicket(String ticketId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        String paymentId = null;

        try {
            // Begin transaction
            connection.setAutoCommit(false);

            // Fetch the payment ID associated with the ticket using the booking table
            String query = "SELECT b.payment_id FROM booking b INNER JOIN ticket t ON b.ticket_id = t.ticket_id WHERE t.ticket_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ticketId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                paymentId = resultSet.getString("payment_id");
            }

            if (paymentId != null) {
                // Update ticket status to 'canceled'
                query = "UPDATE ticket SET ticket_status = 'cancelled' WHERE ticket_id = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, ticketId);
                statement.executeUpdate();

                // Update payment status to 'refunded'
                query = "UPDATE payment SET payment_status = 'refunded' WHERE payment_id = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, paymentId);
                statement.executeUpdate();

                // Commit the transaction
                connection.commit();
            } else {
                System.out.println("No payment found for the provided ticket ID.");
                connection.rollback();
            }

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw new SQLException("Error canceling ticket and updating payment status");
        } finally {
            connection.setAutoCommit(true);
           
        }
    }
}





