

package com.wg.bookmyshow.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.wg.bookmyshow.controller.AccountController;
import com.wg.bookmyshow.model.AccountModel;
import com.wg.bookmyshow.util.Printer;

public class ManageAccounts {
    private static final Printer PRINTER = new Printer(); 
    private final AccountController accountController;

    public ManageAccounts() throws SQLException {
        this.accountController = new AccountController();
    }

    public void manageAccountsMenu() throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                PRINTER.printHeader("MANAGE ACCOUNTS MENU");

                System.out.println("1. Create Account");
                System.out.println("2. Delete Account");
                System.out.println("3. Update Account");
                System.out.println("4. Block Account");
                System.out.println("5. Unblock Account");
                System.out.println("6. Approve Account Creation Request");
                System.out.println("7. View All Accounts");
                System.out.println("8. View All Organizer Accounts");
                System.out.println("9. View Blocked Accounts");
                System.out.println("10. Search Account");
                System.out.println("11. View My Account Details");
                System.out.println("12. Back to Admin Menu");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        accountController.createAccount();
                        break;
                    case 2:
                        // Check if there are any user accounts to delete
                        List<AccountModel> userAccounts = accountController.viewAllUserAccounts();
                        if (!userAccounts.isEmpty()) {
                            accountController.deleteAccount();
                        } else {
                            System.out.println("No user accounts available to delete.");
                        }
                        break;
                    case 3:
                        // Check if there are any user accounts to update
                        List<AccountModel> updateAccounts = accountController.viewAllUserAccounts();
                        if (!updateAccounts.isEmpty()) {
                            accountController.updateAccount();
                        } else {
                            System.out.println("No user accounts available to update.");
                        }
                        break;
                    case 4:
                        // Check if there are any user accounts to block
                        List<AccountModel> blockAccounts = accountController.viewAllUserAccounts();
                        if (!blockAccounts.isEmpty()) {
                            accountController.blockAccount();
                        } else {
                            System.out.println("No user accounts available to block.");
                        }
                        break;
                    case 5:
                        // Check if there are any blocked accounts to unblock
                        List<AccountModel> blockedAccounts = accountController.viewBlockedAccounts();
                        if (!blockedAccounts.isEmpty()) {
                            accountController.approveRequest();
                        } else {
                            System.out.println("No blocked accounts available to unblock.");
                        }
                        break;
                    case 6:
                        // Check if there are any blocked accounts to approve
                        List<AccountModel> approveAccounts = accountController.viewBlockedAccounts();
                        if (!approveAccounts.isEmpty()) {
                            accountController.approveRequest();
                        } else {
                            System.out.println("No blocked accounts available to approve.");
                        }
                        break;
                    case 7:
                        accountController.viewAllUserAccounts();
                        break;
                    case 8:
                        accountController.viewAllOrganizerAccounts();
                        break;
                    case 9:
                        accountController.viewBlockedAccounts();
                        break;
                    case 10:
                        // Check if there are any user accounts to search
                        List<AccountModel> searchAccounts = accountController.viewAllUserAccounts();
                        if (!searchAccounts.isEmpty()) {
                            accountController.viewAccountByUsername();
                        } else {
                            System.out.println("No user accounts available to search.");
                        }
                        break;
                    case 11:
                        accountController.viewMyAccount();
                        break;
                    case 12:
                        System.out.println("Returning to Admin Menu...");
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




