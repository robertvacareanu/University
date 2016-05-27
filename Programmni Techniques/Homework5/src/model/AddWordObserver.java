package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AddWordObserver extends Observable {
	
	private List<Observer> observers;
	
	public AddWordObserver() {
		// TODO Auto-generated constructor stub
		observers= new LinkedList<>();
	}

	@Override
	public synchronized void clearChanged() {
		// TODO Auto-generated method stub
		super.clearChanged();
	}

	@Override
	public synchronized void setChanged() {
		// TODO Auto-generated method stub
		super.setChanged();
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
	
}
