package datalayer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.Order;
import model.Product;

public class OrderDAO {

	private static final String ORDERS_TABLE = "orders";
	private static final String ORDERS_TABLE_ID = "id";
	private static final String ORDERS_TABLE_ORDER_ID = "order_id";
	private static final String ORDERS_TABLE_CUSTOMER = "customer";
	private static final String ORDERS_TABLE_ID_PRODUCT = "id_product";
	private static final String ORDERS_TABLE_QUANTITY = "quantity";
	
	private static final String QUERY_ORDERS = "SELECT" + " " + "*" + " " + "FROM" + " " + ORDERS_TABLE;
	
	private Connection connection;
	private CustomerDAO customerDAO;
	private ProductDAO productDAO;
	
	public OrderDAO(Connection connection) {
		this.connection = connection;
		customerDAO = new CustomerDAO(connection);
		productDAO = new ProductDAO(connection);
	}
	
	
	public List<Order> getOrder() {
		boolean notInList = false;
		boolean notInProductsList = false;
		List<String> attributes = new LinkedList<>();
		List<Order> resultList = new LinkedList<>();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(QUERY_ORDERS);
			
			boolean toAdd = false;
			
			
			while(result.next()) {
				attributes.add(result.getString(ORDERS_TABLE_ORDER_ID));
				System.out.println("ATTR" + attributes.get(0));
				Order order = new Order(attributes);
				order.setCustomer(customerDAO.getSpecificCustomer(result.getString(ORDERS_TABLE_CUSTOMER)));
				Product product = productDAO.getSpecificProduct(Long.valueOf(result.getString(ORDERS_TABLE_ID_PRODUCT)));
				order.addProduct(product, Integer.valueOf(result.getString(ORDERS_TABLE_QUANTITY)));
				
				if(resultList.size() != 0) {
					for(int i=0; i<resultList.size(); i++) {
						Order o = resultList.get(i);
						if(o.getId() == order.getId() && !(o.getProducts().containsKey(product))) {
							o.addProduct(product, order.getProducts().get(product));
							toAdd=false;
						} else {
							toAdd=true;
						}
					}
					if(toAdd) {
						resultList.add(order);
						toAdd=false;
					}
				} else {
					resultList.add(order);
				}
				attributes.clear();
			}
			
			
		} catch (SQLException e) {
			System.out.println("ENTITYDAO EXCEPTION");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}

	public void addOrder(Order order) {
		String query = "INSERT" + " " + "INTO" + " " + ORDERS_TABLE + " (" + ORDERS_TABLE_ID + ", " + ORDERS_TABLE_ORDER_ID + ", " + ORDERS_TABLE_CUSTOMER + ", " + ORDERS_TABLE_ID_PRODUCT + ", "
				+ ORDERS_TABLE_QUANTITY + ")" + " " + "values (?, ?, ?, ?, ?)";
		
		Set<Product> products = order.getProducts().keySet();
		HashMap<Product, Integer> productsAndQuantities = order.getProducts();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			Iterator<Product> it = products.iterator();
			while(it.hasNext()) {
				Product p = it.next();
				
				preparedStatement.setInt(1, 0);
				preparedStatement.setLong(2, order.getId());
				preparedStatement.setString(3, order.getCustomer().getName());
				System.out.println("PRODUCT ID: " + p.getId());
				preparedStatement.setLong(4, p.getId());
				preparedStatement.setInt(5, productsAndQuantities.get(p));
				
				preparedStatement.execute();
				System.out.println("HEREREREERERE");
				preparedStatement.clearParameters();
				it.remove();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public void deleteOrder(Long id) {
		String query = "DELETE" + " " + "FROM" + " " + ORDERS_TABLE + " " + "WHERE" + " " + ORDERS_TABLE_ORDER_ID + "=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setLong(1, id);

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
