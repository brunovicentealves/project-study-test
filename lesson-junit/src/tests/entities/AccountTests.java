package tests.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import entities.Account;
import tests.factory.AccountFactory;

public class AccountTests {

	@Test
	public void depositShouldIncreaseBalanceWhenPositiveAmount() {

		double amount = 200.0;
		double expected = 196;
		Account acc = AccountFactory.createEmptyAccount();

		acc.desposit(amount);

		Assertions.assertEquals(expected, acc.getBalance());

	}

	@Test
	public void depositShouldDonothingWhenNegativeAmount() {

		double amount = -200.0;
		double expected = 100;
		Account acc = AccountFactory.createAccount(expected);

		acc.desposit(amount);

		Assertions.assertEquals(expected, acc.getBalance());

	}

	@Test
	public void fullWithdrawShouldClearBalance() {
		double expectedValue = 0.0;
		double initialBalance = 800.0;
		Account acc = AccountFactory.createAccount(initialBalance);

		double result = acc.fullWithdraw();

		Assertions.assertTrue(expectedValue == acc.getBalance());
		Assertions.assertTrue(result == initialBalance);
	}

	@Test
	public void withdrawShouldDecreaseBalanceWhenSufficientBalance() {

		double initialBalance = 800.0;
		Account acc = AccountFactory.createAccount(initialBalance);

		acc.withdraw(500.00);

		Assertions.assertEquals(300.00, acc.getBalance());

	}

	@Test
	public void withdrawShouldThrowExceptionWhenInsufficientBalance() {

		double initialBalance = 300.0;
		Account acc = AccountFactory.createAccount(initialBalance);

		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			acc.withdraw(500.00);
		});

		Assertions.assertTrue(exception.getMessage().contains("Saldo insuficiente"));

	}

}
