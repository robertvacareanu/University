package presentation.adminview;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import business.EntityDTO;
import business.UserManager;
import model.Customer;
import model.Product;
import presentation.Window;

public class ProductView {
	private JPanel rightMenu;
	private HashMap<Product, Integer> products;
	private boolean throwException = false;
	
	public ProductView(JPanel rightMenu, HashMap<Product, Integer> products) {
		this.rightMenu = rightMenu;
		this.products = products;
	}
	
	public void viewProduts(EntityDTO entity) throws Exception{
		
		throwException = false;
		rightMenu.removeAll();
		HashMap<Product, Integer> products = entity.getProducts();
		Set<Product> productsAux = products.keySet();
		int size = products.size();
		final JTable productsTable;// = new JTable();
		String[] aux =Window.getFields(new Product());
		
		String[] columns = new String[aux.length+1];
		for(int i=0; i<aux.length; i++) {
			columns[i] = aux[i];
		}
		columns[aux.length] = "Quantity";
		
		// columns = getFields(customers.get(10));
		Object[][] data = new Object[size][4];
		Iterator<Product> it = productsAux.iterator();
		int i = 0;
		while (it.hasNext()) {
			Product p = it.next();
			data[i][0] = p.getId();
			data[i][1] = p.getName();
			data[i][2] = p.getPrice();
			data[i][3] = products.get(p);
			i++;
		}

		productsTable = new JTable();
		DefaultTableModel atm = new DefaultTableModel(data, columns) {
			;
			@Override
			public void setValueAt(Object aValue, int row, int column) {
				// TODO Auto-generated method stub
				super.setValueAt(aValue, row, column);

			}

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return column > 0;
			}
		};
		productsTable.setModel(atm);
		JScrollPane scrollPane = new JScrollPane(productsTable);
		rightMenu.add(scrollPane);
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (null != productsTable.getCellEditor()) {
					// there is an edit in progress
					productsTable.getCellEditor().stopCellEditing();
				}
				atm.fireTableDataChanged();
				productsTable.validate();

				int i = 0;
				Iterator<Product> it = productsAux.iterator();
				while (it.hasNext()) {
					Product p = it.next();
					p.setName(atm.getValueAt(i, 1).toString());
					p.setPrice(Double.valueOf(atm.getValueAt(i, 2).toString()));
					products.put(p, (Integer.valueOf(atm.getValueAt(i, 3).toString())));
					try {
						UserManager.getInstance().getEntityDTO().getEntityDAO().updateProduct(p.getId(), p, products.get(p));
					} catch(Exception ex) {
						throwException = true;
					}
					i++;
				}
				entity.setProducts(products);
				JOptionPane.showMessageDialog(null, "Bad Input", "ERROR", JOptionPane.ERROR_MESSAGE);
				for (Product p : entity.getProducts().keySet()) {
					System.out.println(p);
				}
			}

		});

		JButton addProductButton = new JButton("Add");
		addProductButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel newProduct = new JPanel(new GridLayout(0, 2));
				JTextField name = new JTextField(5);
				name.setToolTipText("Name");
				JTextField price = new JTextField(5);
				price.setToolTipText("Price");
				JTextField quantity = new JTextField(5);
				quantity.setToolTipText("Quantity");

				newProduct.add(new JLabel("Name: "));
				newProduct.add(name);
				newProduct.add(new JLabel("Price: "));
				newProduct.add(price);
				newProduct.add(new JLabel("Quantity: "));
				newProduct.add(quantity);

				int result = JOptionPane.showConfirmDialog(null, newProduct, "Enter the Product values", JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					List<String> attributes = new LinkedList<>();
					attributes.add(String.valueOf(0));
					attributes.add(price.getText());
					attributes.add(name.getText());
					int q = Integer.valueOf(quantity.getText());
					Product p = new Product(attributes);
					entity.addProduct(p, q);
					try {
						UserManager.getInstance().getEntityDTO().getEntityDAO().addProduct(p, q);
					} catch (Exception ex) {
						throwException = true;
					}
					entity.update();
				}
			}
		});

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selection = productsTable.getSelectedRows();
				for (int i = 0; i < selection.length; i++) {
					UserManager.getInstance().getEntityDTO().getEntityDAO().deleteProduct(Long.valueOf(productsTable.getValueAt(selection[i], 0).toString()));
					entity.update();
				}

			}
		});

		rightMenu.add(saveButton);
		rightMenu.add(addProductButton);
		rightMenu.add(deleteButton);
		if(throwException) {
			throw new Exception();
		}
	}
}
