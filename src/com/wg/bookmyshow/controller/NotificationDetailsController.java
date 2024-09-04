package com.wg.bookmyshow.controller;

import com.wg.bookmyshow.model.AccountModel;
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
    	String userId=AccountController.getLoggedInUserId();
    	List<NotificationDetails> notifications = notificationDetailsService.getNotificationDetails(userId);
        NotificationDetailsPrinter.printNotificationDetails(notifications);

    }
}
