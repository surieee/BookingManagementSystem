package com.wg.bookmyshow.service;

import com.wg.bookmyshow.dao.PaymentHistoryDao;
import com.wg.bookmyshow.model.PaymentHistoryModel;

import java.sql.SQLException;
import java.util.List;

public class PaymentHistoryService {

    private PaymentHistoryDao paymentHistoryDao;

    public PaymentHistoryService() throws SQLException {
        this.paymentHistoryDao = new PaymentHistoryDao();
    }

    public List<PaymentHistoryModel> getAllPaymentHistories() throws SQLException {
        return paymentHistoryDao.getAllPaymentHistories();
    }
}
