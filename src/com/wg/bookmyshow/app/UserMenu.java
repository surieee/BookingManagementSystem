

package com.wg.bookmyshow.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.wg.bookmyshow.controller.AccountController;
import com.wg.bookmyshow.controller.BookingController;
import com.wg.bookmyshow.controller.CancelTicketController;
import com.wg.bookmyshow.controller.EventController;
import com.wg.bookmyshow.controller.NotificationController;
import com.wg.bookmyshow.controller.NotificationDetailsController;
import com.wg.bookmyshow.controller.PaymentController;
import com.wg.bookmyshow.controller.PaymentHistoryController;
import com.wg.bookmyshow.controller.TicketController;
import com.wg.bookmyshow.controller.TicketViewController;
import com.wg.bookmyshow.model.EventModel;
import com.wg.bookmyshow.model.TicketModel;
import com.wg.bookmyshow.util.Printer;

public class UserMenu {

    private final EventController eventController;
    private final BookingController bookingController;
    private final NotificationController notificationController;
    private final PaymentController paymentController;
    private final PaymentHistoryController paymentHistoryController;
    private final TicketController ticketController;
    private final TicketViewController ticketViewController;
    private final NotificationDetailsController notificationDetailsController;
    private final AccountController accountController;
    private final CancelTicketController cancelTicketController;
    private static final Printer PRINTER = new Printer(); // For consistent output

    public UserMenu() throws SQLException {
        this.eventController = new EventController();
        this.bookingController = new BookingController();
        this.notificationController = new NotificationController();
        this.paymentController = new PaymentController();
        this.paymentHistoryController = new PaymentHistoryController();
        this.ticketController = new TicketController();
        this.ticketViewController = new TicketViewController();
        this.notificationDetailsController = new NotificationDetailsController();
        this.accountController = new AccountController();
        this.cancelTicketController = new CancelTicketController();
    }

    public void showUserMenu() throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                PRINTER.printHeader("USER MENU"); // Printing header for user menu

                System.out.println("1. View All Events");
                System.out.println("2. Search Events"); // not working
                System.out.println("3. Book Tickets");
                System.out.println("4. View All Ticket Details");
                System.out.println("5. View My Bookings");
                System.out.println("6. View My Notifications");
                System.out.println("7. Cancel Ticket");
                System.out.println("8. View My Tickets");
                System.out.println("9. View Payment History");
                System.out.println("10. View My Account Details");
                System.out.println("11. Logout");
                System.out.print("Choose an option: ");

                int choice = -1;
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); // Clear the invalid input
                    continue; // Restart the loop
                }

                switch (choice) {
                    case 1:
                        List<EventModel> events = eventController.viewEvents();
                        if (events.isEmpty()) {
                            System.out.println("No events available.");
                        }
                        break;
                    case 2:
                        List<EventModel> searchResults =  eventController.viewEvents();
                        if (searchResults.isEmpty()) {
                            System.out.println("No events found matching the search criteria.");
                        } else {
                            // Optionally print search results
                        	eventController.searchEvents();
                        }
                        break;
                    case 3:
                        List<EventModel> availableEvents = eventController.viewEvents();
                        if (!availableEvents.isEmpty()) {
                            bookingController.bookTickets();
                        } else {
                            System.out.println("No events available to book tickets.");
                        }
                        break;
                    case 4:
                       ticketViewController.showTicketsForUser();
                        
                        break;
                    case 5:
                        bookingController.viewBookingsById();
                        break;
                    case 6:
                    	notificationController.viewNotifications();
                        //notificationDetailsController.displayNotificationDetails(); not working;(
                        break;
                    case 7:
                      
                            ticketController.viewMyTicketsToCancel();
                        
                        break;
                    case 8:
                        ticketController.viewMyTickets();
                        break;
                    case 9:
                        paymentHistoryController.showPaymentHistory();
                        break;
                    case 10:
                        accountController.viewMyAccount();
                        break;
                    case 11:
                        System.out.println("Logging out...");
                        return; // Exit the loop and logout
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}

