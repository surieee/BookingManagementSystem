package com.wg.bookmyshow.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.wg.bookmyshow.controller.AccountController;
import com.wg.bookmyshow.controller.BookingController;
import com.wg.bookmyshow.controller.EventController;
import com.wg.bookmyshow.controller.NotificationController;
import com.wg.bookmyshow.model.EventModel;
import com.wg.bookmyshow.util.EventPrinter;
import com.wg.bookmyshow.util.Printer;

public class OrganizerMenu {
    private static final Printer PRINTER = new Printer(); // Use the Printer for consistent header printing

    public void showOrganizerMenu() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        final EventController eventController = new EventController();
        final BookingController bookingController = new BookingController();
        final AccountController accountController = new AccountController();
        final NotificationController notificationController = new NotificationController();

        while (true) {
            try {
                PRINTER.printHeader("ORGANIZER MENU");

                System.out.println("1. Create Event");
                System.out.println("2. Update Event");
                System.out.println("3. View My Events");
                System.out.println("4. Cancel Event");
                System.out.println("5. Send Notifications");
                System.out.println("6. View All Bookings");
                System.out.println("7. View Booking of Event");
                System.out.println("8. View My Account Details");
                System.out.println("9. Logout");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        eventController.createEvent();
                        break;
                    case 2:
                        List<EventModel> myEventsForUpdate = eventController.viewMyEvents();
                        if (!myEventsForUpdate.isEmpty()) {
                            eventController.updateEvent();
                        } else {
                            System.out.println("No events available for update.");
                        }
                        break;
                    case 3:
                        List<EventModel> myEvents = eventController.viewMyEvents();
                        if (!myEvents.isEmpty()) {
                            EventPrinter.printEvents(myEvents); // Optional: Print events if needed
                        } else {
                            System.out.println("No events found for the given organizer ID.");
                        }
                        break;
                    case 4:
                        List<EventModel> myEventsForCancel = eventController.viewMyEvents();
                        if (!myEventsForCancel.isEmpty()) {
                            eventController.cancelEvent();
                        } else {
                            System.out.println("No events available for cancellation.");
                        }
                        break;
                    case 5:
                        List<EventModel> eventsForNotifications = eventController.viewMyEvents();
                        if (!eventsForNotifications.isEmpty()) {
                            notificationController.sendNotifications();
                        } else {
                            System.out.println("No events available to send notifications.");
                        }
                        break;
                    case 6:
                        bookingController.viewBookings();
                        break;
                    case 7:
                        List<EventModel> eventsForBookingView = eventController.viewMyEvents();
                        if (!eventsForBookingView.isEmpty()) {
                            bookingController.viewBookingsByEventname();
                        } else {
                            System.out.println("No events available to view bookings.");
                        }
                        break;
                    case 8:
                        accountController.viewMyAccount();
                        break;
                    case 9:
                        System.out.println("Logging out...");
                        return; // Exit to the previous menu
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}



