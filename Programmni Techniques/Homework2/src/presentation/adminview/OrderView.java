package presentation.adminview;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.UserManager;
import model.Customer;
import model.Order;
import model.Product;
import presentation.Window;

public class OrderView {
	
	private JPanel rightMenu;
	private List<Order> orders;
	
	public OrderView(JPanel rightMenu, List<Order> orders) {
		this.rightMenu = rightMenu;
		this.orders = orders;
	}
	
	public void viewOrder() {
		rightMenu.removeAll();
		JTable orderTable;// = new JTable();
		String[] columns = Window.getFields(new Order());
		// columns = getFields(customers.get(0));
		Object[][] data = new Object[orders.size()][3];
		for (int i = 0; i < orders.size(); i++) {
			data[i][0] = orders.get(i).getId();
			data[i][1] = orders.get(i).getCustomer().getName();
			// if(customers.get(i) instanceof Customer) {
			data[i][2] = orders.get(i).getProducts();
			// data[i][0
			// }
		}
		
		orderTable = new JTable();
		DefaultTableModel atm = new DefaultTableModel(data, columns) {
			;
			@Override
			public void setValueAt(Object aValue, int row, int column) {
				// TODO Auto-generated method stub
				super.setValueAt(aValue, row, column);
				// UserManager.getInstance().getEntityDTO().getEntityDAO().updateOrder(new Order());
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return column > 0;
			}
		};
		orderTable.setModel(atm);

		// customerTable.setModel(tm);
		// customerTable.setModel(model);
		// customerTable = new JTable(data, columns);
		// customerTable.getColumnModel().getColumn(2).setCellEditor());
		// customerTable.setPreferredScrollableViewportSize(new Dimension(240, 160));
		// customerTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(orderTable);
		rightMenu.add(scrollPane);
		// rightMenu.add(new JButton("TEst"));
		// rightMenu.add(new JButton("TEsaf"));
		
		
		
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel newOrder = new JPanel(new GridLayout(0, 2));
				
				JLabel customer = new JLabel("Customer");
				JTextField customerTextField = new JTextField(5);
				
				JLabel products = new JLabel("Products");
				JTextField productsTextField = new JTextField(5);
				productsTextField.setToolTipText("Enter the name of the products and the quantity separated by ','");
				
				newOrder.add(customer);
				newOrder.add(customerTextField);
				newOrder.add(products);
				newOrder.add(productsTextField);
				
				
				int result = JOptionPane.showConfirmDialog(null, newOrder, "Enter the Order values", JOptionPane.OK_CANCEL_OPTION);
				
				if(result==JOptionPane.OK_OPTION) {
					Order o = new Order();
					List<String> customerAttributes = new LinkedList<>();
					customerAttributes.add(customerTextField.getText());
					customerAttributes.add("");
					customerAttributes.add("0");
					o.setCustomer(new Customer(customerAttributes));
					String[] productsAndQuantities = productsTextField.getText().split(",| ");
					System.out.println("ADDING: " + productsAndQuantities.length);
					for(int i=0; i<productsAndQuantities.length; i+=2) {
						o.addProduct(new Product(Long.valueOf(productsAndQuantities[i])), Integer.valueOf(productsAndQuantities[i+1]));
					}

					
					
					
					UserManager.getInstance().getEntityDTO().getEntityDAO().addOrder(o);
				}
				
				
				
			}
		});
		
		JButton deleteButton = new JButton("Delete");
		
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selection = orderTable.getSelectedRows();
				for (int i = 0; i < selection.length; i++) {
					UserManager.getInstance().getEntityDTO().getEntityDAO().deleteOrder(Long.valueOf(orderTable.getValueAt(selection[i], 0).toString()));
					//entity.update();
					//AdminWindow.this.revalidate();
				}
			}
		});
		
		rightMenu.add(deleteButton);
		rightMenu.add(addButton);
		
		rightMenu.repaint();
		//this.validate();
	}
	
}
