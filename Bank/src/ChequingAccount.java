
public class ChequingAccount extends Account{ 
	private double balance;

	public ChequingAccount(double balance, double transactionAmount) {
		super(transactionAmount);
		this.balance = balance;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}	
	
	public String toString() {
		return "Chequing account balance: " + balance;
	}
	
	public void deposit(double transactionAmount) {
		balance += transactionAmount;
	}

	public void withdraw(double transactionAmount) {
		balance -= transactionAmount;
	}
	
}
