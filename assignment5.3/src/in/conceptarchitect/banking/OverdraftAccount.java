package in.conceptarchitect.banking;

public class OverdraftAccount extends BankAccount {
	private String type;
	double odLimit;
	public OverdraftAccount(int accountNumber, String name, String password, double amount, String type) {
		super(accountNumber, name, password, amount);
		this.type = type;
		// TODO Auto-generated constructor stub
		calculateOdLimit();
	}

	private void calculateOdLimit() {
		// TODO Auto-generated method stub
		
		odLimit=Math.max(balance/10,odLimit);
	}

	public double getOdLimit() {
		// TODO Auto-generated method stub
		return odLimit;
	}
	
	@Override
	public double deposit(double deposit) {
		// TODO Auto-generated method stub
		var result= super.deposit(deposit);
		calculateOdLimit();
		return result;
	}
	
	@Override
	public void creditInterest(double interestRate) {
		// TODO Auto-generated method stub
		super.creditInterest(interestRate);
		calculateOdLimit();
	}
	
	@Override
	public double getMaxWithdrawableAmount(String password) {
		// TODO Auto-generated method stub
		return balance+odLimit;
	}
	
	@Override
	public Response withdraw(double amount, String password) {	
		
		var result=super.withdraw(amount, password);
		
		if(result.getCode()==ResponseStatus.SUCCESS)
			if(balance<0) {
				var fee= balance/100;
				balance+=fee;
			}
			
		return result;
	}
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

}
