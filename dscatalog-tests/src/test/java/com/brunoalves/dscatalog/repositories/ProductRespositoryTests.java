package com.brunoalves.dscatalog.repositories;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import com.brunoalves.dscatalog.entities.Product;
import com.brunoalves.dscatalog.tests.ProductMockFactory;

@DataJpaTest
public class ProductRespositoryTests {

	@Autowired
	private ProductRespository repository;

	private long exintngId;
	private long nonExintngId;
	private long countTotalProducts;

	@BeforeEach
	public void setUp() {
		exintngId = 1L;
		nonExintngId = 111L;
		countTotalProducts = 25L;
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		repository.deleteById(exintngId);

		Optional<Product> result = repository.findById(exintngId);

		Assertions.assertFalse(result.isPresent());

	}

	@Test
	public void saveShouldPersistWhitAutoincrementWhenIsNull() {

		Product product = ProductMockFactory.createMockProduct();

		product.setId(null);

		product = repository.save(product);

		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());

	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdExists() {

		Exception exception = Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExintngId);
		});
	}

	@Test
	public void findbyIdShouldGetObjectWhenIdExist() {

		Optional<Product> product = repository.findById(exintngId);

		Assertions.assertNotNull(product);
		Assertions.assertEquals(exintngId, product.get().getId());

	}

	@Test
	public void findByIdShouldThrowNotFoundExceptionWhenIdNotExists() {

		Optional<Product> product = repository.findById(nonExintngId);

		Assertions.assertEquals(Optional.empty(), product);
		Assertions.assertTrue(product.isEmpty());

	}

}
