package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import business.EntityDTO;
import business.UserManager;
import model.Customer;
import model.Order;
import model.Product;

public class UserWindow extends AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel leftMenu, rightMenu;
	private JScrollPane rightMenuScrollPane = new JScrollPane();
	private EntityDTO entity;
	private JLabel totalCost;
	private Customer customer;

	public UserWindow() {
		super((int) -Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 8, 0, 0);
		entity = UserManager.getInstance().getEntityDTO();
		customer = UserManager.getInstance().getCurrentCustomer();
	}

	@Override
	public void execute() {
		System.out.println("USER VIEW");
		setLayout(new BorderLayout());

		JButton newOrderButton = new JButton("New Order");
		newOrderButton.setOpaque(false);
		newOrderButton.setFocusPainted(false);
		newOrderButton.setMargin(new Insets(0, 0, 0, 0));
		newOrderButton.setContentAreaFilled(false);
		newOrderButton.setBorderPainted(false);
		newOrderButton.setForeground(Color.WHITE);

		JButton pastOrderButton = new JButton("History");
		pastOrderButton.setOpaque(false);
		pastOrderButton.setFocusPainted(false);
		pastOrderButton.setMargin(new Insets(0, 0, 0, 0));
		pastOrderButton.setContentAreaFilled(false);
		pastOrderButton.setBorderPainted(false);
		pastOrderButton.setForeground(Color.WHITE);

		JButton exitButton = new JButton("Exit");
		exitButton.setOpaque(false);
		exitButton.setFocusPainted(false);
		exitButton.setMargin(new Insets(0, 0, 0, 0));
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.setForeground(Color.WHITE);

		leftMenu = new JPanel();
		leftMenu.setLayout(new BoxLayout(leftMenu, BoxLayout.Y_AXIS));
		leftMenu.add(newOrderButton);
		leftMenu.add(pastOrderButton);
		leftMenu.add(exitButton);
		leftMenu.setBackground(Color.DARK_GRAY);

		rightMenu = new JPanel();
		rightMenuScrollPane = new JScrollPane(rightMenu);

		add(leftMenu, BorderLayout.WEST);
		add(rightMenuScrollPane, BorderLayout.CENTER);

		newOrderButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newOrder();
			}
		});

		pastOrderButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pastOrder();
			}
		});

		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});

		setVisible(true);
	}

	private void newOrder() {

		rightMenu.removeAll();

		List<Product> products = new LinkedList<>(entity.getProducts().keySet());
		HashMap<Product, Integer> productQuantities = entity.getProducts();
		JLabel product = new JLabel("Product");
		JLabel quantity = new JLabel("Quantity");
		JLabel price = new JLabel("Price");
		rightMenu.setLayout(new GridLayout(0, 3));
		rightMenu.add(product);
		rightMenu.add(price);
		rightMenu.add(quantity);
		rightMenu.add(new JSeparator());
		rightMenu.add(new JSeparator());
		rightMenu.add(new JSeparator());
		CustomTextFieldArea quantities[] = new CustomTextFieldArea[products.size()];
		// JSpinner quantities[] = new JSpinner[products.size()];
		for (int i = 0; i < products.size(); i++) {
			quantities[i] = new CustomTextFieldArea(products.get(i).getPrice(), productQuantities.get(products.get(i)));
		}
		int i = 0;
		for (Product p : products) {
			rightMenu.add(new JLabel(p.getName()));
			rightMenu.add(new JLabel(p.getPrice() + "$"));
			rightMenu.add(quantities[i++]);

		}

		JButton orderButton = new JButton("Order");
		orderButton.addActionListener(new ActionListener() {
			Order o = new Order();

			@Override
			public void actionPerformed(ActionEvent e) {
				double sum = 0;
				for (int i = 0; i < quantities.length; i++) {
					sum += Integer.valueOf(quantities[i].getText()) * quantities[i].price;

				}
				if (customer.getBalance() >= sum) {
					o.setCustomer(customer);
					for (int i = 0; i < products.size(); i++) {
						if (Integer.valueOf(quantities[i].getText()) > 0) {
							o.addProduct(products.get(i));
							for (int j = 1; j < Integer.valueOf(quantities[i].getText()); j++) {
								o.incrementQuantity(products.get(i));
							}

						}
					}

					entity.addOrder(o);
					customer.setBalance(customer.getBalance() - Double.valueOf(totalCost.getText()));
					try {
						UserManager.getInstance().updateCurrent();
					} catch (Exception ex) {
						JOptionPane.showOptionDialog(UserWindow.this, "Error", "Error", JOptionPane.OK_OPTION, ERROR, null, null, null);
					}
					JOptionPane.showMessageDialog(UserWindow.this, "Ok", "Order Processed", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(UserWindow.this, "Not Enough Money", "Not Enough Money", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		totalCost = new JLabel();
		rightMenu.add(new JLabel(String.valueOf(customer.getBalance())));
		rightMenu.add(orderButton);
		rightMenu.add(totalCost);

		this.revalidate();

	}

	private void pastOrder() {
		rightMenu.removeAll();
		JTable orders;// = new JTable();
		LinkedList<Order> ordersOfCustomer = (LinkedList<Order>) entity.getOrderByCustomer(customer);
		String[] columns = { "id", "products", "price" };
		Object[][] data = new Object[10][3];
		for (int j = 0; j < ordersOfCustomer.size(); j++) {
			data[j][0] = ordersOfCustomer.get(j).getId();
			data[j][1] = ordersOfCustomer.get(j).getProducts();
			data[j][2] = ordersOfCustomer.get(j).getPrice();
		}
		orders = new JTable(data, columns);
		orders.setMaximumSize(new Dimension(300, 500));
		orders.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(orders);
		rightMenu.add(scrollPane);

		this.revalidate();

	}

	private class CustomTextFieldArea extends JFormattedTextField implements KeyListener {
		
		private static final long serialVersionUID = 6376210892132771271L;
		private double price;
		private int quantity;

		CustomTextFieldArea(double price, int quantity) {
			this.price = price;
			this.quantity = quantity;
			addKeyListener(this);
			setText(String.valueOf(new Integer(0)));
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(Integer.valueOf(getText()) > quantity) {
				setText(String.valueOf(quantity));
			}
			
			int value = 0;
			double initialPrice = 0;
			if (totalCost.getText() == null || totalCost.getText() == "") {
				initialPrice = 0;
			} else {
				initialPrice = Double.valueOf(totalCost.getText());
			}
			if (Integer.valueOf(getText()) == null) {
				value = 0;
			} else {
				value = Integer.valueOf(getText());
			}
			totalCost.setText(String.valueOf(initialPrice + value * price));
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

	}
	
}
