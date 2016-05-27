package business;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.Account;
import model.Person;

public class Bank implements BankProc, Serializable {

	private Map<Person, Set<Account>> personToAccount;

	public Bank() {
		personToAccount = new HashMap<>();
	}

	/**
	 * @Precondition account != null person != null
	 * @Postcondition The bank data remains well formed
	 */
	@Override
	public void addPerson(Person person, Account account) {
		assert (person != null) : "Person should not be null";
		assert (account != null) : "Account should not be null";
		if (!personToAccount.containsKey(person)) {
			personToAccount.put(person, new HashSet<Account>());
		}
		personToAccount.get(person).add(account);
		account.addObserver(person);
		System.out.println("ADD: " + person.getId() + "\t" + person.getName() + " \t" + account.getAmount() + " " + account.getId());
		assert (wellFormed() != false) : "Data should remain well fomrmed";
	}

	/**
	 * @Precondition account != null person != null
	 * @Postcondition The bank data remains well formed
	 */
	@Override
	public void removePerson(Person person, Account account) {
		assert (person != null) : "Person should not be null";
		assert (account != null) : "Account should not be null";
		if (personToAccount.containsKey(person)) {
			personToAccount.remove(person);
		}
		assert (wellFormed() != false) : "Data should remain well fomrmed";
	}

	/**
	 * @Precondition account != null person != null
	 * @Postcondition The bank data remains well formed
	 */
	@Override
	public void addHolderAssociatedAccount(Person person, Account account) {
		assert (person != null) : "Person should not be null";
		assert (account != null) : "Account should not be null";
		Set<Account> set = personToAccount.get(person);
		account.addObserver(person);
		set.add(account);
		assert (wellFormed() != false) : "Data should remain well fomrmed";
	}

	/**
	 * @Precondition account != null person != null
	 * @Postcondition The bank data remains well formed
	 */
	@Override
	public void removeHolderAssociatedAccount(Person person, Account account) {
		assert (person != null) : "Person should not be null";
		assert (account != null) : "Account should not be null";
		HashSet<Account> set = (HashSet<Account>) personToAccount.get(person);
		set.remove(account);
		assert (wellFormed() != false) : "Data should remain well fomrmed";
	}

	/**
	 * 
	 * @return the Hash map
	 */
	public Map<Person, Set<Account>> getPersonToAccount() {
		return personToAccount;
	}

	/**
	 * Check for every person and every account to not be null
	 * 
	 * @return whether the bank accounts data are still consistency
	 */
	private boolean wellFormed() {
		if (personToAccount == null) {
			return false;
		}
		for (Person person : personToAccount.keySet()) {
			if (person == null) {
				return false;
			}
			Iterator<Account> it = personToAccount.get(person).iterator();
			while (it.hasNext()) {
				Account ac = it.next();
				if (ac == null) {
					return false;
				}
			}
		}
		return true;
	}

}
