package model;

public class SpendingAccount extends Account {

	public SpendingAccount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public void addMoney(double sum) {
		amount += sum;	
		setChanged();
		notifyObservers(sum);
	}

	@Override
	public void withdrawMoney(double sum) {
		amount -= 0.97 * sum;
		setChanged();
		notifyObservers(sum);
	}

}
