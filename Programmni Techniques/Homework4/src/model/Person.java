package model;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class Person implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2616793711490904697L;
	private long id;
	private String name;
	
	public Person(String name) {
		this.name = name;
	}
	
	public Person(String name, long id) {
		this.name = name;
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("MODIFIED! " + name);
	}

}
