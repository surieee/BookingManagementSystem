package com.wg.bookmyshow.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.wg.bookmyshow.dao.NotificationDao;
import com.wg.bookmyshow.model.AccountModel;
import com.wg.bookmyshow.service.AccountService;
import com.wg.bookmyshow.service.EventService;
import com.wg.bookmyshow.service.NotificationService;
import com.wg.bookmyshow.util.AccountBlockedException;
import com.wg.bookmyshow.util.AccountPrinter;
import com.wg.bookmyshow.util.ConsoleUtils;
import com.wg.bookmyshow.util.FieldSanitizer;
import com.wg.bookmyshow.util.FieldValidator;
import com.wg.bookmyshow.util.FileLogging;
import com.wg.bookmyshow.util.LoggingHelper;
import com.wg.bookmyshow.util.LoginException;
import com.wg.bookmyshow.util.PasswordHelper;
import com.wg.bookmyshow.util.ValidationException;
import java.util.logging.Logger;
import java.util.logging.Level;
public class AccountController {
	 
    private final AccountService accountService;
    private final EventService eventService;
    private final NotificationService notificationService; 
    public AccountController() throws SQLException{
    	this.accountService= new AccountService();
		this.eventService = new EventService();
    	this.notificationService= new NotificationService();
    }
    
    private final Scanner scanner = new Scanner(System.in);
    public static String loggedInAccountId;
    static String loggedInUsername;
    private static Logger logger = FileLogging.getLogger(AccountController.class); 
  

     
    
        private final FieldValidator fieldValidator = new FieldValidator(); // Assuming this is initialized
        private final PasswordHelper passwordHelper = new PasswordHelper(); // Assuming this is initialized

        public void createAccount() throws ClassNotFoundException, SQLException {
            String name, username, password, email, phoneNumber, role;
            int age;

            // Name input with sanitization and validation
            while (true) {
                System.out.print("Enter name (alphabetic characters and spaces only): ");
                name = FieldSanitizer.sanitizeName(scanner.nextLine().trim());
                try {
                    if (FieldValidator.validateName(name)) {
                        break;
                    }
                } catch (ValidationException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                    logger.log(Level.WARNING, "Invalid name input: {0}", e.getMessage());
                }
            }

            // Username input with sanitization and validation
            while (true) {
                System.out.print("Enter username (alphanumeric characters and underscores only): ");
                username = FieldSanitizer.sanitizeUsername(scanner.nextLine().trim());
                try {
                    if (FieldValidator.validateUsername(username)) {
                        break;
                    }
                } catch (ValidationException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                    logger.log(Level.WARNING, "Invalid username input: {0}", e.getMessage());
                }
            }

            // Password input with validation
            while (true) {
            	System.out.print("Enter your password (must be at least 8 characters long, including at least 1 uppercase letter, 1 number, and 1 special character): ");

            	password = PasswordHelper.getPasswordFromUser();
                try {
                    if (FieldValidator.validatePassword(password)) {
                        break;
                    }
                } catch (ValidationException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                    logger.log(Level.WARNING, "Invalid password input: {0}", e.getMessage());
                    return;
                }
            }

            // Email input with sanitization and validation
            while (true) {
                System.out.print("Enter email (valid email format): ");
                email = FieldSanitizer.sanitizeEmail(scanner.nextLine().trim());
                try {
                    if (FieldValidator.validateEmail(email)) {
                        break;
                    }
                } catch (ValidationException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                    logger.log(Level.WARNING, "Invalid email input: {0}", e.getMessage());
                }
            }

            // Phone number input with sanitization and validation
            while (true) {
                System.out.print("Enter phone number (digits only, optional international code): ");
                phoneNumber = FieldSanitizer.sanitizePhoneNumber(scanner.nextLine().trim());
                try {
                    if (FieldValidator.validatePhoneNumber(phoneNumber)) {
                        break;
                    }
                } catch (ValidationException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                    logger.log(Level.WARNING, "Invalid phone number input: {0}", e.getMessage());
                }
            }

            // Role input with sanitization and validation
            while (true) {
                System.out.print("Enter role (user/organizer): ");
                role = FieldSanitizer.sanitizeRole(scanner.nextLine().trim());
                if (role.equalsIgnoreCase("admin")) {
                    System.err.println("Invalid Role");
                    return;
                }
                try {
                    if (FieldValidator.validateRole(role)) {
                        break;
                    }
                } catch (ValidationException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                    logger.log(Level.WARNING, "Invalid role input: {0}", e.getMessage());
                }
            }

            // Age input with validation
            while (true) {
                System.out.print("Enter age (positive integer only): ");
                try {
                    age = Integer.parseInt(scanner.nextLine().trim());
                    if (FieldValidator.validateAge(age)) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Age must be a number.");
                    logger.log(Level.WARNING, "Invalid age input: {0}", e.getMessage());
                } catch (ValidationException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                    logger.log(Level.WARNING, "Invalid age input: {0}", e.getMessage());
                }
            }

            // Determine if account should be blocked based on role
            boolean blocked = role.equals("user") || role.equals("organizer");

            // Attempt to create the account
            try {
                boolean isCreated = accountService.createAccount(name, username, password, email, phoneNumber, role, age, blocked);
                if (isCreated) {
                    System.out.println("Account created successfully!");
                    logger.log(Level.INFO, "Account created successfully for username: {0}", username);
                } else {
                    System.out.println("Failed to create account. Please try again.");
                    logger.log(Level.SEVERE, "Failed to create account for username: {0}", username);
                }
            } catch (Exception e) {
                System.out.println("An error occurred while creating the account: " + e.getMessage());
                logger.log(Level.SEVERE, "Error occurred while creating account: {0}", e.getMessage());
            }
        }


//    public void login() throws ClassNotFoundException, SQLException {
//        System.out.print("Enter username: ");
//        String username = scanner.nextLine().trim();
//        System.out.print("Enter password: ");
//        String password = scanner.nextLine().trim();
//
//        // Input validation
//        if (username.isEmpty() || password.isEmpty()) {
//            System.out.println("Username or password cannot be empty.");
//            return;
//        }
//        try {
////        if (FieldValidator.validateUsername(username) && FieldValidator.validatePassword(password)) {
//        // Call service method for login
//        AccountModel account = accountService.login(username, password);
//            loggedInUsername = username;
//            loggedInAccountId = AccountModel.getAccountId();
//           
//			
//            System.out.println("Login successful! Welcome " + account.getName());
//            
//            logger.info("User Authenticated Successfully !\nUsername: " + username + "\n");
//			
//            notificationController.viewHighPriorityNotifications();
//
//            accountService.navigateBasedOnRole(account);
//            // Proceed with role-specific actions
//        } 
//           catch(LoginException e){
//        	   logger.severe("User Authentication Failed!! \n Username: " + username + "\n Password: " + password + "\n" + e.getMessage());
//   			e.printStackTrace();
//        }
//        }
            
        public void login() throws ClassNotFoundException, SQLException {
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            String password = PasswordHelper.getPasswordFromUser();

            // Input validation
            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Username or password cannot be empty.");
                return;
            }

            try {
                // Validate login details with AccountService
                AccountModel account = accountService.login(username, password);

                if (account == null) {
                    throw new LoginException("Invalid username or password.");
                }

                // Check if the account is blocked
                if (account.isBlocked()) { // Assuming you have a method isBlocked() in AccountModel
                    throw new AccountBlockedException("Account is blocked. Please contact support.");
                }

                loggedInUsername = username;
                loggedInAccountId = AccountModel.getAccountId();

                System.out.println("Login successful! Welcome " + account.getName());

                logger.info("User Authenticated Successfully! Username: " + username);
                String role= account.getRole();
                if ("admin".equalsIgnoreCase(role)) {
                    int unapprovedCount = accountService.getCountOfBlockedAccounts();
                    if (unapprovedCount > 0) {
                    	  String message = "You have " + unapprovedCount + " account(s) to approve.";
                          
                      
                          ConsoleUtils.printRoundedBox(message);
                    } else {
                        System.out.println("There are no account(s) to approve.");
                    }
                    int blockedEventsCount = eventService.getCountOfBlockedEvents(); // Assuming this method counts blocked events

                    if (blockedEventsCount > 0) {
                        String message = "You have " + blockedEventsCount + " events that are blocked.";
                        ConsoleUtils.printRoundedBox(message); // Using a utility method to print in a rounded box format
                    } else {
                        System.out.println("There are no blocked events to manage.");
                    }

                }

                NotificationController notificationController = new NotificationController();
                if(role!="admin")
                notificationController.viewHighPriorityNotifications();

                accountService.navigateBasedOnRole(account);

            } catch (AccountBlockedException e) {
                logger.warning("Login failed - Account is blocked: " + e.getMessage());
                System.out.println(e.getMessage());
            } catch (LoginException e) {
                logger.warning("User Authentication Failed! Username: " + username + " Error: " + e.getMessage());
                System.out.println("Invalid username or password. Please try again.");
            } catch (SQLException e) {
                logger.severe("Database error occurred during login: " + e.getMessage());
                System.out.println("An error occurred while accessing the database. Please try again later.");
            } catch (Exception e) {
                logger.severe("Unexpected error during login: " + e.getMessage());
                System.out.println("An unexpected error occurred. Please try again later.");
            }
        }


//	public void createAccount() throws ClassNotFoundException, SQLException {
//        System.out.print("Enter name: ");
//        String name = scanner.nextLine().trim();
//        System.out.print("Enter username: ");
//        String username = scanner.nextLine().trim();
////        System.out.print("Enter password: ");
////        String password = scanner.nextLine().trim();
//        String password = PasswordHelper.getPasswordFromUser();
//        System.out.print("Enter email: ");
//        String email = scanner.nextLine().trim();
//        System.out.print("Enter phone number: ");
//        String phoneNumber = scanner.nextLine().trim();
//        System.out.print("Enter role (admin/user/organizer): ");
//        String role = scanner.nextLine().trim().toLowerCase();
//        System.out.print("Enter age: ");
//        int age = scanner.nextInt();
//        scanner.nextLine(); // consume the remaining newline
//
//        // Input validation
//        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || role.isEmpty()) {
//            System.out.println("All fields are required.");
//            return;
//        }
//        
//        boolean blocked = role.equals("user") || role.equals("organizer");
//
//        // Role validation
//        if (!(role.equals("admin") || role.equals("user") || role.equals("organizer"))) {
//            System.out.println("Invalid role. Please enter admin, user, or organizer.");
//            return;
//        }
//        try {
//        FieldValidator.validateName(name);
//        FieldValidator.validateUsername(username);
//        FieldValidator.validatePassword(password);
//        FieldValidator.validateEmail(email);
//        FieldValidator.validatePhoneNumber(phoneNumber);
//        FieldValidator.validateRole(role);
//        FieldValidator.validateAge(age);
//
//
//        String hashedPassword = PasswordHelper.hashPassword(password);
//        boolean isCreated = accountService.createAccount(name, username, hashedPassword, email, phoneNumber, role, age,blocked);
//        if (isCreated) {
//            System.out.println("Account created successfully!");
//        } else {
//            System.out.println("Failed to create account. Please try again.");
//        }
//        
//    }catch (ValidationException e) {
//        System.out.println("Invalid input: " + e.getMessage());
//    }
//	}
	
	public static String getLoggedInUsername() {
        return loggedInUsername;
    }
	public static String getLoggedInUserId() {
        return loggedInAccountId;
    }


	   public void deleteAccount() throws ClassNotFoundException, SQLException {
	        System.out.print("Enter username of the account to delete: ");
	        String username = scanner.nextLine().trim();
            
	        // Input validation
	        if (username.isEmpty()) {
	            System.out.println("Username cannot be empty.");
	            return;
	        }

	        // Call service method for deleting the account
	        boolean isDeleted = accountService.deleteAccount(username);
	        if (isDeleted) {
	           
	            
	        } else {
	            System.out.println("Failed to delete account. Please try again.");
	        }
	    }
	   
	   public void updateAccount() throws ClassNotFoundException, SQLException {
		    System.out.print("Enter username of the account to update: ");
		    String username = scanner.nextLine().trim();

		    // Input validation
		    if (username.isEmpty()) {
		        System.out.println("Username cannot be empty.");
		        return;
		    }

		    // Check if the account exists before proceeding
		    if (!accountService.doesAccountExist(username)) {
		        System.out.println("Account not found.");
		        return;
		    }

		    // Ask for field to update
		    System.out.println("Which field would you like to update?");
		    System.out.println("1. Username");
		    System.out.println("2. Password");
		    System.out.println("3. Name");
		    System.out.println("4. Email");
		    System.out.println("5. Phone Number");
		    System.out.println("6. Role");
		    System.out.println("7. Age");
		    System.out.println("8. Update All Fields");
		    System.out.print("Choose an option: ");
		    int fieldChoice = scanner.nextInt();
		    scanner.nextLine(); // Consume newline

		    boolean isUpdated = false;

		    switch (fieldChoice) {
		        case 1:
		            System.out.print("Enter new username: ");
		            String newUsername = scanner.nextLine().trim();
		            FieldValidator.validateUsername(newUsername);
		            newUsername = FieldSanitizer.sanitizeUsername(newUsername);
		            isUpdated = accountService.updateAccount(username, "username", newUsername);
		            break;
		        case 2:
		            System.out.print("Enter new password: ");
		            String newPassword = PasswordHelper.getPasswordFromUser();
		           // newPassword = scanner.nextLine().trim();
		            FieldValidator.validatePassword(newPassword);
		            String hashedPassword = PasswordHelper.hashPassword(newPassword); // Assuming a method to hash passwords
		            isUpdated = accountService.updateAccount(username, "account_password", hashedPassword);
		            break;
		        case 3:
		            System.out.print("Enter new name: ");
		            String name = scanner.nextLine().trim();
		            FieldValidator.validateName(name);
		            name = FieldSanitizer.sanitizeName(name);
		            isUpdated = accountService.updateAccount(username, "account_name", name);
		            break;
		        case 4:
		            System.out.print("Enter new email: ");
		            String email = scanner.nextLine().trim();
		            FieldValidator.validateEmail(email);
		            email = FieldSanitizer.sanitizeEmail(email);
		            isUpdated = accountService.updateAccount(username, "email", email);
		            break;
		        case 5:
		            System.out.print("Enter new phone number: ");
		            String phoneNumber = scanner.nextLine().trim();
		            FieldValidator.validatePhoneNumber(phoneNumber);
		            phoneNumber = FieldSanitizer.sanitizePhoneNumber(phoneNumber);
		            isUpdated = accountService.updateAccount(username, "phone_number", phoneNumber);
		            break;
		        case 6:
		            System.out.print("Enter new role (admin/user/organizer): ");
		            String role = scanner.nextLine().trim().toLowerCase();
		            FieldValidator.validateRole(role);
		            role = FieldSanitizer.sanitizeRole(role);
		            isUpdated = accountService.updateAccount(username, "account_role", role);
		            break;
		        case 7:
		            System.out.print("Enter new age: ");
		            int age = scanner.nextInt();
		            scanner.nextLine(); // Consume newline
		            FieldValidator.validateAge(age);
		            isUpdated = accountService.updateAccount(username, "age", String.valueOf(age));
		            break;
		        case 8:
		            // Update all fields
		            System.out.print("Enter new username: ");
		            String updatedUsername = scanner.nextLine().trim();
		            FieldValidator.validateUsername(updatedUsername);
		            updatedUsername = FieldSanitizer.sanitizeUsername(updatedUsername);
		            
		            System.out.print("Enter new password: ");
		            String updatedPassword = scanner.nextLine().trim();
		            FieldValidator.validatePassword(updatedPassword);
		            String updatedHashedPassword = PasswordHelper.hashPassword(updatedPassword);
		            
		            System.out.print("Enter new name: ");
		            String updatedName = scanner.nextLine().trim();
		            FieldValidator.validateName(updatedName);
		            updatedName = FieldSanitizer.sanitizeName(updatedName);
		            
		            System.out.print("Enter new email: ");
		            String updatedEmail = scanner.nextLine().trim();
		            FieldValidator.validateEmail(updatedEmail);
		            updatedEmail = FieldSanitizer.sanitizeEmail(updatedEmail);
		            
		            System.out.print("Enter new phone number: ");
		            String updatedPhoneNumber = scanner.nextLine().trim();
		            FieldValidator.validatePhoneNumber(updatedPhoneNumber);
		            updatedPhoneNumber = FieldSanitizer.sanitizePhoneNumber(updatedPhoneNumber);
		            
		            System.out.print("Enter new role (admin/user/organizer): ");
		            String updatedRole = scanner.nextLine().trim().toLowerCase();
		            FieldValidator.validateRole(updatedRole);
		            updatedRole = FieldSanitizer.sanitizeRole(updatedRole);
		            
		            System.out.print("Enter new age: ");
		            int updatedAge = scanner.nextInt();
		            scanner.nextLine(); // Consume newline
		            FieldValidator.validateAge(updatedAge);
		            
		            // Assume success initially
		            isUpdated = true;

		            // Update username first
		            boolean usernameUpdated = accountService.updateAccount(username, "username", updatedUsername);
		            if (!usernameUpdated) {
		                System.out.println("Failed to update username. Other fields will not be updated.");
		                isUpdated = false;
		                break;
		            }

		            // Update other fields only if username update is successful
		            boolean otherFieldsUpdated = accountService.updateAccount(updatedUsername, "account_password", updatedHashedPassword)
		                    && accountService.updateAccount(updatedUsername, "account_name", updatedName)
		                    && accountService.updateAccount(updatedUsername, "email", updatedEmail)
		                    && accountService.updateAccount(updatedUsername, "phone_number", updatedPhoneNumber)
		                    && accountService.updateAccount(updatedUsername, "account_role", updatedRole)
		                    && accountService.updateAccount(updatedUsername, "age", String.valueOf(updatedAge));

		            if (!otherFieldsUpdated) {
		                System.out.println("Failed to update one or more fields. Rolling back changes.");
		                isUpdated = false;
		                // Optionally, handle rollback logic here
		            }
		            break;
		        default:
		            System.out.println("Invalid choice.");
		            isUpdated = false; // Set isUpdated to false on invalid choice
		            break;
		    }

		    if (isUpdated) {
		        System.out.println("Account updated successfully.");
		    } else {
		        System.out.println("Failed to update account. Please try again.");
		    }
		}





	    public void viewAccountByUsername() {
	    	System.out.print("Enter username of the account to search: ");
	        String username = scanner.nextLine().trim();
	    	try {
	    		 AccountModel account = accountService.getAccountByUsername(username);
	            if (account != null) {
	                AccountPrinter.printAccount(account);
	            } else {
	                System.out.println("No account found with username: " + username);
	            }
	        } catch (ClassNotFoundException | SQLException e) {
	            System.err.println("An error occurred while fetching the account: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    public void viewMyAccount() {
	    	
	        String username = loggedInUsername;
	    	try {
	    		 AccountModel account = accountService.getAccountByUsername(username);
	            if (account != null) {
	                AccountPrinter.printAccount(account);
	            } else {
	                System.out.println("No account found with username: " + username);
	            }
	        } catch (ClassNotFoundException | SQLException e) {
	            System.err.println("An error occurred while fetching the account: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
//		public void viewAllUserAccounts() throws ClassNotFoundException, SQLException {
//	        // Call service method to retrieve and display all user accounts
//	        List<AccountModel> users = accountService.getAllUsers();
//	        if (users.isEmpty()) {
//	            System.out.println("No users found.");
//	        } else {
//	        	AccountPrinter.printAccounts(users);
//	        }
//	    }
	    public List<AccountModel> viewAllUserAccounts() throws ClassNotFoundException, SQLException {
	        // Call service method to retrieve and display all user accounts
	        List<AccountModel> users = accountService.getAllUsers();
	        
	        if (users.isEmpty()) {
	            System.out.println("No users found.");
	        } else {
	            AccountPrinter.printAccounts(users);
	        }
	        
	        return users; // Return the list of users
	    }


	    public void viewAllOrganizerAccounts() throws ClassNotFoundException, SQLException {
	        // Call service method to retrieve and display all organizer accounts
	        List<AccountModel> organizers = accountService.getAllOrganizers();
	        if (organizers.isEmpty()) {
	            System.out.println("No organizers found.");
	        } else {
	           
	            	AccountPrinter.printAccounts(organizers);
	            
	        }
	    }



	    public void blockAccount() throws ClassNotFoundException, SQLException {
	        System.out.print("Enter username of the account to change block status: ");
	        String username = scanner.nextLine().trim();

	        // Input validation
	        if (username.isEmpty()) {
	            System.out.println("Username cannot be empty.");
	            return;
	        }
	        

	        System.out.print("Do you want to block (true/false)? ");
	        boolean blockStatus = scanner.nextBoolean();
	        scanner.nextLine(); // consume the newline

	        // Call service method to change the blocked status
	        boolean statusChanged = accountService.changeBlockedStatus(username, blockStatus);
	        if (statusChanged) {
	            System.out.println("Account status changed successfully.");
	        } else {
	            System.out.println("Failed to change account status. Please try again.");
	        }
	    }


	    public void approveRequest() throws ClassNotFoundException, SQLException {
	        System.out.print("Enter username of the account to approve: ");
	        String username = scanner.nextLine().trim();

	        // Input validation
	        if (username.isEmpty()) {
	            System.out.println("Username cannot be empty.");
	            return;
	        }

	        // Call service method to set blocked status to false (approve)
	        boolean isApproved = accountService.changeBlockedStatus(username, false);
	        if (isApproved) {
	            System.out.println("Account approved successfully.");

	            // Generate notification message
	            String notificationMessage = "Your account has been approved.";

	           // AccountModel account = getAccountByUsername(username); // Method to get account_id
	            String accountId= AccountModel.getAccountId();
	            if (accountId == null) {
	                System.out.println("No account found for username: " + username);
	                return;
	            }
	            // Create and send notification
	            boolean notificationSent = notificationService.approvalNotification( notificationMessage, "HIGH",accountId);
	            if (notificationSent) {
	                System.out.println("Notification sent successfully.");
	            } else {
	                System.out.println("Failed to send notification.");
	            }
	        } else {
	            System.out.println("Failed to approve account. Please try again.");
	        }
	    }

//	    public void viewBlockedAccounts() throws ClassNotFoundException, SQLException {
//	        List<AccountModel> blockedAccounts = accountService.getBlockedAccounts();
//
//			if (blockedAccounts.isEmpty()) {
//			    System.out.println("No blocked accounts found.");
//			} else {
//			    AccountPrinter.printAccounts(blockedAccounts);
//			}
//	    }
	    public List<AccountModel> viewBlockedAccounts() throws ClassNotFoundException, SQLException {
	        List<AccountModel> blockedAccounts = accountService.getBlockedAccounts();

	        if (blockedAccounts.isEmpty()) {
	            System.out.println("No blocked accounts found.");
	        } else {
	            AccountPrinter.printAccounts(blockedAccounts);
	        }

	        return blockedAccounts; // Return the list of blocked accounts
	    }


}
