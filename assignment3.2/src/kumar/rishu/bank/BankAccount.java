package kumar.rishu.bank;

public class BankAccount {
	
	private String name ;
	private int accountNumber ;
	private int balance ;
	private String password ;
	private static int accountCounter = 0;
	
    public BankAccount(String name,  String pass, int balance){
		this.name = name;
		this.balance = balance;
		this.password = pass;
		accountCounter = accountCounter+1;
		this.accountNumber = accountCounter;
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
	
	
	
	public String showAccountDetails(String pass) {
		return "Name: "+getName()+"\n"+
			   "Account No: "+getAccountNumber()+"\n"+
			   "Balance: "+getBalance(pass) + "\n";
	}
	
	public void setBalance(int amount) {
		this.balance = amount;
	}
	
	public boolean checkPassword(String pass) {
		if(!this.password.equals(pass))
			return false;
		return true;
	}
	
	public String moneyTransfer(BankAccount fromAcc, BankAccount toAcc, int amount) {
		if(fromAcc.balance > amount ) {
			fromAcc.setBalance(fromAcc.balance - amount);
			toAcc.setBalance(toAcc.balance+amount);
			return "Amount Transfered";
		}
		
		return "Transfer can not be Completed";
		
	}



	public int knowBalance() {
		// TODO Auto-generated method stub
		return balance;
	}
	
	
}
