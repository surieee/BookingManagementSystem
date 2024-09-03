package com.wg.bookmyshow.dao;

import com.wg.bookmyshow.config.DBConnection;
import com.wg.bookmyshow.model.NotificationModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDao extends GenericDao<NotificationModel> {

    public NotificationDao() throws SQLException {
        super();
    }

    @Override
    protected NotificationModel mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        NotificationModel notification = new NotificationModel();
        notification.setNotificationId(resultSet.getString("notification_id"));
        notification.setMessage(resultSet.getString("message"));
        notification.setPriority(resultSet.getString("priority"));
        notification.setUserId(resultSet.getString("user_id"));
        notification.setBookingId(resultSet.getString("booking_id"));
        return notification;
    }

    public boolean addNotification(NotificationModel notification) {
        String sql = "INSERT INTO notification (notification_id, message, priority, user_id, booking_id) VALUES (?, ?, ?, ?, ?)";
        try {
        	
            return executeUpdateQuery(sql, notification.getNotificationId(), notification.getMessage(), 
                    notification.getPriority(), notification.getUserId(), notification.getBookingId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> getUserIdsAndBookingIdsByEventId(String eventId) {
        List<String[]> userBookings = new ArrayList<>();
        String sql = "SELECT user_id, booking_id FROM booking WHERE event_id = ?";
        try {
            try (PreparedStatement preparedStatement = prepareStatement(sql, eventId)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String userId = resultSet.getString("user_id");
                    String bookingId = resultSet.getString("booking_id");
                    userBookings.add(new String[]{userId, bookingId});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userBookings;
    }

    public List<NotificationModel> getNotificationsByUserId(String userId) {
        String sql = "SELECT * FROM notification WHERE user_id = ?";
        try {
            return executeGetAllQuery(sql, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<NotificationModel> getHighPriorityNotificationsByUserId(String userId) {
        String sql = "SELECT * FROM notification WHERE user_id = ? AND priority = 'HIGH'";
        try {
            return executeGetAllQuery(sql, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}



