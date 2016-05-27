package business;

import model.Account;
import model.Person;

public interface BankProc {

	void addPerson(Person person, Account account);
	void removePerson(Person person, Account account);
	void addHolderAssociatedAccount(Person person, Account account);
	void removeHolderAssociatedAccount(Person person, Account account);
	
	
	
}
