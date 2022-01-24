package kumar.rishu.bank;

public class BankAccount {
	
	private String name ;
	private int accountNumber ;
	private double balance ;
	private String password ;
	private float interestRate;
	
    public BankAccount(String name, int AccNo, String pass, double balance, float d){
		this.name = name;
		this.accountNumber = AccNo;
		this.balance = balance;
		this.interestRate = d;
		this.password = pass;
	}
	
	
	
	public String deposit(int amount) {
		if(amount<=0) {
			return "Invalid Amount";
		}
		else {
			this.balance += amount;
			return "Amount deposited";
		}
	}
	
	public String withdraw(int amount, String pass) {
		if(!this.password.equals(pass)) {
			return "Not Authorised";
		}
		else {
			if(amount<=0 || amount > this.balance) {
				return "Invalid Amount";
			}
			else {
				this.balance -= amount;
				return "Amount Withdrawn";
			}
		}
	}
	
	public String changePassword(String pswd,String newpswd) {
		if(!this.password.equals(pswd))
			return "Wrong Current Password.";
		this.password=newpswd;
		return "Password Successfully updated";
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAccountNumber() {
		return this.accountNumber;
	}
	
	public String getBalance(String pwd) {
		if(!this.password.equals(pwd))
			return "Invalid credential.";
		return ""+this.balance;
	}
	
	public BankAccount showAccountDetails() {
		return this;
	}
	
	
}
