package business;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Account;
import presentation.MainWindow;

public class Manager {

	Bank bank;
	MainWindow mainWindow;

	public Manager() {
		bank = loadBank();
		mainWindow = new MainWindow(bank.getPersonToAccount());
		mainWindow.createPersonAddActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bank.addPerson(mainWindow.getCreatedPerson(), mainWindow.getCreatedAccount());
				mainWindow.updateUI();
			}
		});

		mainWindow.createAccountAddActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				bank.addHolderAssociatedAccount(mainWindow.getCurrentPerson(), mainWindow.getCreatedAccount());
				mainWindow.updateUI();
			}
		});

		mainWindow.withdrawAddActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (Account a : bank.getPersonToAccount().get(mainWindow.getCurrentPerson())) {
					if (a.getId() == mainWindow.getCurrentAccount().getId()) {
						a.withdrawMoney(mainWindow.getSum());
					}
				}
				mainWindow.updateUI();

			}
		});

		mainWindow.addMoneyAddActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (Account a : bank.getPersonToAccount().get(mainWindow.getCurrentPerson())) {
					if (a == mainWindow.getCurrentAccount()) {
						a.addMoney(mainWindow.getSum());
					}
				}
				mainWindow.updateUI();

			}
		});
		
		mainWindow.exitButtonAddActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveBank();
				System.exit(0);
			}
		});
		
		mainWindow.deleteAccountAddActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bank.removeHolderAssociatedAccount(mainWindow.getCurrentPerson(), mainWindow.getCurrentAccount());
				mainWindow.updateUI();
			}
		});
		
		mainWindow.deletePersonAddActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bank.removePerson(mainWindow.getCurrentPerson(), mainWindow.getCurrentAccount());
				mainWindow.updateUI();
			}
		});
	}

	public Bank getBank() {
		return bank;
	}
	
	public void saveBank() {
		try {
			FileOutputStream fileOut = new FileOutputStream("bank.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(bank);
			out.close();
			fileOut.close();
			System.out.println("SAVED");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Bank loadBank() {
		Bank bank = new Bank();
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream("bank.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			bank = (Bank) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bank;
	}

}
