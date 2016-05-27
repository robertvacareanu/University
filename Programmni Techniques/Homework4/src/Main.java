import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Set;

import business.Bank;
import business.Manager;
import model.Account;
import model.Person;
import model.SavingAccount;
import presentation.MainWindow;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Bank b = new Bank();
//		Person p1 = new Person("My name");
//		Account ac = new SavingAccount();
//		b.addPerson(p1, ac);
//		b.addHolderAssociatedAccount(p1,  ac);
//		Iterator<Set<Account>> it = b.getPersonToAccount().values().iterator();
//		
//		while(it.hasNext()) {
//			
//			Set<Account> set = it.next();
//			Iterator<Account> iterator2 = set.iterator();
//			while(iterator2.hasNext()) {
//				iterator2.next().addMoney(40);
//			}
//		}
		Manager manager = new Manager();
		//manager.loadBank();
	}
}
