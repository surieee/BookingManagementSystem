package com.wg.bookmyshow.controller;

import com.wg.bookmyshow.model.PaymentHistoryModel;
import com.wg.bookmyshow.service.PaymentHistoryService;
import com.wg.bookmyshow.service.PaymentService;
import com.wg.bookmyshow.util.PaymentHistoryPrinter;

import java.sql.SQLException;
import java.util.List;

public class PaymentHistoryController {

    private PaymentHistoryService paymentHistoryService;

    public PaymentHistoryController() throws SQLException {
        this.paymentHistoryService = new PaymentHistoryService();
    }

    public void showPaymentHistory() {
        try {
            List<PaymentHistoryModel> paymentHistories = paymentHistoryService.getAllPaymentHistories();
            PaymentHistoryPrinter.printAllPaymentHistories(paymentHistories);        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving payment history.");
        }
    }
//    public void refundPayment() {
//       
//        String paymentId = scanner.nextLine();
//
//        boolean success = PaymentService.refundPayment(paymentId);
//        if (success) {
//            System.out.println("Payment has been successfully refunded.");
//        } else {
//            System.out.println("Failed to refund the payment.");
//        }
//    }
}