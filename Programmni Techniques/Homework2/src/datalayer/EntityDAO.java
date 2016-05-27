package datalayer;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import business.EntityDTO;
import datalayer.dao.CustomerDAO;
import datalayer.dao.OrderDAO;
import datalayer.dao.ProductDAO;
import model.AbstractUser;
import model.Admin;
import model.Customer;
import model.Order;
import model.Product;

/**
 * 
 * @author Robert This class is a DAO used to fetch data to/from database The communication with the database should be made through this class
 */
public class EntityDAO  {

	private static final String DATABASE_NAME = "ptwarehouse";

	private static final String ADMIN_TABLE = "admin";
	private static final String ADMIN_TABLE_USER = "user";
	private static final String ADMIN_TABLE_PASSWORD = "password";

	private static final String CUSTOMER_TABLE = "customer";
	private static final String CUSTOMER_TABLE_NAME = "name";
	private static final String CUSTOMER_TABLE_PASSWORD = "password";
	private static final String CUSTOMER_TABLE_BALANCE = "balance";

	private static final String ORDERS_TABLE = "orders";
	private static final String ORDERS_TABLE_ID = "id";
	private static final String ORDERS_TABLE_ORDER_ID = "order_id";
	private static final String ORDERS_TABLE_CUSTOMER = "customer";
	private static final String ORDERS_TABLE_ID_PRODUCT = "id_product";
	private static final String ORDERS_TABLE_QUANTITY = "quantity";

	private static final String PRODUCT_TABLE = "product";
	private static final String PRODUCT_TABLE_ID = "id";
	private static final String PRODUCT_TABLE_PRICE = "price";
	private static final String PRODUCT_TABLE_NAME = "name";
	private static final String PRODUCT_TABLE_QUANTITY = "quantity";

	private static final String QUERY_ORDERS = "SELECT" + " " + "*" + " " + "FROM" + " " + ORDERS_TABLE;
	public static final String QUERY_ADMIN = "SELECT" + " " + ADMIN_TABLE_USER + "," + " " + ADMIN_TABLE_PASSWORD + " " + "FROM" + " " + ADMIN_TABLE;
	public static final String QUERY_CUSTOMER = "SELECT" + " " + CUSTOMER_TABLE_NAME + "," + " " + CUSTOMER_TABLE_PASSWORD + "," + " " + CUSTOMER_TABLE_BALANCE + " " + "FROM" + " " + CUSTOMER_TABLE;
	public static final String QUERY_PRODUCT = "SELECT" + " " + PRODUCT_TABLE_ID + "," + " " + PRODUCT_TABLE_NAME + "," + " " + PRODUCT_TABLE_PRICE + "," + " " + PRODUCT_TABLE_QUANTITY + " " + "FROM"
			+ " " + PRODUCT_TABLE;

	DatabaseManager dbm = new DatabaseManager();
	Connection connection;
	CustomerDAO customerDAO;
	OrderDAO orderDAO;
	ProductDAO productDAO;

	public EntityDAO() {
		connection = dbm.getConnection();
		customerDAO = new CustomerDAO(connection);
		orderDAO = new OrderDAO(connection);
		productDAO = new ProductDAO(connection);
	}

	public List<Admin> getAdmin() {
		return customerDAO.getAdmin();
	}

	public List<Customer> getCustomer() {
		return customerDAO.getCustomer();
	}

	public HashMap<Product, Integer> getProduct() {
		return productDAO.getProduct();
	}

	public List<Order> getOrder() {
		return orderDAO.getOrder();
	}

	public void updateCustomer(String name, Customer customer) throws Exception{
		customerDAO.updateCustomer(name, customer);
	}

	public void updateProduct(long id, Product product, int quantity) throws SQLException{
		productDAO.updateProduct(id, product, quantity);
	}

	public void updateOrder(int id, Order order) throws SQLException{
		// First select all the rows having the id equal with the id of the parameter
		String query = "UPDATE" + " " + CUSTOMER_TABLE + " " + "SET" + " " + CUSTOMER_TABLE_NAME + "=?" + ", " + CUSTOMER_TABLE_PASSWORD + "=?" + " , " + CUSTOMER_TABLE_BALANCE + "=?" + " " + "WHERE"
				+ " " + CUSTOMER_TABLE_NAME + "=?";
	}

	public void addCustomer(Customer customer) {
		customerDAO.addCustomer(customer);

	}

	public void addProduct(Product product, int quantity) throws SQLException{
		productDAO.addProduct(product, quantity);
	}
	
	public void addOrder(Order order) {
		orderDAO.addOrder(order);
	}

	public void deleteProduct(Long id) {
		productDAO.deleteProduct(id);
	}

	public void deleteCustomer(String name) {
		customerDAO.deleteCustomer(name);
	}
	
	public void deleteOrder(Long id) {
		orderDAO.deleteOrder(id);
	}

}
