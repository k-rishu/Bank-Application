package in.conceptarchitect.banking;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bank icici = new Bank("ICICI", 12);
		icici.openAccount("rishu", "rishu123", 20000, "Savings");
		ATM atm = new ATM(icici);
		atm.start();
	}

}
