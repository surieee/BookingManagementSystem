package com.wg.bookmyshow.dao;

import com.wg.bookmyshow.config.DBConnection;
import com.wg.bookmyshow.model.EventModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventDao extends GenericDao<EventModel> {

    public EventDao() throws SQLException {
        super();
    }

    @Override
    protected EventModel mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        EventModel event = new EventModel();
        event.setEventId(resultSet.getString("event_id"));
        event.setEventName(resultSet.getString("event_name"));
        event.setVenue(resultSet.getString("venue"));
        event.setPrice(resultSet.getDouble("price"));
        event.setEventDate(resultSet.getTimestamp("event_date"));
        event.setTypeOfEvent(resultSet.getString("type_of_event"));
        event.setEventDescription(resultSet.getString("event_description"));
        event.setSeatsAvailable(resultSet.getInt("seats_available"));
        event.setTotalSeats(resultSet.getInt("total_seats"));
        event.setOrganizerId(resultSet.getString("organizer_id"));
        event.setDurationHours(resultSet.getInt("duration_hours"));
        event.setDurationMinutes(resultSet.getInt("duration_minutes"));
        event.setBlocked(resultSet.getBoolean("blocked"));
        event.setEventStatus(resultSet.getString("event_status"));


        return event;
    }

    public boolean insertEvent(EventModel event) throws ClassNotFoundException {
        String checkOrganizerQuery = "SELECT COUNT(*) FROM Account WHERE account_id = ?";
        String insertEventQuery = "INSERT INTO Event (event_id, event_name, venue, price, event_date, type_of_event, event_description, seats_available, total_seats, organizer_id, duration_hours, duration_minutes, blocked, event_status) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkOrganizerQuery);
             PreparedStatement insertStmt = connection.prepareStatement(insertEventQuery)) {

            // Check if organizer exists
            checkStmt.setString(1, event.getOrganizerId());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("Organizer with ID " + event.getOrganizerId() + " does not exist.");
                return false;
            }

            // Insert event
            insertStmt.setString(1, event.getEventId());
            insertStmt.setString(2, event.getEventName());
            insertStmt.setString(3, event.getVenue());
            insertStmt.setDouble(4, event.getPrice());
            insertStmt.setTimestamp(5, event.getEventDate());
            insertStmt.setString(6, event.getTypeOfEvent());
            insertStmt.setString(7, event.getEventDescription());
            insertStmt.setInt(8, event.getSeatsAvailable());
            insertStmt.setInt(9, event.getTotalSeats());
            insertStmt.setString(10, event.getOrganizerId());
            insertStmt.setInt(11, event.getDurationHours());
            insertStmt.setInt(12, event.getDurationMinutes());
            insertStmt.setBoolean(13, event.isBlocked());
            insertStmt.setString(14, event.getEventStatus());
            

            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEvent(EventModel event) {
        String query = "UPDATE Event SET event_name = ?, venue = ?, price = ?, event_date = ?, type_of_event = ?, event_description = ?, seats_available = ?, total_seats = ?, organizer_id = ?, duration_hours = ?, duration_minutes = ?, blocked = ?, event_status = ?"
        		+ " WHERE event_id = ?";
        
        try {
            // Correctly pass the start_time parameter before the event_id
            return executeUpdateQuery(query, 
                                      event.getEventName(), 
                                      event.getVenue(), 
                                      event.getPrice(), 
                                      new java.sql.Date(event.getEventDate().getTime()), 
                                      event.getTypeOfEvent(), 
                                      event.getEventDescription(), 
                                      event.getSeatsAvailable(), 
                                      event.getTotalSeats(), 
                                      event.getOrganizerId(), 
                                      event.getDurationHours(), 
                                      event.getDurationMinutes(), 
                                      event.isBlocked(), 
                                      event.getEventStatus(), 

// Assuming startTime is of type Date
                                      event.getEventId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteEvent(String eventName) {
        String query = "DELETE FROM Event WHERE event_name = ?";
        try {
            return executeUpdateQuery(query, eventName);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public EventModel getEventByName(String eventName) {
        String query = "SELECT * FROM Event WHERE event_name = ?";
        try {
            return executeGetQuery(query, eventName);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public EventModel getEventById(String eventId) {
        String query = "SELECT * FROM Event WHERE event_id = ?";
        try {
            return executeGetQuery(query, eventId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<EventModel> getAllEvents() {
        String query = "SELECT * FROM Event";
        try {
            return executeGetAllQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<EventModel> findEventsByOrganizerUsername(String username) throws ClassNotFoundException {
        String sql = "SELECT e.* FROM Event e " +
                     "JOIN Account a ON e.organizer_id = a.account_id " +
                     "WHERE a.username = ? AND a.account_role = 'organizer'";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<EventModel> events = new ArrayList<>();
                while (resultSet.next()) {
                    events.add(mapResultSetToEntity(resultSet));
                }
                return events;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

//    public List<EventModel> searchEvents(Map<String, Object> filters) throws ClassNotFoundException {
//        StringBuilder query = new StringBuilder("SELECT * FROM Event WHERE 1=1");
//System.out.println("innnnnn");
//        // Add filters to the query
//        for (String key : filters.keySet()) {
//            query.append(" AND ").append(key).append(" = ?");
//        }
//
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(query.toString())) {
//
//            int index = 1;
//            for (Object value : filters.values()) {
//                stmt.setObject(index++, value);
//            }
//
//            ResultSet rs = stmt.executeQuery();
//            List<EventModel> events = new ArrayList<>();
//
//            while (rs.next()) {
//                events.add(mapResultSetToEntity(rs));
//            }
//
//            return events;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
    public List<EventModel> searchEvents(Map<String, Object> filters) throws ClassNotFoundException {
        StringBuilder query = new StringBuilder("SELECT * FROM Event WHERE 1=1");
        
        // Add filters to the query
        for (String key : filters.keySet()) {
            query.append(" AND ").append(key).append(" = ?");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query.toString())) {

            int index = 1;
            for (Object value : filters.values()) {
                // Ensure proper handling of different data types
                if (value instanceof Integer) {
                    stmt.setInt(index++, (Integer) value);
                } else if (value instanceof Double) {
                    stmt.setDouble(index++, (Double) value);
                } else if (value instanceof Boolean) {
                    stmt.setBoolean(index++, (Boolean) value);
                } else if (value instanceof String) {
                    stmt.setString(index++, (String) value);
                } else if (value instanceof java.sql.Timestamp) {
                    stmt.setTimestamp(index++, (java.sql.Timestamp) value);
                } else {
                    stmt.setObject(index++, value); // Default to Object for flexibility
                }
            }

//            // Debug: Print the constructed query
//            System.out.println("Constructed Query: " + stmt.toString());

            ResultSet rs = stmt.executeQuery();
            List<EventModel> events = new ArrayList<>();

            while (rs.next()) {
                events.add(mapResultSetToEntity(rs));
            }

            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public boolean blockEvent(String eventId) {
        String query = "UPDATE Event SET blocked = TRUE WHERE event_id = ?";
        try {
            return executeUpdateQuery(query, eventId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEventStatus(String eventId, String status) {
        String query = "UPDATE Event SET event_status = ? WHERE event_id = ?";
        try {
            return executeUpdateQuery(query, status, eventId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<EventModel> viewMyEvents(String organizerId) throws SQLException {
        String query = "SELECT * FROM event WHERE organizer_id = ?";
        return executeGetAllQuery(query, organizerId);
    }
    public List<EventModel> viewCancelledEvents() throws SQLException {
        String query = "SELECT * FROM event WHERE event_status = ?";
        return executeGetAllQuery(query, "cancelled");
    }

    public boolean cancelEvent(String eventId) throws SQLException {
        String updateEventQuery = "UPDATE event SET event_status = 'cancelled' WHERE event_id = ?";
        String updateTicketQuery = "UPDATE ticket SET ticket_status = 'canceled' WHERE event_id = ?";
        String updatePaymentQuery = "UPDATE payment SET payment_status = 'refunded' WHERE payment_id IN (SELECT payment_id FROM booking WHERE ticket_id IN (SELECT ticket_id FROM ticket WHERE event_id = ?))";

        try {
            connection.setAutoCommit(false);

            // Update Event Status
            executeUpdateQuery(updateEventQuery, eventId);

            // Update Ticket Status
            executeUpdateQuery(updateTicketQuery, eventId);

            // Update Payment Status
            executeUpdateQuery(updatePaymentQuery, eventId);

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw new SQLException("Error canceling event and updating related records", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

	public List<EventModel> getBlockedEvents() throws SQLException {
    String query = "SELECT * FROM event WHERE blocked = 1";
    return executeGetAllQuery(query);
    
}
	   public int countBlockedEvents() throws SQLException {
	        String query = "SELECT COUNT(*) FROM event WHERE blocked = true";
	        int count = 0;
	        try (ResultSet resultSet = prepareStatement(query).executeQuery()) {
	            if (resultSet.next()) {
	                count = resultSet.getInt(1);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new SQLException("Error counting blocked events", e);
	        }
	        return count;
	    }
	

}







