package com.wg.bookmyshow.dao;

import com.wg.bookmyshow.config.DBConnection;
import com.wg.bookmyshow.model.PaymentModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao extends GenericDao<PaymentModel> {

    public PaymentDao() throws SQLException {
        super();
    }

    @Override
    protected PaymentModel mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        PaymentModel payment = new PaymentModel();
        payment.setPaymentId(resultSet.getString("payment_id"));
        payment.setBillAmount(resultSet.getDouble("bill_amount"));
        payment.setDateOfPayment(resultSet.getTimestamp("date_of_payment"));
        payment.setPaymentStatus(resultSet.getString("payment_status"));
        return payment;
    }

    // Save a new payment to the database
    public String savePayment(PaymentModel payment) {
        String insertQuery = "INSERT INTO payment (payment_id, bill_amount, date_of_payment, payment_status) VALUES (?, ?, ?, ?)";
        try {
            // Use executeUpdateQuery method from GenericDao
            boolean success = executeUpdateQuery(insertQuery, payment.getPaymentId(), payment.getBillAmount(),
                    new Timestamp(payment.getDateOfPayment().getTime()), payment.getPaymentStatus());
            return success ? payment.getPaymentId() : null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving payment", e);
        }
    }

    // Update payment status
    public void updatePaymentStatus(String paymentId, String status) {
        String updateQuery = "UPDATE payment SET payment_status = ? WHERE payment_id = ?";
        try {
            executeUpdateQuery(updateQuery, status, paymentId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating payment status", e);
        }
    }

    // Find payments by user ID
    public List<String> findPaymentsByUserId(String userId) throws ClassNotFoundException {
        List<String> paymentHistory = new ArrayList<>();
        String selectQuery = "SELECT p.payment_id, p.bill_amount, p.date_of_payment, p.payment_status " +
                "FROM payment p " +
                "JOIN booking b ON p.payment_id = b.payment_id " +
                "WHERE b.user_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectQuery)) {

            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String paymentRecord = String.format("Payment ID: %s, Amount: %.2f, Date: %s, Status: %s",
                            rs.getString("payment_id"),
                            rs.getDouble("bill_amount"),
                            rs.getDate("date_of_payment"),
                            rs.getString("payment_status"));
                    paymentHistory.add(paymentRecord);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving payments by user ID", e);
        }
        return paymentHistory;
    }

    // Check if a payment ID exists
    public boolean paymentIdExists(String paymentId) {
        String query = "SELECT 1 FROM payment WHERE payment_id = ?";
        try {
            return executeGetQuery(query, paymentId) != null; // Check if payment exists
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking if payment ID exists", e);
        }
    }
    public  void updatePaymentStatus(String paymentId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();

        try {
            String query = "UPDATE payment SET payment_status = 'refunded' WHERE payment_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, paymentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating payment status");
        } 

    }
}

