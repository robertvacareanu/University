package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class Order {
	private static long genericId=0;
	private long id;
	private Customer customer;
	private HashMap<Product, Integer> products;
	
	public Order(List<String> attributes) {
		genericId++;
		this.id = Long.valueOf(attributes.get(0));
		//System.out.println("LIST CONSTRUCTOR: " + id + "generic: " + genericId +"\n\n");
		products = new HashMap<>();
	}
	
	public Order() {
		genericId++;
		this.id=genericId;
		//System.out.println("EMPTY constructor: " + id);
		products = new HashMap<>();
	}
	
	public void incrementQuantity(Product p) {
		for(Product product : products.keySet()) {
			if(product.getName().equals(p.getName())) {
				int quantity = products.get(product);
				products.put(product, quantity+1);
			}
		}
	}


	public double getPrice() {
		double result = 0;
		for (Product p : products.keySet()) {
			result += p.getPrice() * products.get(p);
		}
		return result;
	}
	
	public void addProduct(Product p, Integer quantity) {
		products.put(p, quantity);
	}
	
	public void addProduct(Product p) {
		products.put(p, 1);
	}
	
	public HashMap<Product, Integer> getProducts() {
		return products;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void printToFile() throws IOException {
		StringBuilder orderDetails = new StringBuilder();
		orderDetails.append("\nI am order with: \r\nid: " + id + "\r\ncustomer name: " + customer.getName() + " \r\nproducts: ");
		for(Product p : products.keySet()) {
			orderDetails.append("Product id: " + p.getId() + " Product name: " + p.getName() + " Product price: " + p.getPrice() + " Quantity: " + products.get(p) + "\r\n\t  ");
		}
		orderDetails.append("\r\n");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("Order.txt", true));
			writer.write(orderDetails.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR");
			e.printStackTrace();
		} finally {
			writer.close();
		}
		
		
		
	}
	
	
	@Override
	public String toString() {
		StringBuilder orderDetails = new StringBuilder();
		orderDetails.append("\nI am order with: \nid: " + id + "\ncustomer name: " + customer.getName() + " \nproducts: ");
		for(Product p : products.keySet()) {
			orderDetails.append("Product id: " + p.getId() + " Product name: " + p.getName() + " Product price: " + p.getPrice() + " Quantity: " + products.get(p) + "\n\t  ");
		}
		orderDetails.append("\n");
		return orderDetails.toString();
	}
}
