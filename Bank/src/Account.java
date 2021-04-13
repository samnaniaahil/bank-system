
abstract class Account {
	private double transactionAmount;
	
	public Account(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
		
	public double getTransactionAmount() {
		return transactionAmount;
	}
		
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public String toString() {
		return "Transaction amount: " + transactionAmount;
	}
	
	public abstract void deposit(double transactionAmount);
	
	public abstract void withdraw(double transactionAmount);
		
}

