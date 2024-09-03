package com.wg.bookmyshow.service;

import com.wg.bookmyshow.dao.PaymentDao;
import com.wg.bookmyshow.model.PaymentModel;

import java.sql.SQLException;
import java.util.List;

public class PaymentService {
    private PaymentDao paymentDao;

    public PaymentService() throws SQLException {
        this.paymentDao = new PaymentDao(); // Initialize the PaymentDao
    }

    // Process a payment
    public String processPayment(String userId, double amount) throws ClassNotFoundException {
        PaymentModel payment = new PaymentModel();
        payment.setBillAmount(amount);
    

        String paymentId = paymentDao.savePayment(payment);

        // Immediately print and check the Payment ID
        //System.out.println("Payment ID after saving: " + payment.getPaymentId());

        if (paymentId!=null) {
           
           paymentDao.updatePaymentStatus(payment.getPaymentId(), "completed");
            return paymentId;
        } else {
            return null;
        }
    }


    // Get payment history for a user
    public List<String> getPaymentHistory(String userId) throws ClassNotFoundException {
        return paymentDao.findPaymentsByUserId(userId);
    }
}
