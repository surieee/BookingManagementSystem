package com.wg.bookmyshow.controller;

import com.wg.bookmyshow.model.NotificationDetails;
import com.wg.bookmyshow.service.NotificationDetailsService;
import com.wg.bookmyshow.util.NotificationDetailsPrinter;

import java.sql.SQLException;
import java.util.List;

public class NotificationDetailsController {

    private final NotificationDetailsService notificationDetailsService;

    public NotificationDetailsController() {
        this.notificationDetailsService = new NotificationDetailsService();
    }

    public void displayNotificationDetails() throws ClassNotFoundException, SQLException {
        List<NotificationDetails> notifications = notificationDetailsService.getNotificationDetails();
        NotificationDetailsPrinter.printNotificationDetails(notifications);

    }
}
