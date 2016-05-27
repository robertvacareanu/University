package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import business.EntityDTO;
import business.UserManager;
import presentation.adminview.CustomerView;
import presentation.adminview.OrderView;
import presentation.adminview.ProductView;

public class AdminWindow extends AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel leftMenu, rightMenu;
	private JScrollPane rightMenuScrollPane = new JScrollPane();
	private EntityDTO entity;

	public AdminWindow() {
		super((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 8, 0, 1);
		entity = UserManager.getInstance().getEntityDTO();

	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("ADMIN VIEW");
		setLayout(new BorderLayout());

		JButton viewCustomersButton = new JButton("View Customers");
		viewCustomersButton.setOpaque(false);
		viewCustomersButton.setFocusPainted(false);
		viewCustomersButton.setMargin(new Insets(0, 0, 0, 0));
		viewCustomersButton.setContentAreaFilled(false);
		viewCustomersButton.setBorderPainted(false);
		viewCustomersButton.setForeground(Color.WHITE);

		JButton viewOrdersButton = new JButton("History");
		viewOrdersButton.setOpaque(false);
		viewOrdersButton.setFocusPainted(false);
		viewOrdersButton.setMargin(new Insets(0, 0, 0, 0));
		viewOrdersButton.setContentAreaFilled(false);
		viewOrdersButton.setBorderPainted(false);
		viewOrdersButton.setForeground(Color.WHITE);

		JButton viewProductsButton = new JButton("Products");
		viewProductsButton.setOpaque(false);
		viewProductsButton.setFocusPainted(false);
		viewProductsButton.setMargin(new Insets(0, 0, 0, 0));
		viewProductsButton.setContentAreaFilled(false);
		viewProductsButton.setBorderPainted(false);
		viewProductsButton.setForeground(Color.WHITE);

		JButton exitButton = new JButton("Exit");
		exitButton.setOpaque(false);
		exitButton.setFocusPainted(false);
		exitButton.setMargin(new Insets(0, 0, 0, 0));
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.setForeground(Color.WHITE);

		leftMenu = new JPanel();
		leftMenu.setLayout(new BoxLayout(leftMenu, BoxLayout.Y_AXIS));
		leftMenu.add(viewCustomersButton);
		leftMenu.add(viewOrdersButton);
		leftMenu.add(viewProductsButton);
		leftMenu.add(exitButton);
		leftMenu.setBackground(Color.DARK_GRAY);

		rightMenu = new JPanel();
		rightMenuScrollPane = new JScrollPane(rightMenu);

		add(leftMenu, BorderLayout.WEST);
		add(rightMenuScrollPane, BorderLayout.CENTER);

		viewCustomersButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// JOptionPane.showMessageDialog(UserWindow.this, "", "New Order", JOptionPane.INFORMATION_MESSAGE);
				viewCustomer();

			}
		});

		viewOrdersButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				viewOrder();
			}
		});

		viewProductsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				viewProducts();

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

	private void viewOrder() {
		new OrderView(rightMenu, entity.getOrders()).viewOrder();
		rightMenu.repaint();
		this.validate();
	}

	private void viewCustomer() {

		try {
			new CustomerView(rightMenu).viewCustomer(entity);
		} catch (Exception e) {
			JOptionPane.showOptionDialog(AdminWindow.this, "Error", "Error", JOptionPane.OK_OPTION, ERROR, null, null, null);
			e.printStackTrace();
		}
		this.revalidate();
	}

	private void viewProducts() {
	
		try {
			new ProductView(rightMenu, entity.getProducts()).viewProduts(entity);
		} catch (Exception e) {
			JOptionPane.showOptionDialog(AdminWindow.this, "Error", "Error", JOptionPane.OK_OPTION, ERROR, null, null, null);
		}
		this.revalidate();
	}
}