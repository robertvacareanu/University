package business;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import datalayer.EntityDAO;
import model.AbstractUser;
import model.Admin;
import model.Customer;
import model.Order;
import model.Product;

/**
 * 
 * @author Robert
 * This class is used to transfer the models retrieved from database between layers
 */
public class EntityDTO {

	private List<Customer> users;
	private List<Admin> admins;
	private List<Order> orders;
	private HashMap<Product, Integer> products;
	private EntityDAO entitydao;
	
	public EntityDTO() {
		entitydao = new EntityDAO();
		users = entitydao.getCustomer();
		admins = entitydao.getAdmin();
		orders = entitydao.getOrder();
		products = entitydao.getProduct();
	}
	
	public void update() {
		users = entitydao.getCustomer();
		admins = entitydao.getAdmin();
		System.out.println("ENTITYDTO: " + entitydao.getOrder().size());
		orders = entitydao.getOrder();
		products = entitydao.getProduct();
	}
	
	public void addProduct(Product product, int quantity) {
		products.put(product, quantity);
	}
	
	public void addOrder(Order order) {
		//System.out.println("IN DTO: " + );
		for(Order o : orders) {
			if(o.getId() == order.getId()) {
				for(Product p : o.getProducts().keySet()) {
					Iterator<Product> t = order.getProducts().keySet().iterator();
					Product product = t.next();
					if(p.getName().equals(product.getName())) {
						o.incrementQuantity(product);
						return;
					} else {
						o.addProduct(product, order.getProducts().get(product));
						return;
					}
				}
			}
		}
		//System.out.println("LAST LINE ADD ORDER");
		orders.add(order);
		entitydao.addOrder(order);
	}
	
	public void addUser(Customer abstractUser) {
		users.add(abstractUser);
	}
	
	public void addAdmin(Admin admin) {
		admins.add(admin);
	}
	
	public List<Admin> getAdmins() {
		return admins;
	}

	public List<Customer> getUsers() {
		return users;
	}
	
	public void setUsers(List<Customer> customer) {
		this.users = customer; 
	}

	public List<Order> getOrders() {
		return orders;
	}

	public HashMap<Product, Integer> getProducts() {
		return products;
	}
	
	public void setProducts(HashMap<Product, Integer> products) {
		this.products = products;
	}
	
	public Customer getSpecificCustomer(String name) {
		for(AbstractUser au : users) {
			if(au.getName().equals(name)) return (Customer) au;
		}
		return null;
	}
	
	public Product getSpecificProduct(long id) {
		for(Product p : products.keySet()) {
			if(p.getId() == id) return p;
		}
		return null;
	}
	
	public List<Order> getOrderByCustomer(Customer customer) {
		List<Order> result = new LinkedList<>();
		for(Order o : orders) {
			if(o.getCustomer().getName().equals(customer.getName())) {
				result.add(o);
			}
		}
		return result;
	}
		
	public EntityDAO getEntityDAO() { return entitydao; }

	public void updateCustomer(String name, Customer customer) throws Exception{
		entitydao.updateCustomer(name, customer);
	}
}
