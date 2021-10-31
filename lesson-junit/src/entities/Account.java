package entities;

public class Account {

	public static double DEPOSIT_FEE_PERCEMTAGE = 0.02;
	private Long id;
	private Double balance;

	public Account() {
		super();
	}

	public Account(Long id, Double balance) {
		super();
		this.id = id;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getBalance() {
		return balance;
	}

	public void desposit(double amount) {
		if (amount > 0) {
			amount -= amount * DEPOSIT_FEE_PERCEMTAGE;
			balance += amount;
		}
	}

	public void withdraw(double amount) {
		if (amount > balance) {
			throw new IllegalArgumentException("Saldo insuficiente");
		}
		balance -= amount;
	}

	public double fullWithdraw() {
		double aux = balance;
		balance = (double) 0;

		return aux;
	}

}
