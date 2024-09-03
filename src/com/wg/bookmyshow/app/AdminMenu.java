package com.wg.bookmyshow.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.wg.bookmyshow.controller.AccountController;
import com.wg.bookmyshow.util.Printer;

public class AdminMenu {
    private static final Printer PRINTER = new Printer();

    public void showAdminMenu() throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        AccountController accountController = new AccountController();
        final ManageAccounts manageAccounts = new ManageAccounts();
        final ManageEvents manageEvents = new ManageEvents();

        while (true) {
            try {
                PRINTER.printHeader("ADMIN MENU");

                System.out.println("Admin Menu:");
                System.out.println("1. Manage Accounts");
                System.out.println("2. Manage Events");
                System.out.println("3. Logout");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        manageAccounts.manageAccountsMenu();
                        break;
                    case 2:
                        manageEvents.manageEventsMenu();
                        break;
                    case 3:
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




