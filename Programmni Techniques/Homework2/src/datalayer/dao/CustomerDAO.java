package datalayer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.AbstractUser;
import model.Admin;
import model.Customer;

public class CustomerDAO {
	private static final String ADMIN_TABLE = "admin";
	private static final String ADMIN_TABLE_USER = "user";
	private static final String ADMIN_TABLE_PASSWORD = "password";
	
	private static final String QUERY_ADMIN = "SELECT" + " " + ADMIN_TABLE_USER + "," + " " + ADMIN_TABLE_PASSWORD + " " + "FROM" + " " + ADMIN_TABLE;
	
	private static final String CUSTOMER_TABLE = "customer";
	private static final String CUSTOMER_TABLE_NAME = "name";
	private static final String CUSTOMER_TABLE_PASSWORD = "password";
	private static final String CUSTOMER_TABLE_BALANCE = "balance";
	private static final String QUERY_CUSTOMER = "SELECT" + " " + CUSTOMER_TABLE_NAME + "," + " " + CUSTOMER_TABLE_PASSWORD + "," + " " + CUSTOMER_TABLE_BALANCE + " " + "FROM" + " " + CUSTOMER_TABLE;

	Connection connection;

	public CustomerDAO(Connection connection) {
		this.connection = connection;
	}
	
	public Customer getSpecificCustomer(String name) {
		List<Customer> users = getCustomer();
		for (AbstractUser au : users) {
			if (au.getName().equals(name))
				return (Customer) au;
		}
		return null;
	}

	public List<Admin> getAdmin() {
		List<String> attributes = new LinkedList<>();
		List<Admin> resultList = new LinkedList<>();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(QUERY_ADMIN);
			while (result.next()) {
				attributes.add(result.getString(ADMIN_TABLE_USER));
				attributes.add(result.getString(ADMIN_TABLE_PASSWORD));
				resultList.add(new Admin(attributes));
				attributes.clear();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}
	
	public List<Customer> getCustomer() {
		List<String> attributes = new LinkedList<>();
		List<Customer> resultList = new LinkedList<>();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(QUERY_CUSTOMER);
			while (result.next()) {
				attributes.add(result.getString(CUSTOMER_TABLE_NAME));
				attributes.add(result.getString(CUSTOMER_TABLE_PASSWORD));
				attributes.add(result.getString(CUSTOMER_TABLE_BALANCE));
				resultList.add(new Customer(attributes));
				attributes.clear();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}

	public void updateCustomer(String name, Customer customer) throws Exception{
		String query = "UPDATE" + " " + CUSTOMER_TABLE + " " + "SET" + " " + CUSTOMER_TABLE_NAME + "=?" + ", " + CUSTOMER_TABLE_PASSWORD + "=?" + " , " + CUSTOMER_TABLE_BALANCE + "=?" + " " + "WHERE"
				+ " " + CUSTOMER_TABLE_NAME + "=?";// + customer.getName();
		System.out.println("DAO: " + query);
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setDouble(3, customer.getBalance());
			preparedStatement.setString(4, name);

			preparedStatement.executeUpdate();
			// connection.commit();
			System.out.println("UPDATE");
			List<Customer> testtest = getCustomer();
			System.out.println("UPDATE" + testtest.get(0) + "\t" + customer.getPassword() + "\t");

	}

	public void addCustomer(Customer customer) {
		String query = "INSERT" + " " + "INTO" + " " + CUSTOMER_TABLE + " " + "(" + CUSTOMER_TABLE_NAME + ", " + CUSTOMER_TABLE_PASSWORD + ", " + CUSTOMER_TABLE_BALANCE + ")" + " "
				+ "values (?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setDouble(3, customer.getBalance());

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteCustomer(String name) {
		String query = "DELETE" + " " + "FROM" + " " + CUSTOMER_TABLE + " " + "WHERE" + " " + CUSTOMER_TABLE_NAME + "=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, name);

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
