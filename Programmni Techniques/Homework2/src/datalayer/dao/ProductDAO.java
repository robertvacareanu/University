package datalayer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import model.Product;

public class ProductDAO {

	private static final String PRODUCT_TABLE = "product";
	private static final String PRODUCT_TABLE_ID = "id";
	private static final String PRODUCT_TABLE_PRICE = "price";
	private static final String PRODUCT_TABLE_NAME = "name";
	private static final String PRODUCT_TABLE_QUANTITY = "quantity";
	
	private static final String QUERY_PRODUCT = "SELECT" + " " + PRODUCT_TABLE_ID + "," + " " + PRODUCT_TABLE_NAME + "," + " " + PRODUCT_TABLE_PRICE + "," + " " + PRODUCT_TABLE_QUANTITY + " " + "FROM"
			+ " " + PRODUCT_TABLE;
	
	Connection connection;
	
	public ProductDAO(Connection connection) {
		this.connection = connection;
	}
	
	public Product getSpecificProduct(long id) {
		HashMap<Product, Integer> products = getProduct();
		for (Product p : products.keySet()) {
			if (p.getId() == id)
				return p;
		}
		return null;
	}
	
	public HashMap<Product, Integer> getProduct() {
		List<String> attributes = new LinkedList<>();
		HashMap<Product, Integer> resultList = new HashMap<>();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(QUERY_PRODUCT);
			while (result.next()) {
				attributes.add(result.getString(PRODUCT_TABLE_ID));
				attributes.add(result.getString(PRODUCT_TABLE_PRICE));
				attributes.add(result.getString(PRODUCT_TABLE_NAME));
				resultList.put(new Product(attributes), Integer.valueOf(result.getString(PRODUCT_TABLE_QUANTITY)));
				attributes.clear();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultList;

	}
	
	public void deleteProduct(Long id) {
		String query = "DELETE" + " " + "FROM" + " " + PRODUCT_TABLE + " " + "WHERE" + " " + PRODUCT_TABLE_ID + "=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setLong(1, id);

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateProduct(long id, Product product, int quantity) {
		String query = "UPDATE" + " " + PRODUCT_TABLE + " " + "SET" + " " + PRODUCT_TABLE_PRICE + "=?" + ", " + PRODUCT_TABLE_NAME + "=?" + " , " + PRODUCT_TABLE_QUANTITY + "=?" + " " + "WHERE" + " "
				+ PRODUCT_TABLE_ID + "=?";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setDouble(1, product.getPrice());
			preparedStatement.setString(2, product.getName());
			preparedStatement.setInt(3, quantity);
			preparedStatement.setLong(4, id);

			preparedStatement.executeUpdate();
			// connection.commit();
			System.out.println("UPDATE");
			HashMap<Product, Integer> testtest = getProduct();
			System.out.println("UPDATE" + testtest.get(product) + "\t" + product.getName() + "\t" + product.getPrice() + "\t " + "\t");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION DAO");
			e.printStackTrace();
		}
	}

	public void addProduct(Product product, int quantity) {
		String query = "INSERT" + " " + "INTO" + " " + PRODUCT_TABLE + " " + "(" + PRODUCT_TABLE_NAME + ", " + PRODUCT_TABLE_PRICE + ", " + PRODUCT_TABLE_QUANTITY + ")" + " " + "values (?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, product.getName());
			preparedStatement.setDouble(2, product.getPrice());
			preparedStatement.setInt(3, quantity);

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
