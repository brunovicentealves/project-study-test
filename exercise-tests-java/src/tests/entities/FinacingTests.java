package tests.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import entities.Financing;

public class FinacingTests {

	@Test
	public void constructShouldCreateObjectWhenValidData() {

		Financing financing = new Financing(100000.0, 2000.0, 80);

		Assertions.assertEquals(100000.0, financing.getTotalAmount());
		Assertions.assertEquals(2000.0, financing.getIncome());
		Assertions.assertEquals(80, financing.getMonths());

	}

	@Test
	public void constructShouldThrowIlegalArgumentExceptionWhenInvalidData() {

		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Financing financing = new Financing(100000.0, 2000.0, 20);
		});

		Assertions.assertTrue(exception.getMessage().contains("A parcela não pode ser maior que a metade da renda"));

	}

	@Test
	public void setTotalAmountShouldSetDataWhenValidData() {

		Financing financing = new Financing(100000.0, 2000.0, 80);

		financing.setTotalAmount(90000.0);

		Assertions.assertEquals(90000.0, financing.getTotalAmount());
		Assertions.assertEquals(2000.0, financing.getIncome());
		Assertions.assertEquals(80, financing.getMonths());

	}

	@Test
	public void setTotalAmountShouldThrowIlegalArgumentExceptionWhenInvalidData() {

		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {

			Financing financing = new Financing(100000.0, 2000.0, 20);
			financing.setTotalAmount(900000.0);
		});

		Assertions.assertTrue(exception.getMessage().contains("A parcela não pode ser maior que a metade da renda"));

	}

	@Test
	public void setIncomeShouldSetDataWhenValidData() {

		Financing financing = new Financing(100000.0, 2000.0, 80);

		financing.setIncome(3000.00);

		Assertions.assertEquals(100000.0, financing.getTotalAmount());
		Assertions.assertEquals(3000.0, financing.getIncome());
		Assertions.assertEquals(80, financing.getMonths());

	}

	@Test
	public void setIncomeShouldThrowIlegalArgumentExceptionWhenInvalidData() {

		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {

			Financing financing = new Financing(100000.0, 2000.0, 80);

			financing.setIncome(1000.00);
		});

		Assertions.assertTrue(exception.getMessage().contains("A parcela não pode ser maior que a metade da renda"));

	}

	@Test
	public void setMonthsShouldSetDataWhenValidData() {

		Financing financing = new Financing(100000.0, 3000.0, 80);

		financing.setMonths(90);

		Assertions.assertEquals(100000.0, financing.getTotalAmount());
		Assertions.assertEquals(3000.0, financing.getIncome());
		Assertions.assertEquals(90, financing.getMonths());

	}

	@Test
	public void setMonthsShouldThrowIlegalArgumentExceptionWhenInvalidData() {

		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {

			Financing financing = new Financing(100000.0, 3000.0, 80);

			financing.setMonths(30);
		});

		Assertions.assertTrue(exception.getMessage().contains("A parcela não pode ser maior que a metade da renda"));

	}

	@Test
	public void entryShouldSetDataWhenValidData() {

		Financing financing = new Financing(10000.0, 3000.0, 80);

		Assertions.assertEquals(2000.0, financing.entry());

	}

	@Test
	public void quotaShouldSetDataWhenValidData() {

		Financing financing = new Financing(10000.0, 3000.0, 80);

		Assertions.assertEquals(100.0, financing.quota());

	}

}
