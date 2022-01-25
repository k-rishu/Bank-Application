package in.conceptarchitect.banking;

import in.conceptarchitect.utils.Input;

public class ATM {

	Bank bank; //associated parent bank.
	int accountNumber;
	Input keyboard=new Input();
	private String password;
	private String type;
	

	public ATM(Bank bank) {
		super();
		this.bank = bank;
	}
	
	public void start() {
		accountNumber=keyboard.readInt("account number?");
		//System.out.println("acc No" + accountNumber);
		password=keyboard.readString("password?");
		//System.out.println("password "+ password);
		//type = keyboard.readString("Type?");
		//A secret menu
		if(accountNumber==-1 && password=="NIMDA")
			adminMenu();
		else
			mainMenu();
	}
	
	
	
	private void adminMenu() {
		System.out.println("In Admin Menu");
		// TODO Auto-generated method stub
		while(true) {
			var choice=keyboard.readInt("1. open account 2. credit interest 3. view all accounts 0. exit ?");
			switch(choice) {
			case 1:
			{
				var name = keyboard.readString("Enter Name: ");
				var password = keyboard.readString("Enter Password: ");

				var amount = keyboard.readInt("Enter Amount: ");
				var accType = keyboard.readString("Enter Account Type: ");
				bank.openAccount(name, password, amount, accType);
			}
			break;
			case 2:
				bank.creaditIntrest(12);
				break;
			case 3:
				bank.viewAllAccounts();
			case 0:
				return ;
				
			default:
				showError("invalid choice. retry");
			}
		}
	}

	private void mainMenu() {
		System.out.println("In Main Menu");
		while(true) {
			var choice=keyboard.readInt("1. deposit 2. withdraw 3. check balance 4. transfer 5. close account 0. exit ?");
			switch(choice) {
			case 1:
				doDeposit();
				break;
			case 2:
				doWithdraw();
				break;
				
			case 3:
				doCheckBlance();
				break;
				
			case 4:
				doTransfer();
				break;
				
			case 5:
				doCloseAccount();
				break;
				
			case 0:
				return ;
				
			default:
				showError("invalid choice. retry");
			
			}
		}
	}

	private void showError(String string) {
		// TODO Auto-generated method stub
		System.err.println(string);
	}

	private void doCloseAccount() {
		// TODO Auto-generated method stub
		double result = bank.closeAccount(accountNumber, password);
		if(result == -1) {
			System.err.println("Invalid Passowrd");
		}
		else
			dispenseCash((int)result);
	}

	private void doTransfer() {
		// TODO Auto-generated method stub
		int amount=keyboard.readInt("amount?");
		int targetAccount=keyboard.readInt("target account?");
		Response response= bank.transfer(accountNumber, amount, password, targetAccount);
		if(response.getCode()==ResponseStatus.SUCCESS) {
			showInfo("Amount Transferred Successfully");
		} else {
			showError(response.getMessage());
		}
		
	}

	private void doCheckBlance() {
		// TODO Auto-generated method stub
		var account=bank.getAccount(accountNumber,password);
		if(account==null){
			showError("Invalid Credentials");
			return;
		}
		double balance=account.getBalance(password);
		showInfo("Your Balance:"+balance);
		
	}

	private void doWithdraw() {
		// TODO Auto-generated method stub
		int amount=keyboard.readInt("Amount to withdraw?");
		Response result= bank.withdraw(accountNumber, amount, password);
		if(result.getCode()==ResponseStatus.SUCCESS)
			dispenseCash(amount);
		else
			showError(result.getMessage());
		
	}

	private void dispenseCash(int amount) {
		// TODO Auto-generated method stub
		System.out.println("Please collect your cash : "+amount);
	}

	private void doDeposit() {
		// TODO Auto-generated method stub
		int amount=keyboard.readInt("Deposit Amount?"); //ATM allows only whole sum (actully multiple of 100)
		if(amount%100!=0) {
			showError("Invalid Denomination");
			return ;
		}
		
		if(bank.deposit(accountNumber, amount)>0)
			showInfo("Amount deposited");
		else
			showInfo("Deposit failed");
		
		
	}

	private void showInfo(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}
	
	
}
