package com.wg.bookmyshow.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.wg.bookmyshow.util.Printer;
import com.wg.bookmyshow.controller.AccountController;

public class AccountMenu {
    private static final Printer PRINTER = new Printer();

    public static void AccountMenuDisplay() throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        AccountController accountController = new AccountController();

        while (true) {
            try {
                PRINTER.printHeader("WELCOME TO BOOKING MANAGEMENT SYSTEM");

                System.out.println("1. Create Account");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        accountController.createAccount();
                        break;
                    case 2:
                        accountController.login();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            } catch (Exception e) {
            	System.out.println("Inside accountmenu");
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}


