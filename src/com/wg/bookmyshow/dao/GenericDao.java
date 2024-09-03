//package com.wg.bookmyshow.dao;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.wg.bookmyshow.config.DBConnection;
//import com.wg.bookmyshow.model.AccountModel;
//
//     
//    public abstract class GenericDao<T> {
//     
//    	private Connection connection;
//     
//    	public GenericDao() throws SQLException {
//    		try {
//    			this.connection = DBConnection.getConnection();
//    		} catch (ClassNotFoundException e) {
//    			e.printStackTrace();
//    		}
//    	}
//     
//    	protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;
//     
//    	public T executeGetQuery(String query) throws SQLException, ClassNotFoundException {
//    		T entity = null;
//    		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//    			ResultSet resultSet = preparedStatement.executeQuery();
//    			System.out.println("here1");
//     
//    			if (resultSet.next()) {
//    				entity = mapResultSetToEntity(resultSet);
//    				System.out.println("mapping");
//    			}
//    			System.out.println("here2");
//    		}
//    		return entity;
//    	}
//     
//    	public List<T> executeGetAllQuery(String query) throws SQLException, ClassNotFoundException {
//    		List<T> entities = new ArrayList<>();
//    		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//    			ResultSet resultSet = preparedStatement.executeQuery();
//     
//    			while (resultSet.next()) {
//    				entities.add(mapResultSetToEntity(resultSet));
//    			}
//    		}
//    		return entities;
//    	}
//     
//    	public boolean executeQuery(String query) throws SQLException, ClassNotFoundException {
//    		System.out.println(query);
//    		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//    			return preparedStatement.executeUpdate() > 0;
//    		}
//    	}
//
//		protected void prepareStatementForInsert(PreparedStatement preparedStatement, AccountModel account)
//				throws SQLException {
//			// TODO Auto-generated method stub
//			
//		}
//
//		protected void prepareStatementForUpdate(PreparedStatement preparedStatement, AccountModel account)
//				throws SQLException {
//			// TODO Auto-generated method stub
//			
//		}
//
//		protected String getPlaceholders() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		protected String getUpdateFields() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//    }
package com.wg.bookmyshow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wg.bookmyshow.config.DBConnection;

public abstract class GenericDao<T> {

    protected Connection connection;

    public GenericDao() {
        // Connection will be initialized on demand
    }

    private void ensureConnection() throws SQLException {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DBConnection.getConnection();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Database connection initialization failed", e);
        }
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    public T executeGetQuery(String query, Object... parameters) throws SQLException {
        ensureConnection();
        T entity = null;
        try (PreparedStatement preparedStatement = prepareStatement(query, parameters);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                entity = mapResultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error executing get query", e);
        }
        return entity;
    }

    public List<T> executeGetAllQuery(String query, Object... parameters) throws SQLException {
        ensureConnection();
        List<T> entities = new ArrayList<>();
        try (PreparedStatement preparedStatement = prepareStatement(query, parameters);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error executing get all query", e);
        }
        return entities;
    }

    public boolean executeUpdateQuery(String query, Object... parameters) throws SQLException {
        ensureConnection();
        try (PreparedStatement preparedStatement = prepareStatement(query, parameters)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error executing update query", e);
        }
    }

    protected PreparedStatement prepareStatement(String query, Object... parameters) throws SQLException {
        ensureConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
        return preparedStatement;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}




