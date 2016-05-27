package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.Account;
import model.Person;
import model.SavingAccount;
import model.SpendingAccount;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4871543715541829336L;

	private JButton createPerson;
	private JRadioButton savingAccountRadioButton;
	private JRadioButton spendingAccountRadioButton;
	private JTextField amountInAccount;
	private JTextField idTextField;
	private JTextField nameTextField;
	private JTable personTable;
	private JScrollPane personTableScrollPane;
	private JTable accountsTable;
	private JScrollPane accountsTableScrollPane;
	private Map<Person, Set<Account>> data;
	private Person currentPerson = null;
	private Account currentAccount = null;
	private JTextField sumForAccountOperation;
	private JButton withdraw;
	private JButton addMoney;
	private JButton createAccount;
	private JButton exitButton;
	private JButton deletePerson;
	private JButton deleteAccount;
	private static int valueChangedCounter = 0;

	public MainWindow(Map<Person, Set<Account>> data) {
		this.data = data;
		setSize(800, 600);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(0, 2));
		exitButton = new JButton("Exit");
		nameTextField = new JTextField();
		nameTextField.setPreferredSize(new Dimension(100, 25));
		idTextField = new JTextField();
		idTextField.setPreferredSize(new Dimension(100, 25));
		deletePerson = new JButton("Delete Person");
		deleteAccount = new JButton("Delete Account");
		ButtonGroup bg = new ButtonGroup();
		savingAccountRadioButton = new JRadioButton("Saving Account");
		spendingAccountRadioButton = new JRadioButton("Spending Account");
		createAccount = new JButton("Create Account");
		bg.add(savingAccountRadioButton);
		bg.add(spendingAccountRadioButton);

		amountInAccount = new JTextField();
		amountInAccount.setPreferredSize(new Dimension(100, 25));

		JLabel name = new JLabel("Name: ");
		JLabel id = new JLabel("ID: ");

		JPanel top = new JPanel(new GridLayout(20, 0));
		createPerson = new JButton("Create Person");

		top.add(name);
		top.add(nameTextField);
		top.add(id);
		top.add(idTextField);
		top.add(savingAccountRadioButton);
		top.add(spendingAccountRadioButton);
		top.add(amountInAccount);
		top.add(createPerson);
		top.add(createAccount);

		JPanel topLeft = new JPanel(new GridLayout(20, 0));
		sumForAccountOperation = new JTextField();
		withdraw = new JButton("Withdraw");
		addMoney = new JButton("Add");

		topLeft.add(new JLabel("Enter Sum:"));
		topLeft.add(sumForAccountOperation);
		topLeft.add(withdraw);
		topLeft.add(addMoney);
		topLeft.add(deleteAccount);
		topLeft.add(deletePerson);
		topLeft.add(exitButton);
		add(top);
		add(topLeft);

		personTable = new JTable();
		accountsTable = new JTable();

		if (currentPerson != null) {
			accountsTable = createGeneralTable(data.get(currentAccount), accountsTable);
		}
		accountsTableScrollPane = new JScrollPane(accountsTable);
		personTableScrollPane = new JScrollPane(createGeneralTable(data.keySet(), personTable));
		personTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (valueChangedCounter % 2 == 0 && personTable.getSelectedRow() >= 0 && personTable.getSelectedRow() < personTable.getRowCount()) {
					for (Person p : data.keySet()) {
						if (((String) personTable.getValueAt(personTable.getSelectedRow(), 0)).equals(p.getName()) && (long) personTable.getValueAt(personTable.getSelectedRow(), 1) == p.getId()) {
							currentPerson = p;
						}
					}
					createGeneralTable(data.get(currentPerson), accountsTable);
					accountsTableScrollPane.setViewportView(createGeneralTable(data.get(currentPerson), accountsTable));
				}
				valueChangedCounter++;
			}
		});
		accountsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (valueChangedCounter % 2 == 0 && accountsTable.getSelectedRow() >= 0 && accountsTable.getSelectedRow() < accountsTable.getRowCount()) {
					for (Account a : data.get(currentPerson)) {
						if (a.getId() == (long) accountsTable.getValueAt(accountsTable.getSelectedRow(), 0)) {
							currentAccount = a;
						}
					}
				}
				valueChangedCounter++;
			}
		});

		add(personTableScrollPane);
		add(accountsTableScrollPane);

		setVisible(true);
	}

	public void deleteAccountAddActionListener(ActionListener a) {
		deleteAccount.addActionListener(a);
	}

	public void deletePersonAddActionListener(ActionListener a) {
		deletePerson.addActionListener(a);
	}

	public void createPersonAddActionListener(ActionListener a) {
		createPerson.addActionListener(a);
	}

	public Account getCreatedAccount() {
		if (savingAccountRadioButton.isSelected()) {
			return new SavingAccount(Double.valueOf(amountInAccount.getText()));
		} else {
			return new SpendingAccount(Double.valueOf(amountInAccount.getText()));
		}
	}

	public Person getCreatedPerson() {
		return new Person(nameTextField.getText(), Long.valueOf(idTextField.getText()));
	}

	public void createAccountAddActionListener(ActionListener a) {
		createAccount.addActionListener(a);
	}

	public void addMoneyAddActionListener(ActionListener a) {
		addMoney.addActionListener(a);
	}

	public void withdrawAddActionListener(ActionListener a) {
		withdraw.addActionListener(a);
	}

	public void exitButtonAddActionListener(ActionListener a) {
		exitButton.addActionListener(a);
	}

	public Person getCurrentPerson() {
		return currentPerson;
	}

	public Account getCurrentAccount() {
		return currentAccount;
	}

	public double getSum() {
		return Double.valueOf(sumForAccountOperation.getText());
	}

	public void updateUI() {
		// Re-Add Tables;
		personTableScrollPane.setViewportView(createGeneralTable(data.keySet(), personTable));
		if (currentPerson != null) {
			accountsTableScrollPane.setViewportView(createGeneralTable(data.get(currentPerson), accountsTable));
		}
		revalidate();
	}

	private JTable createGeneralTable(Collection<?> data, JTable personsTable) {
		populateTable(data, personsTable);
		return personsTable;
	}

	private void populateTable(Collection<?> data, JTable table) {
		if (data.size() > 0) {
			Object[][] dataForTable = retrieveData(data);
			String[] columnnNames = getFields(data.iterator().next());
			DefaultTableModel model = new DefaultTableModel(dataForTable, columnnNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			table.setModel(model);
		} else {
			System.out.println("NO SPACE");
		}
	}

	private Object[][] retrieveData(Collection<?> data) {
		Object[][] result = new Object[data.size()][getFields(data.iterator().next()).length + 1];
		int i = 0, j = 0;
		Iterator<?> it = data.iterator();
		while (it.hasNext()) {
			Object value = it.next();
			for (Class<?> c = value.getClass(); c != null && c != Object.class; c = c.getSuperclass()) {
				for (Method m : c.getDeclaredMethods()) {
					if (Modifier.isPublic(m.getModifiers()) && m.getReturnType() != void.class && (m.getName().startsWith("get") || m.getName().startsWith("is"))) {
						try {
							result[i][j] = m.invoke(value);
							j++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
			result[i][j] = value.getClass().getSimpleName();
			j = 0;
			i++;
		}
		return result;
	}

	public static String[] getFields(Object obj) {
		List<Field> fields = new ArrayList<>();
		List<Field> fieldsAux = new ArrayList<>();
		for (Class<?> c = obj.getClass(); c != null && c != Observable.class; c = c.getSuperclass()) {
			fieldsAux.addAll(Arrays.asList(c.getDeclaredFields()));
		}
		for (Field f : fieldsAux) {
			if (!Modifier.isStatic(f.getModifiers()) && f.getType() != List.class) {
				fields.add(0, f);
			}
		}
		fields.sort(new Comparator<Field>() {
			public int compare(Field o1, Field o2) {
				return o2.getName().compareTo(o1.getName());
			}
		});
		String result[] = new String[fields.size() + 1];
		for (int i = 0; i < fields.size(); i++) {
			result[i] = fields.get(i).getName();
		}
		result[fields.size()] = "Type";

		return result;
	}

}
