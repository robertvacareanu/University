package datalayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.EntityDTO;
import model.AbstractUser;
import model.Admin;
import model.Customer;
import model.Order;
import model.Product;

public class DatabaseManager {
	private static Connection connection;
	private static final String DATABASE_NAME = "ptwarehouse";

	public DatabaseManager() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ptwarehouse", "root", "");
//			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Connection getConnection() {
		return connection;
	}


}
