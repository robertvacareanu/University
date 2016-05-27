package model;

import java.util.List;

public class Product {

	private long id;
	private double price;
	private String name;
	
	public Product(List<String> attributes) {
		this.id = Long.valueOf(attributes.get(0));
		this.price = Double.valueOf(attributes.get(1));
		this.name = attributes.get(2);
	}
	
	public Product(long id) {
		this.id = id;
	}
	
	public Product() {
		
	}

	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " ";
	}
}
