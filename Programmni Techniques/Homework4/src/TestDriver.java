import static org.junit.Assert.*;

import org.junit.Test;

import business.Bank;
import model.Account;
import model.Person;
import model.SavingAccount;

public class TestDriver {

	Bank bank = new Bank();

	@Test
	public void addPerson() {
		Person p = new Person("p");
		Account a = new SavingAccount(10);
		bank.addPerson(p, a);
		boolean result = false;
		for (Person person : bank.getPersonToAccount().keySet()) {
			if (p.getName().equals(person.getName())) {
				result = true;
			}
		}
		assertTrue("Add Person", result);
	}

	@Test
	public void deletePerson() {
		Person p = new Person("p");
		Account a = new SavingAccount(10);
		bank.addPerson(p, a);
		bank.removePerson(p, a);
		boolean result = true;
		for (Person person : bank.getPersonToAccount().keySet()) {
			if (p.getName().equals(person.getName())) {
				result = false;
			}
		}
		assertTrue("Remove Person", result);
	}

	@Test
	public void addAccount() {
		Person p = new Person("p");
		Account a = new SavingAccount(10);
		bank.addPerson(p, a);
		boolean result = false;
		for (Account account : bank.getPersonToAccount().get(p)) {
			if (account.getId() == a.getId() && account.getAmount() == a.getAmount()) {
				result = true;
			}
		}
		assertTrue("Add Person", result);
	}

	@Test
	public void removeAccount() {
		Person p = new Person("p");
		Account a = new SavingAccount(10);
		bank.addPerson(p, a);
		bank.removeHolderAssociatedAccount(p, a);
		boolean result = true;
		for (Account account : bank.getPersonToAccount().get(p)) {
			if (account.getId() == a.getId() && account.getAmount() == a.getAmount()) {
				result = false;
			}
		}
		assertTrue("Add Person", result);
	}
}
