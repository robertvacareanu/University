package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class Account extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1506927587799380597L;
	private static long genericId = 0;
	private long id;
	protected double amount;
	List<Observer> observers;

	public Account() {
		id = ++genericId;
		observers = new LinkedList<>();
	}

	@Override
	public synchronized void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void notifyObservers(Object arg) {
		for(Observer o : observers) {
			o.update(this, arg);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	public abstract void addMoney(double sum);

	public abstract void withdrawMoney(double sum);

}
