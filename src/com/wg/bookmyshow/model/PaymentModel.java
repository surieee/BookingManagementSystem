package com.wg.bookmyshow.model;

import java.util.UUID;
import java.sql.Timestamp;
import java.util.Date;

public class PaymentModel {
    private String paymentId;
    private double billAmount;
    private Timestamp dateOfPayment;
    private String paymentStatus;

    public PaymentModel() {
        this.paymentId = UUID.randomUUID().toString(); // Generate unique payment ID
        this.billAmount = 0.0; // Default bill amount
        this.dateOfPayment = new Timestamp(new Date().getTime());// Default payment date to current date
        this.paymentStatus = "completed"; // Default payment status
    }
    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }


    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public Timestamp getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Timestamp date) {
        this.dateOfPayment = date;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
