//package com.wg.bookmyshow.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.wg.bookmyshow.config.DBConnection;
//import com.wg.bookmyshow.model.BookingModel;
//
//public class BookingDao extends GenericDao<BookingModel> {
//
//    public BookingDao() throws SQLException {
//        super();
//    }
//
//    @Override
//    protected BookingModel mapResultSetToEntity(ResultSet rs) throws SQLException {
//        BookingModel booking = new BookingModel();
//        booking.setBookingId(rs.getString("booking_id"));
//        booking.setBookingDate(rs.getTimestamp("booking_date"));
//        booking.setTotalAmount(rs.getDouble("total_amount"));
//        booking.setBookingStatus(rs.getString("booking_status"));
//        booking.setEventId(rs.getString("event_id"));
//        booking.setPaymentId(rs.getString("payment_id"));
//        booking.setTicketId(rs.getString("ticket_id"));
//        booking.setUserId(rs.getString("user_id"));
//        booking.setEventStatus(rs.getString("event_status"));
//        return booking;
//    }
//
//    public List<BookingModel> getAllBookings() {
//        String query = "SELECT * FROM Booking";
//        try {
//            return executeGetAllQuery(query);
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//
//    public boolean saveBooking(BookingModel booking) throws ClassNotFoundException {
//        String insertBookingQuery = "INSERT INTO Booking (booking_id, booking_date, total_amount, booking_status, user_id, event_id, payment_id, ticket_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        try (Connection connection = DBConnection.getConnection()) {
//            connection.setAutoCommit(false);
//
//            if (!doesPaymentExist(connection, booking.getPaymentId())) {
//                System.err.println("Payment ID does not exist. Cannot proceed with booking.");
//                return false;
//            }
//
//            try (PreparedStatement bookingStmt = connection.prepareStatement(insertBookingQuery)) {
//                bookingStmt.setString(1, booking.getBookingId());
//                bookingStmt.setTimestamp(2, booking.getBookingDate());
//                bookingStmt.setDouble(3, booking.getTotalAmount());
//                bookingStmt.setString(4, booking.getBookingStatus());
//                bookingStmt.setString(5, booking.getUserId());
//                bookingStmt.setString(6, booking.getEventId());
//                bookingStmt.setString(7, booking.getPaymentId());
//                bookingStmt.setString(8, booking.getTicketId());
//
//                int rowsInserted = bookingStmt.executeUpdate();
//                connection.commit();
//
//                return rowsInserted > 0;
//            } catch (SQLException e) {
//                connection.rollback(); // Rollback transaction on failure
//                e.printStackTrace();
//                return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean cancelBooking(String bookingId) {
//        String sql = "UPDATE Booking SET booking_status = 'canceled' WHERE booking_id = ?";
//        try {
//            return executeQuery(sql, bookingId);
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public List<BookingModel> viewBookingsByEventName(String eventName) throws ClassNotFoundException {
//        String sql = "SELECT * FROM Booking WHERE event_id = (SELECT event_id FROM Event WHERE event_name = ?)";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, eventName);
//            ResultSet rs = stmt.executeQuery();
//
//            List<BookingModel> bookings = new ArrayList<>();
//            while (rs.next()) {
//                bookings.add(mapResultSetToEntity(rs));
//            }
//            return bookings;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//
//    public List<BookingModel> getBookingsByAccountId(String accountId) {
//    	
//    	String query = "SELECT * FROM Booking WHERE user_id = ?";
//        try {
//            return executeGetAllQuery(query, accountId);
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//
//    private boolean doesPaymentExist(Connection connection, String paymentId) throws SQLException {
//        String checkPaymentQuery = "SELECT COUNT(*) FROM Payment WHERE payment_id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(checkPaymentQuery)) {
//            stmt.setString(1, paymentId);
//            ResultSet rs = stmt.executeQuery();
//            rs.next();
//            return rs.getInt(1) > 0;
//        }
//    }
//}
package com.wg.bookmyshow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wg.bookmyshow.config.DBConnection;
import com.wg.bookmyshow.model.BookingModel;

public class BookingDao extends GenericDao<BookingModel> {

    public BookingDao() throws SQLException {
        super();
    }

    @Override
    protected BookingModel mapResultSetToEntity(ResultSet rs) throws SQLException {
        BookingModel booking = new BookingModel();
        booking.setBookingId(rs.getString("booking_id"));
        booking.setBookingDate(rs.getTimestamp("booking_date"));
        booking.setTotalAmount(rs.getDouble("total_amount"));
       
        booking.setEventId(rs.getString("event_id"));
        booking.setPaymentId(rs.getString("payment_id"));
        booking.setTicketId(rs.getString("ticket_id"));
        booking.setUserId(rs.getString("user_id"));
  
        return booking;
    }

    public List<BookingModel> getAllBookings() {
        String query = "SELECT * FROM Booking";
        try {
            return executeGetAllQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean saveBooking(BookingModel booking) throws ClassNotFoundException {
        String insertBookingQuery = "INSERT INTO Booking (booking_id, booking_date, total_amount,user_id, event_id, payment_id, ticket_id) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            if (!doesPaymentExist(connection, booking.getPaymentId())) {
                System.err.println("Payment ID does not exist. Cannot proceed with booking.");
                connection.rollback();
                return false;
            }

            boolean result = executeUpdateQuery(insertBookingQuery, 
                booking.getBookingId(),
                booking.getBookingDate(),
                booking.getTotalAmount(),
               
                booking.getUserId(),
                booking.getEventId(),
                booking.getPaymentId(),
                booking.getTicketId());

            if (result) {
                connection.commit();
            } else {
                connection.rollback();
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelBooking(String bookingId) {
        String sql = "UPDATE Booking SET booking_status = 'canceled' WHERE booking_id = ?";
        try {
            return executeUpdateQuery(sql, bookingId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<BookingModel> viewBookingsByEventName(String eventName) {
        String sql = "SELECT * FROM Booking WHERE event_id = (SELECT event_id FROM Event WHERE event_name = ?)";
        try (PreparedStatement stmt = prepareStatement(sql, eventName);
             ResultSet rs = stmt.executeQuery()) {
            List<BookingModel> bookings = new ArrayList<>();
            while (rs.next()) {
                bookings.add(mapResultSetToEntity(rs));
            }
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<BookingModel> getBookingsByAccountId(String accountId) {
        String query = "SELECT * FROM Booking WHERE user_id = ?";
        try {
            return executeGetAllQuery(query, accountId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private boolean doesPaymentExist(Connection connection, String paymentId) throws SQLException {
        String checkPaymentQuery = "SELECT COUNT(*) FROM Payment WHERE payment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(checkPaymentQuery)) {
            stmt.setString(1, paymentId);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }
}




