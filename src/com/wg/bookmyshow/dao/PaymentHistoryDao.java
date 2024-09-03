package com.wg.bookmyshow.dao;


import com.wg.bookmyshow.model.PaymentHistoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PaymentHistoryDao extends GenericDao<PaymentHistoryModel> {

    public PaymentHistoryDao() {
        super(); // Calls the constructor of GenericDao
    }

    @Override
    protected PaymentHistoryModel mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        PaymentHistoryModel paymentHistory = new PaymentHistoryModel();
        paymentHistory.setBookingDate(resultSet.getTimestamp("booking_date"));
        paymentHistory.setEventName(resultSet.getString("event_name"));
        paymentHistory.setVenue(resultSet.getString("venue"));
        paymentHistory.setEventDate(resultSet.getDate("event_date"));
        paymentHistory.setUsername(resultSet.getString("username"));
        paymentHistory.setEmail(resultSet.getString("email"));
        paymentHistory.setPaymentId(resultSet.getString("payment_id"));
        paymentHistory.setBillAmount(resultSet.getDouble("bill_amount"));
        paymentHistory.setPaymentStatus(resultSet.getString("payment_status"));
        paymentHistory.setTicketId(resultSet.getString("ticket_id"));
        paymentHistory.setSeatNumber(resultSet.getString("seat_number"));
        paymentHistory.setTicketStatus(resultSet.getString("ticket_status"));
        return paymentHistory;
    }

    // Method to fetch all payment histories
    public List<PaymentHistoryModel> getAllPaymentHistories() throws SQLException {
        String query = "SELECT * FROM PaymentHistory"; // Assuming the view name is PaymentHistory
        return executeGetAllQuery(query);
    }
}
