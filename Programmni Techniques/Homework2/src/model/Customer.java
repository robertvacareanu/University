package model;

import java.util.List;

public class Customer extends AbstractUser {
	private double balance;

	public Customer(List<String> attributes) {
		super(attributes.get(0), attributes.get(1));
		this.setBalance(Double.valueOf(attributes.get(2)));
	}
	
	public Customer() {
		super("", "");
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Hello, my name is: " + getName() + " and I have pass: " + getPassword() + " and balance: " + getBalance() + "$" + "\n";
	}

}
