//package com.wg.bookmyshow.app;
//
//import java.sql.SQLException;
//import java.util.InputMismatchException;
//import java.util.Scanner;
//
//import com.wg.bookmyshow.controller.AccountController;
//import com.wg.bookmyshow.controller.EventController;
//import com.wg.bookmyshow.controller.TicketViewController;
//import com.wg.bookmyshow.service.TicketService;
//import com.wg.bookmyshow.util.Printer;
//
//public class ManageEvents {
//    private static final Printer PRINTER = new Printer(); // Use the Printer for consistent header printing
//    private final EventController eventController ;
//
//
//	private TicketViewController ticketViewController;
//   
//
//    public ManageEvents() throws SQLException {
//        this.eventController = new EventController();
//        this.ticketViewController= new TicketViewController();
//    }
//    public void manageEventsMenu() throws SQLException, ClassNotFoundException {
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            try {
//                PRINTER.printHeader("MANAGE EVENTS MENU");
//
//
//                System.out.println("1. View All Events");
//                
//                System.out.println("2. Delete Event");
//                System.out.println("3. Block Event [Cannot be booked further]");
//                System.out.println("4. Unblock Event");
//                System.out.println("5. Cancel Event[Refunds booked tickets]");
//                System.out.println("6. Approve Event Creation Request");
//                System.out.println("7. Update Event");
//                System.out.println("8. View Blocked Events");
//                System.out.println("9. View Cancelled Events");
//                System.out.println("10. Back to Admin Menu");
//                System.out.print("Choose an option: ");
//                int choice = scanner.nextInt();
//                scanner.nextLine(); // Consume newline
//
//                switch (choice) {
//
//                case 1:
//                    eventController.viewEvents();
//                    break;
//
//
//                    case 2:
//                    	eventController.viewEvents();
//                        eventController.deleteEvent();
//                        eventController.cancelEvent();
//                        break;
//                    case 3:
//                    	eventController.viewEvents();
//                        eventController.blockEvent();
//                        break;
//                    case 4:
//                   	 eventController.viewBlockedEvents();
//                       eventController.approveEvents();
//                       break;
//                    case 5:
//                    	 eventController.viewEvents();
//                    	  eventController.cancelEvent();
//
//
//                          break;
//                    case 6:
//                    	 eventController.viewBlockedEvents();
//                        eventController.approveEvents();
//                        break;
//                    case 7:
//                    	eventController.viewEvents();
//                        eventController.updateEvent();
//                        break;
//                    
//                    case 8:
//                        eventController.viewBlockedEvents();
//                        break;
//                    case 9:
//                    	   eventController.viewCancelledEvents();
//                        break;  
//                    case 10:
//                        System.out.println("Returning to previous menu...");
//                        return; // Exit to the previous menu
//                    default:
//                        System.out.println("Invalid choice. Please try again.");
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("Invalid input. Please enter a number.");
//                scanner.nextLine(); // Clear the invalid input
//            } catch (Exception e) {
//                System.out.println("An unexpected error occurred: " + e.getMessage());
//            }
//        }
//    }
//}
package com.wg.bookmyshow.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.wg.bookmyshow.controller.AccountController;
import com.wg.bookmyshow.controller.EventController;
import com.wg.bookmyshow.controller.TicketViewController;
import com.wg.bookmyshow.model.EventModel;
import com.wg.bookmyshow.util.Printer;

public class ManageEvents {
    private static final Printer PRINTER = new Printer(); // Use the Printer for consistent header printing
    private final EventController eventController;
    private final TicketViewController ticketViewController;

    public ManageEvents() throws SQLException {
        this.eventController = new EventController();
        this.ticketViewController = new TicketViewController();
    }

    public void manageEventsMenu() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                PRINTER.printHeader("MANAGE EVENTS MENU");

                System.out.println("1. View All Events");
                System.out.println("2. Delete Event");
                System.out.println("3. Block Event [Cannot be booked further]");
                System.out.println("4. Unblock Event");
                System.out.println("5. Cancel Event [Refunds booked tickets]");
                System.out.println("6. Approve Event Creation Request");
                System.out.println("7. Update Event");
                System.out.println("8. View Blocked Events");
                System.out.println("9. View Cancelled Events");
                System.out.println("10. Back to Admin Menu");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        eventController.viewEvents();
                        break;
                    case 2:
                        List<EventModel> eventsForDeletion = eventController.viewEvents();
                        if (!eventsForDeletion.isEmpty()) {
                            eventController.deleteEvent();
                        } else {
                            System.out.println("No events available to delete.");
                        }
                        break;
                    case 3:
                        List<EventModel> eventsToBlock = eventController.viewEvents();
                        if (!eventsToBlock.isEmpty()) {
                            eventController.blockEvent();
                        } else {
                            System.out.println("No events available to block.");
                        }
                        break;
                    case 4:
                        List<EventModel> eventsToUnblock = eventController.viewBlockedEvents();
                        if (!eventsToUnblock.isEmpty()) {
                            eventController.approveEvents();
                        } else {
                            System.out.println("No blocked events available to unblock.");
                        }
                        break;
                    case 5:
                        List<EventModel> eventsToCancel = eventController.viewEvents();
                        if (!eventsToCancel.isEmpty()) {
                            eventController.cancelEvent();
                        } else {
                            System.out.println("No events available to cancel.");
                        }
                        break;
                    case 6:
                        List<EventModel> blockedEventsForApproval = eventController.viewBlockedEvents();
                        if (!blockedEventsForApproval.isEmpty()) {
                            eventController.approveEvents();
                        } else {
                            System.out.println("No blocked events available for approval.");
                        }
                        break;
                    case 7:
                        List<EventModel> eventsToUpdate = eventController.viewEvents();
                        if (!eventsToUpdate.isEmpty()) {
                            eventController.updateEvent();
                        } else {
                            System.out.println("No events available to update.");
                        }
                        break;
                    case 8:
                        eventController.viewBlockedEvents();
                        break;
                    case 9:
                        eventController.viewCancelledEvents();
                        break;
                    case 10:
                        System.out.println("Returning to previous menu...");
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



