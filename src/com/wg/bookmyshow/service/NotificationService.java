package com.wg.bookmyshow.service;

import com.wg.bookmyshow.config.DBConnection;
import com.wg.bookmyshow.dao.EventDao;
import com.wg.bookmyshow.dao.NotificationDao;
import com.wg.bookmyshow.model.EventModel;
import com.wg.bookmyshow.model.NotificationModel;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class NotificationService {
    private NotificationDao notificationDao;
    private EventDao eventDao;

    public NotificationService() throws SQLException {
        this.notificationDao = new NotificationDao();
        this.eventDao = new EventDao();
    }

    // Method to send notifications for a specific event
    public void sendNotificationsForEvent(String eventName, String message, String priority) throws ClassNotFoundException {
        // Get event ID by event name
        EventModel event = eventDao.getEventByName(eventName);

        if (event == null || event.getEventId() == null) {
            System.out.println("Event not found: " + eventName);
            return;
        }

        // Get user IDs and booking IDs by event ID
        List<String[]> userBookings = notificationDao.getUserIdsAndBookingIdsByEventId(event.getEventId());

        // Send notifications to each user
        for (String[] userBooking : userBookings) {
            String userId = userBooking[0];
            String bookingId = userBooking[1];

            NotificationModel notification = new NotificationModel();
            notification.setNotificationId("N" + System.currentTimeMillis());
            notification.setMessage(message);
            notification.setPriority(priority);
            notification.setUserId(userId);
            notification.setBookingId(bookingId);

            notificationDao.addNotification(notification);
        }
        System.out.println("Notifications sent successfully for event: " + eventName);
    }

    public List<NotificationModel> viewNotifications(String userId) throws ClassNotFoundException {
        return notificationDao.getNotificationsByUserId(userId);
    }

	
	 // Method to get high-priority notifications for a specific user
    public List<NotificationModel> getHighPriorityNotifications(String userId) throws ClassNotFoundException {
      //  AccountModel account=findByUserId(userId);
    	return notificationDao.getHighPriorityNotificationsByUserId(userId);
    }
   

    // Method to create and send a notification
    public boolean createAndSendNotification(String userId, String message, String priority, String bookingId) {
        // Create a new NotificationModel instance
    	
        NotificationModel notification = new NotificationModel();
        notification.setNotificationId(UUID.randomUUID().toString()); // Generate a unique notification ID
        notification.setMessage(message);
        notification.setPriority(priority);
        notification.setUserId(userId);
        notification.setBookingId(bookingId);

        return notificationDao.addNotification(notification);
    }

    public boolean approvalNotification(String message, String priority,String userId) {
        // Create a new NotificationModel instance
    	
        NotificationModel notification = new NotificationModel();
        notification.setNotificationId(UUID.randomUUID().toString()); // Generate a unique notification ID
        notification.setMessage(message);
        notification.setPriority("HIGH");
        notification.setUserId(userId);
        notification.setBookingId(null);

        return notificationDao.addNotification(notification);
    }
    
}

