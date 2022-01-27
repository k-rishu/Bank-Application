package in.conceptarchitect.banking;

public class SavingsAccount extends BankAccount {
	private String type;
	public SavingsAccount(int accountNumber, String name, String password, double amount, String type) {
		super(accountNumber, name, password, amount);
		// TODO Auto-generated constructor stub
	}

	public double getMinBalance() {
		// TODO Auto-generated method stub
		return 5000;
	}
	
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	@Override
	public double getMaxWithdrawableAmount(String password) {
		// TODO Auto-generated method stub
		return getBalance(password)-getMinBalance();
	}
	

}
