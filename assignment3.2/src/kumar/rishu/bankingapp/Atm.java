package kumar.rishu.bankingapp;


import kumar.rishu.bank.Bank;
import kumar.rishu.bank.BankAccount;
import kumar.rishu.utils.Input;

public class Atm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Input input = new Input();
		
		Bank icici = new Bank("icici", 12);
		int t = icici.openAccount("rishu", "rishu123", 40000);
		
		BankAccount a1 = icici.getAccount(t);
		
		
		
		while(true) {
			homePage();
			int choice = input.readInt("Choice: ");
			switch(choice) {
			case 1:{
				int money = input.readInt("Enter Money you want to deposit");
				String response = a1.deposit(money);
				System.out.println(response);
			}
			break;
			case 2:{
				String pswd = input.readString("Enter Your password:");
				String newpswd = input.readString("Enter Your New password:");
				String response = a1.changePassword(pswd, newpswd);
				System.out.println(response);
			}
			break;
			case 3:{
				String pswd = input.readString("Enter Your password:");
				int money = input.readInt("Enter Money you want to Withdraw");
				String response = a1.withdraw(money, pswd);
				System.out.println(response);
			}
			break;
			case 4:{
				String pswd = input.readString("Enter Your password:");
				String response = a1.getBalance(pswd);
				System.out.println(response);
			}
			break;
			case 0:
				System.out.println("Thanks For Visiting !");
				System.exit(0); 
			}
		}
		
	}
	
	public static void homePage() {
		System.out.println("Select 1 Deposit");
		System.out.println("Select 2 for Change Password");
		System.out.println("Select 3 for Withdraw");
		System.out.println("Select 4 for Check Balance");
		System.out.println("Select 0 for EXIT");
	}

}
