package com.wg.bookmyshow.controller;

import com.wg.bookmyshow.model.NotificationModel;
import com.wg.bookmyshow.service.NotificationService;
import com.wg.bookmyshow.util.NotificationPrinter;
import com.wg.bookmyshow.util.NotificationPrinter2;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class NotificationController {
    private NotificationService notificationService;
    private Scanner scanner;

    public NotificationController() throws SQLException {
        this.notificationService = new NotificationService();
        this.scanner = new Scanner(System.in);
    }

    // Method to send notifications for a specific event
    public void sendNotifications() throws ClassNotFoundException {
        System.out.print("Enter event name to send notifications: ");
        String eventName = scanner.nextLine();
        System.out.print("Enter notification message: ");
        String message = scanner.nextLine();
        System.out.print("Enter priority (HIGH, MEDIUM, LOW): ");
        String priority = scanner.nextLine().toUpperCase();

        notificationService.sendNotificationsForEvent(eventName, message, priority);
    }

    // Method to view notifications for a specific user
    public void viewNotifications() throws ClassNotFoundException {
        List<NotificationModel> notifications = notificationService.viewNotifications(AccountController.loggedInAccountId);

        if (notifications.isEmpty()) {
            System.out.println("No notifications found for your account.");
        } else {
            NotificationPrinter.printNotifications(notifications);
            //System.out.print("helloooo "); 
        }
    }

public void viewHighPriorityNotifications() throws ClassNotFoundException {
    // Fetch high-priority notifications for the logged-in user
    List<NotificationModel> highPriorityNotifications = notificationService.getHighPriorityNotifications(AccountController.loggedInAccountId);

    if (highPriorityNotifications.isEmpty()) {
       // System.out.println("No high-priority notifications at the moment.");
        return;
    } else {
        NotificationPrinter2.printNotifications(highPriorityNotifications);
    }
    
}

public void createNotification(String eventName, String message,String priority) throws ClassNotFoundException {
    
	notificationService.sendNotificationsForEvent(eventName, message, priority);
}}

