//package com.wg.bookmyshow.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import com.wg.bookmyshow.config.DBConnection;
//import com.wg.bookmyshow.model.AccountModel;
//import com.wg.bookmyshow.util.PasswordHelper;
//
//public class AccountDao extends GenericDao<AccountModel> {
//
//    public AccountDao() throws SQLException {
//        super();
//    }
//
//    @Override
//    protected AccountModel mapResultSetToEntity(ResultSet resultSet) throws SQLException {
//        AccountModel account = new AccountModel();
//        account.setAccountId(resultSet.getString("account_id"));
//        account.setName(resultSet.getString("account_name"));
//        account.setUsername(resultSet.getString("username"));
//        account.setPassword(resultSet.getString("account_password"));
//        account.setEmail(resultSet.getString("email"));
//        account.setPhoneNumber(resultSet.getString("phone_number"));
//        account.setRole(resultSet.getString("account_role"));
//        account.setAge(resultSet.getInt("age"));
//        account.setBlocked(resultSet.getBoolean("blocked"));
//        return account;
//    }
//
//    public boolean createAccount(AccountModel account) throws SQLException, ClassNotFoundException {
//        String query = "INSERT INTO account (account_id, account_name, username, account_password, email, phone_number, account_role, age, blocked) " +
//                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement preparedStatement =DBConnection.getConnection().prepareStatement(query)) {
//            prepareStatementForInsert(preparedStatement, account);
//            return preparedStatement.executeUpdate() > 0;
//        }
//    }
//
//    @Override
//    protected void prepareStatementForInsert(PreparedStatement preparedStatement, AccountModel account) throws SQLException {
//        preparedStatement.setString(1, account.getAccountId());
//        preparedStatement.setString(2, account.getName());
//        preparedStatement.setString(3, account.getUsername());
//        preparedStatement.setString(4, account.getPassword());
//        preparedStatement.setString(5, account.getEmail());
//        preparedStatement.setString(6, account.getPhoneNumber());
//        preparedStatement.setString(7, account.getRole());
//        preparedStatement.setInt(8, account.getAge());
//        preparedStatement.setBoolean(9, account.isBlocked());
//    }
//
//    public boolean updateAccount(AccountModel account) throws SQLException, ClassNotFoundException {
//        String query = "UPDATE account SET account_name = ?, account_password = ?, email = ?, phone_number = ?, account_role = ?, age = ?, blocked = ? " +
//                       "WHERE username = ?";
//        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
//            prepareStatementForUpdate(preparedStatement, account);
//            preparedStatement.setString(8, account.getUsername());
//            return preparedStatement.executeUpdate() > 0;
//        }
//    }
//
//    @Override
//    protected void prepareStatementForUpdate(PreparedStatement preparedStatement, AccountModel account) throws SQLException {
//        preparedStatement.setString(1, account.getName());
//        preparedStatement.setString(2, account.getPassword());
//        preparedStatement.setString(3, account.getEmail());
//        preparedStatement.setString(4, account.getPhoneNumber());
//        preparedStatement.setString(5, account.getRole());
//        preparedStatement.setInt(6, account.getAge());
//        preparedStatement.setBoolean(7, account.isBlocked());
//    }
//
//    public boolean updateAccountUsername(String oldUsername, String newUsername) throws SQLException, ClassNotFoundException {
//        String query = "UPDATE account SET username = ? WHERE username = ?";
//        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
//            preparedStatement.setString(1, newUsername);
//            preparedStatement.setString(2, oldUsername);
//            return preparedStatement.executeUpdate() > 0;
//        }
//    }
//
//    public boolean updateAccountPassword(String username, String newPassword) throws SQLException, ClassNotFoundException {
//        String query = "UPDATE account SET account_password = ? WHERE username = ?";
//        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
//            preparedStatement.setString(1, PasswordHelper.hashPassword(newPassword));
//            preparedStatement.setString(2, username);
//            return preparedStatement.executeUpdate() > 0;
//        }
//    }
//
//    public boolean deleteAccount(String username) throws SQLException, ClassNotFoundException {
//        String query = "DELETE FROM account WHERE username = ?";
//        try (PreparedStatement preparedStatement =DBConnection.getConnection().prepareStatement(query)) {
//            preparedStatement.setString(1, username);
//            return preparedStatement.executeUpdate() > 0;
//        }
//    }
//
//    public AccountModel findByUsername(String username) throws SQLException, ClassNotFoundException {
//        String query = "SELECT * FROM account WHERE username = ?";
//        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
//            preparedStatement.setString(1, username);
//            return executeGetQuery(query);
//        }
//    }
//
//    public List<AccountModel> getAllAccounts() throws SQLException, ClassNotFoundException {
//        String query = "SELECT * FROM account";
//        return executeGetAllQuery(query);
//    }
//
//    public List<AccountModel> getAllOrganizers() throws SQLException, ClassNotFoundException {
//        String query = "SELECT * FROM account WHERE account_role = 'organizer'";
//        return executeGetAllQuery(query);
//    }
//
//    public List<AccountModel> getBlockedAccounts() throws SQLException, ClassNotFoundException {
//        String query = "SELECT * FROM account WHERE blocked = true";
//        return executeGetAllQuery(query);
//    }
//
//    public boolean updateBlockedStatus(String username, boolean blocked) throws SQLException, ClassNotFoundException {
//        String query = "UPDATE account SET blocked = ? WHERE username = ?";
//        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
//            preparedStatement.setBoolean(1, blocked);
//            preparedStatement.setString(2, username);
//            return preparedStatement.executeUpdate() > 0;
//        }
//    }
//}
package com.wg.bookmyshow.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wg.bookmyshow.config.DBConnection;
import com.wg.bookmyshow.model.AccountModel;
import com.wg.bookmyshow.util.PasswordHelper;

public class AccountDao extends GenericDao<AccountModel> {

    public AccountDao() throws SQLException {
        super();
    }

    @Override
    protected AccountModel mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        AccountModel account = new AccountModel();
        account.setAccountId(resultSet.getString("account_id"));
        account.setName(resultSet.getString("account_name"));
        account.setUsername(resultSet.getString("username"));
        account.setPassword(resultSet.getString("account_password"));
        account.setEmail(resultSet.getString("email"));
        account.setPhoneNumber(resultSet.getString("phone_number"));
        account.setRole(resultSet.getString("account_role"));
        account.setAge(resultSet.getInt("age"));
        account.setBlocked(resultSet.getBoolean("blocked"));
        return account;
    }

    @Override
    public boolean executeUpdateQuery(String query, Object... parameters) throws SQLException {
        try (PreparedStatement preparedStatement = prepareStatement(query, parameters)) {
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public boolean createAccount(AccountModel account) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO account (account_id, account_name, username, account_password, email, phone_number, account_role, age, blocked) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return executeUpdateQuery(query, 
                                  account.getAccountId(), 
                                  account.getName(), 
                                  account.getUsername(), 
                                  account.getPassword(), 
                                  account.getEmail(), 
                                  account.getPhoneNumber(), 
                                  account.getRole(), 
                                  account.getAge(), 
                                  account.isBlocked());
    }

    public boolean updateAccount(AccountModel account) throws SQLException, ClassNotFoundException {
        String query = "UPDATE account SET account_name = ?, account_password = ?, email = ?, phone_number = ?, account_role = ?, age = ?, blocked = ? " +
                       "WHERE username = ?";
        return executeUpdateQuery(query, 
                                  account.getName(), 
                                  account.getPassword(), 
                                  account.getEmail(), 
                                  account.getPhoneNumber(), 
                                  account.getRole(), 
                                  account.getAge(), 
                                  account.isBlocked(), 
                                  account.getUsername());
    }

    public boolean updateAccountUsername(String oldUsername, String newUsername) throws SQLException, ClassNotFoundException {
        String query = "UPDATE account SET username = ? WHERE username = ?";
        return executeUpdateQuery(query, newUsername, oldUsername);
    }

    public boolean updateAccountPassword(String username, String newPassword) throws SQLException, ClassNotFoundException {
        String query = "UPDATE account SET account_password = ? WHERE username = ?";
        return executeUpdateQuery(query, PasswordHelper.hashPassword(newPassword), username);
    }

    public boolean deleteAccount(String username) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM account WHERE username = ?";
        return executeUpdateQuery(query, username);
    }

    public AccountModel findByUsername(String username) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM account WHERE username = ?";
        return executeGetQuery(query, username);
    }

    public List<AccountModel> getAllAccounts() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM account";
        return executeGetAllQuery(query);
    }

    public List<AccountModel> getAllOrganizers() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM account WHERE account_role = 'organizer'";
        return executeGetAllQuery(query);
    }

    public List<AccountModel> getBlockedAccounts() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM account WHERE blocked = true";
        return executeGetAllQuery(query);
    }

    public boolean updateBlockedStatus(String username, boolean blocked) throws SQLException, ClassNotFoundException {
        String query = "UPDATE account SET blocked = ? WHERE username = ?";
        return executeUpdateQuery(query, blocked, username);
    }
    public int countBlockedAccounts() throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) AS blocked_count FROM account WHERE blocked = true";
        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("blocked_count");
            }
        }
        return 0;
    }
    
}





