package business;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import datalayer.EntityDAO;
import model.Customer;
import model.Order;
import model.Product;
import presentation.Window;

public class Main {

	
	

		// TODO Auto-generated method stub
		//new Window();
	Main(){

	}
	public static void main(String[] args) {
		//System.out.println(Admin.class.getName());
		//new DatabaseManager();
		//new Window();
		Order o = new Order();
		List<String> attributes = new LinkedList<>();
		attributes.add("Name");
		attributes.add("Pass");
		attributes.add("123456");
		o.setCustomer(new Customer(attributes));
		attributes.clear();
		attributes.add("100");
		attributes.add("12500");
		attributes.add("Bicycle");
		o.addProduct(new Product(attributes));
		attributes.clear();
		attributes.add("1500");
		attributes.add("125000");
		attributes.add("Laptop");
		o.addProduct(new Product(attributes));
		attributes.clear();
		attributes.add("1580");
		attributes.add("500");
		attributes.add("Phone");
		o.addProduct(new Product(attributes));
		try {
			o.printToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(new EntityDAO().getAdmin());
		//UserManager.getInstance();
	}

}
/*

import presentation.Window;
import java.sql.*;

public class Main {

	private Connection connection;
	private Statement statement;
	private ResultSet result;
	private DatabaseMetaData metadata;
	

		// TODO Auto-generated method stub
		//new Window();
	Main(){
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ptwarehouse", "root", "");
			
			metadata = connection.getMetaData();
			
			statement = connection.createStatement();
			
			result = statement.executeQuery("select * from admin");
			
			while(result.next()) {
				System.out.println(result.getString("user") + "\t" + result.getString("password"));
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION");
		}
	}
	public static void main(String[] args) {
		new Main();
		new Window();
	}

}

*/