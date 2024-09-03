package com.wg.bookmyshow.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.wg.bookmyshow.app.AdminMenu;
import com.wg.bookmyshow.app.OrganizerMenu;
import com.wg.bookmyshow.app.UserMenu;
import com.wg.bookmyshow.dao.AccountDao;
import com.wg.bookmyshow.model.AccountModel;
import com.wg.bookmyshow.util.PasswordHelper;

public class AccountService {

    private final AccountDao accountDao;

    public AccountService() throws SQLException {
        this.accountDao = new AccountDao();
    }

    public boolean createAccount(String name, String username, String password, String email, String phoneNumber, String role, int age, boolean blocked) throws SQLException, ClassNotFoundException {
        // Validate input fields
        if (name == null || name.trim().isEmpty() ||
            username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            phoneNumber == null || phoneNumber.trim().isEmpty() ||
            role == null || role.trim().isEmpty() ||
            age <= 0) {
            System.err.println("Invalid input. Please provide valid account details.");
            return false;
        }

        AccountModel account = new AccountModel();
        account.setName(name);
        account.setUsername(username);
        account.setPassword(PasswordHelper.hashPassword(password)); // Hash password before storing
        account.setEmail(email);
        account.setPhoneNumber(phoneNumber);
        account.setRole(role);
        account.setAge(age);
        account.setBlocked(blocked);

        try {
            return accountDao.createAccount(account);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public AccountModel login(String username, String password) throws ClassNotFoundException, SQLException {
        // Validate input fields
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            System.err.println("Username and password cannot be empty.");
            return null;
        }

        try {
            AccountModel account = accountDao.findByUsername(username);
            
           // String HashedPassword=PasswordHelper.hashPassword(password);
          
            if (account != null && PasswordHelper.checkPassword(password, account.getPassword())) {
                //System.out.println(account);
            	// Set organizerId if the role is organizer
                if ("organizer".equals(account.getRole())) {
                    account.setOrganizerId(AccountModel.getAccountId());
                }
                return account;
            }
            System.err.println("Invalid username or password.");
            return null;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void navigateBasedOnRole(AccountModel account) throws SQLException, ClassNotFoundException {
        String role = account.getRole();
        switch (role) {
            case "admin":
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.showAdminMenu();
                break;
            case "user":
                UserMenu userMenu = new UserMenu();
                userMenu.showUserMenu();
                break;
            case "organizer":
                OrganizerMenu organizerMenu = new OrganizerMenu();
                organizerMenu.showOrganizerMenu();
                break;
            default:
                System.out.println("Unknown role. Access denied.");
        }
    }

    // Update an existing account
    public boolean updateAccount(AccountModel account) {
        // Validate input fields
        if (account == null || account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            System.err.println("Invalid account details. Cannot update account.");
            return false;
        }

        try {
            return accountDao.updateAccount(account);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while updating the account: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccount(String username, String field, String value) {
        // Validate input fields
    	//System.out.println("in");
        if (username == null || username.trim().isEmpty() ||
            field == null || field.trim().isEmpty() ||
            value == null || value.trim().isEmpty()) {
            System.err.println("Invalid input. Please provide valid details.");
            return false;
        }

        try {
            boolean success;
            if (field.equals("username")) {
                success = accountDao.updateAccountUsername(username, value);
            } else if (field.equals("account_password")) {
                success = accountDao.updateAccountPassword(username, PasswordHelper.hashPassword(value)); // Hash password before updating
            } else {
                success = updateAccountDetail(username, field, value);
            }
            return success;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while updating the account: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccountDetail(String username, String field, String value) {
        // Validate input fields
        if (username == null || username.trim().isEmpty() ||
            field == null || field.trim().isEmpty() ||
            value == null || value.trim().isEmpty()) {
            System.err.println("Invalid input. Please provide valid details.");
            return false;
        }

        try {
            boolean success = false;

            if (field.equals("username")) {
                success = accountDao.updateAccountUsername(username, value);
            } else if (field.equals("account_password")) {
                success = accountDao.updateAccountPassword(username, PasswordHelper.hashPassword(value)); // Hash password before updating
            } else if (field.equals("account_name") || field.equals("email") || field.equals("phone_number") || field.equals("account_role") || field.equals("age") || field.equals("blocked")) {
                // Only valid fields for this update method
                AccountModel account = accountDao.findByUsername(username);
                if (account != null) {
                    // Update the specific field
                    if (field.equals("account_name")) {
                        account.setName(value);
                    } else if (field.equals("email")) {
                        account.setEmail(value);
                    } else if (field.equals("phone_number")) {
                        account.setPhoneNumber(value);
                    } else if (field.equals("account_role")) {
                        account.setRole(value);
                    } else if (field.equals("age")) {
                        try {
                            account.setAge(Integer.parseInt(value));
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid age format.");
                            return false;
                        }
                    } else if (field.equals("blocked")) {
                        account.setBlocked(Boolean.parseBoolean(value));
                    }
                    success = accountDao.updateAccount(account);
                }
            } else {
                System.err.println("Invalid field name.");
            }

            return success;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while updating the account: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    // Delete an account by username
    public boolean deleteAccount(String username) throws ClassNotFoundException, SQLException {
        if (username == null || username.trim().isEmpty()) {
            System.err.println("Invalid account username. Cannot delete account.");
            return false;
        }

        try {
            AccountModel account = accountDao.findByUsername(username);
            if (account != null && "admin".equalsIgnoreCase(account.getRole())) {
                System.err.println("Admin cannot be deleted");
                return false;
            }

            boolean success = accountDao.deleteAccount(username);
            if (success) {
                System.out.println("Account deleted successfully.");
            } else {
                System.out.println("Failed to delete account.");
            }
            return success;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while deleting the account: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<AccountModel> getAllUsers() throws ClassNotFoundException, SQLException {
        try {
            return accountDao.getAllAccounts();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while fetching user accounts: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean changeBlockedStatus(String username, boolean blocked) throws ClassNotFoundException, SQLException {
        if (username == null || username.isEmpty()) {
            return false;
        }
        
        AccountModel account=accountDao.findByUsername(username);
        String role= account.getRole();
        if ("admin".equalsIgnoreCase(role)) {
            System.err.println("Cannot change the blocked status for users with the 'admin' role.");
            return false;
        }

        try {
            return accountDao.updateBlockedStatus(username, blocked);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while changing the blocked status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<AccountModel> getAllOrganizers() {
        try {
            return accountDao.getAllOrganizers();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while fetching organizers: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<AccountModel> getBlockedAccounts() throws SQLException, ClassNotFoundException {
        return accountDao.getBlockedAccounts();
    }

    public AccountModel getAccountByUsername(String username) throws ClassNotFoundException, SQLException {
        return accountDao.findByUsername(username);
    }
    public int getCountOfBlockedAccounts() throws ClassNotFoundException, SQLException {
        return accountDao.countBlockedAccounts();
    }

    public boolean doesAccountExist(String username) {
        try {
            // Fetch the account by username
            AccountModel account = accountDao.findByUsername(username);
            // Return true if the account exists, false otherwise
            return account != null;
        } catch (Exception e) {
            // Handle any exceptions (e.g., database errors)
            System.err.println("An error occurred while checking if the account exists: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}






