package datalayer;

import java.util.HashMap;

import model.Admin;
import model.Customer;
import model.Order;
import model.Product;

public class Constants {

	public static final class Linker {
		public static final HashMap<String, String> tableEntityLinker;
		public static final HashMap<String, Class<?>> tableClassLinker;

		static {
			tableEntityLinker = new HashMap<>();
			tableEntityLinker.put("admin", "model.Admin");
			tableEntityLinker.put("customer", "model.Customer");
			tableEntityLinker.put("orders", "model.Order");
			tableEntityLinker.put("product", "model.Product");
			tableEntityLinker.put("name", "Name");
			tableEntityLinker.put("password", "Password");
			tableEntityLinker.put("balance", "Balance");
			tableEntityLinker.put("id", "ID");
			tableEntityLinker.put("customer", "Customer");
			tableEntityLinker.put("products", "Products");
			tableEntityLinker.put("price", "Price");
			
			
			tableClassLinker = new HashMap<>();
			tableClassLinker.put("admin", Admin.class);
			tableClassLinker.put("customer", Customer.class);
			tableClassLinker.put("orders", Order.class);
			tableClassLinker.put("product", Product.class);
		}
		
	}

}
