package model;

public class Memento <T> {

	private T t;
	
	public Memento(T t) {
		this.t = t;
	}
	
	public T getSaved() {
		return t;
	}
	
}
