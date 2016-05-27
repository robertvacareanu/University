package model;

public abstract class AbstractUser {
	private String name;
	private String password;
	
	public AbstractUser(String name, String password) {
		this.setName(name);
		this.setPassword(password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Hello, I am " + name + "\n";
	}
}
