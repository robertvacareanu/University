package business;

import java.sql.SQLException;

import model.AbstractUser;
import model.Admin;
import model.Customer;

/**
 * 
 * @author Robert This class is responsible with the authentication/creation of new users
 */
public class UserManager {

	// ! some obj to access database and retrieve info;
	private static UserManager instance;// = new UserManager();
	private EntityDTO entity;// = new EntityDTO();
	private AbstractUser current;

	private UserManager() {
		entity = new EntityDTO();
	}

	public static UserManager getInstance() {
		if(instance == null) instance = new UserManager();
		return instance;
	}

	public boolean authenticate(String user, char[] pass, int mode) {
		System.out.println("HERE\n\n");
		System.out.println("LOGIN: " + user + "\t" + pass.toString());
		if (mode == 0) {
			for (Customer au : entity.getUsers()) {
				if (user.equals(au.getName()) && String.valueOf(pass).equals(au.getPassword())) {
					current = au;
					return true;
				}
			}
		} else {
			for (Admin au : entity.getAdmins()) {
				if (user.equals(au.getName()) && String.valueOf(pass).equals(au.getPassword())) {
					current = au;
					return true;
				}
			}
		}
		return false;

	}

	public boolean create(String user, char[] pass) {
		return false;
	}

	public Admin getCurrentAdmin() {
		return (Admin) current;
	}

	public Customer getCurrentCustomer() {
		return (Customer) current;
	}

	public EntityDTO getEntityDTO() {
		return entity;
	}
	
	public void updateCurrent() throws Exception {
		entity.updateCustomer(current.getName(), (Customer) current);
	}
}
