package in.conceptarchitect.banking;

public class CurrentAccount extends BankAccount {
	private String type;
	public CurrentAccount(int accountNumber, String name, String password, double amount, String type) {
		super(accountNumber, name, password, amount);
		this.type = type;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void creditInterest(double interestRate) {
		//DO NOTHING
	}
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
}
