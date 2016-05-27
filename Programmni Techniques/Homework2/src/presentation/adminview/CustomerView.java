package presentation.adminview;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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

import business.EntityDTO;
import business.UserManager;
import model.Customer;
import presentation.AdminWindow;
import presentation.Window;

public class CustomerView {
	private JPanel rightMenu;
	private boolean throwException = false;
	
	public CustomerView(JPanel rightMenu) {
		this.rightMenu = rightMenu;
	}

	public void viewCustomer(EntityDTO entity) throws Exception{

		throwException = false;
		rightMenu.removeAll();
		List<Customer> customers = entity.getUsers();
		int size = customers.size();
		JTable customerTable;// = new JTable();
		String[] columns = Window.getFields(new Customer());
		System.out.println("THE COLUMNS ARE" + columns[0] + "\t" + columns[1] + "\t" + columns[2]);
		// columns = getFields(customers.get(10));
		Object[][] data = new Object[size][3];
		for (int i = 0; i < customers.size(); i++) {
			data[i][2] = customers.get(i).getPassword();
			data[i][1] = customers.get(i).getName();
			data[i][0] = customers.get(i).getBalance();

		}

		customerTable = new JTable();
		DefaultTableModel atm = new DefaultTableModel(data, columns) {
			
			@Override
			public void setValueAt(Object aValue, int row, int column) {
				super.setValueAt(aValue, row, column);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return true;
			}
		};
		customerTable.setModel(atm);

		JScrollPane scrollPane = new JScrollPane(customerTable);
		rightMenu.add(scrollPane);
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (null != customerTable.getCellEditor()) {
					// there is an edit in progress
					customerTable.getCellEditor().stopCellEditing();
				}
				atm.fireTableDataChanged();
				customerTable.validate();

				for (int i = 0; i < customers.size(); i++) {
					String initialName = customers.get(i).getName();
					customers.get(i).setName(atm.getValueAt(i, 2).toString());
					customers.get(i).setPassword(atm.getValueAt(i, 1).toString());
					customers.get(i).setBalance(Double.valueOf(atm.getValueAt(i, 0).toString()));
					try {
						UserManager.getInstance().getEntityDTO().getEntityDAO().updateCustomer(initialName, customers.get(i));
						entity.setUsers(customers);
					} catch (Exception ex) {
						System.out.println("THROWS EXCEPTION");
						JOptionPane.showMessageDialog (null, "Invalid Input", "ERROR", JOptionPane.ERROR_MESSAGE);
					}

				}

			}
		});
		System.out.println("STATUS OF FLAG: " + throwException + "\n\n\n\n\n\n");
		throwNewException(throwException);

		JButton addCustomerButton = new JButton("Add Customer");
		addCustomerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel newCustomer = new JPanel(new GridLayout(0, 2));
				JTextField name = new JTextField(5);
				name.setToolTipText("Name");
				JTextField pass = new JTextField(5);
				pass.setToolTipText("Pass");
				JTextField balance = new JTextField(5);
				balance.setToolTipText("Balance");
				newCustomer.add(new JLabel("Name: "));
				newCustomer.add(name);
				newCustomer.add(new JLabel("Pass: "));
				newCustomer.add(pass);
				newCustomer.add(new JLabel("Balance: "));
				newCustomer.add(balance);

				int result = JOptionPane.showConfirmDialog(null, newCustomer, "Enter the Customer values", JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					List<String> attributes = new LinkedList<>();
					attributes.add(name.getText());
					attributes.add(pass.getText());
					attributes.add(balance.getText());
					Customer c = new Customer(attributes);
					entity.addUser(c);
					UserManager.getInstance().getEntityDTO().getEntityDAO().addCustomer(c);
				}
			}
		});

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selection = customerTable.getSelectedRows();
				for (int i = 0; i < selection.length; i++) {
					UserManager.getInstance().getEntityDTO().getEntityDAO().deleteCustomer(customerTable.getValueAt(selection[i], 0).toString());
					entity.update();
				}

			}
		});
		rightMenu.add(saveButton);
		rightMenu.add(addCustomerButton);
		rightMenu.add(deleteButton);
		System.out.println("STATUS OF THROWEXCEPTION: " + throwException + "\n\n\n\n\n\n");
	}
	private void throwNewException(boolean flag) throws Exception{
		if(flag) {
			throw new Exception();
		}
	}
}
