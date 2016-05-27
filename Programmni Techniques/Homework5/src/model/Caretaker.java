package model;

import java.util.ArrayList;
import java.util.List;

public class Caretaker<T> {

	private List<Memento<T>> saved = new ArrayList<>();
	
	public void addMemento(Memento<T> memento) {
		saved.add(memento);
	}
	
	public Memento<T> getMemento(int index) {
		return saved.get(index);
	}
	
}
