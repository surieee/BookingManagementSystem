package com.wg.bookmyshow.service;

import com.wg.bookmyshow.dao.NotificationDetailsDao;
import com.wg.bookmyshow.model.NotificationDetails;

import java.sql.SQLException;
import java.util.List;

public class NotificationDetailsService {

    private final NotificationDetailsDao notificationDetailsDao;

    public NotificationDetailsService() {
        this.notificationDetailsDao = new NotificationDetailsDao();
    }

    public List<NotificationDetails> getNotificationDetails(String userId) throws ClassNotFoundException, SQLException {
        
    	return notificationDetailsDao.getAllNotificationDetails(userId);
    }
}
