package com.wg.bookmyshow.controller;

import com.wg.bookmyshow.model.AccountModel;
import com.wg.bookmyshow.service.PaymentService;

import java.sql.SQLException;
import java.util.List;

public class PaymentController {
    private PaymentService paymentService;

    public PaymentController() throws SQLException {
        this.paymentService = new PaymentService(); // Initialize the PaymentService
    }

    // Make a payment
    public String makePayment(String userId, double amount) throws ClassNotFoundException {
        return paymentService.processPayment(userId, amount);
    }

    // View payment history
    public void viewPaymentHistory() throws ClassNotFoundException {
    	String loggedInAccountId = AccountController.loggedInAccountId;
    	String userId=loggedInAccountId;
        List<String> paymentHistory = paymentService.getPaymentHistory(userId);
        // Display payment history, can be further formatted
        if (paymentHistory.isEmpty()) {
            System.out.println("No payment history found for user: " + userId);
        } else {
            System.out.println("Payment history for user: " + userId);
            for (String payment : paymentHistory) {
                System.out.println(payment);
            }
        }
    }
}
