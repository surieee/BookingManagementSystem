
package com.wg.bookmyshow.dao;

import com.wg.bookmyshow.model.NotificationDetails;
import com.wg.bookmyshow.model.NotificationModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationDetailsDao extends GenericDao<NotificationDetails> {

    private static final Logger logger = Logger.getLogger(NotificationDetailsDao.class.getName());

    @Override
    protected NotificationDetails mapResultSetToEntity(ResultSet rs) throws SQLException {
        NotificationDetails notification = new NotificationDetails();
        notification.setNotificationId(rs.getString("notification_id"));
        notification.setNotificationMessage(rs.getString("notification_message"));
        notification.setNotificationPriority(rs.getString("notification_priority"));
        notification.setNotifiedUserId(rs.getString("notified_user_id"));
        notification.setEventName(rs.getString("event_name"));
        notification.setVenue(rs.getString("venue"));
        notification.setEventDate(rs.getTimestamp("event_date"));
        notification.setTypeOfEvent(rs.getString("type_of_event"));
        notification.setEventBlocked(rs.getBoolean("is_event_blocked"));
        notification.setEventStatus(rs.getString("event_status"));
        return notification;
    }

    public List<NotificationDetails> getAllNotificationDetails(String userId) throws SQLException {
        String query = "SELECT * FROM notification_details_view WHERE notified_user_id = ?";
        return executeGetAllQuery(query, userId);
    }

  

}

