package model;

public class SavingAccount extends Account {

	public SavingAccount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public void addMoney(double sum) {
		amount += 1.01 * sum;
		setChanged();
		notifyObservers(sum);
		System.out.println("MODIFIED  " + countObservers());
		
	}

	@Override
	public void withdrawMoney(double sum) {
		// TODO Auto-generated method stub
		amount -= 1.05 * sum;
		setChanged();
		notifyObservers(sum);
	}

}
