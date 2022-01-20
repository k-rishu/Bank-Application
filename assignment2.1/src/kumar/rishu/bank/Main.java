package kumar.rishu.bank;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BankAccount person1 = new BankAccount("rishu", 0001, "rishu@123", 2000, 6);
		person1.getBalance();
		person1.withdraw(300, "rishu@123");
		person1.deposit(500);
		person1.getName();
		person1.getAccountNumber();
	}

}
