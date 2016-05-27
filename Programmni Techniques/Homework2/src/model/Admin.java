package model;

import java.util.List;

public class Admin extends AbstractUser{

	public Admin(List<String> attributes) {
		super(attributes.get(0), attributes.get(1));
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Helo, I am " + getName() + " with password: " + getPassword() + "\n";
	}

}
