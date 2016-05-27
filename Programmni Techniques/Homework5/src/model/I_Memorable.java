package model;

public interface I_Memorable<T> {

	public Memento<T> storeInMemento();
	public T restoreFromMemento(Memento<T> memento);
	
}
