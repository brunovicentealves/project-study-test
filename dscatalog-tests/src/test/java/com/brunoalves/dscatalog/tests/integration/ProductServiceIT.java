package com.brunoalves.dscatalog.tests.integration;

import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.brunoalves.dscatalog.dto.ProductDTO;
import com.brunoalves.dscatalog.exceptions.DscatalogNotfoundException;
import com.brunoalves.dscatalog.repositories.ProductRespository;
import com.brunoalves.dscatalog.service.ProductService;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductRespository repository;

	private Long existingId;
	private Long nonExixstingId;
	private Long countTotalProducts;

	@BeforeEach
	public void setUp() throws Exception {

		existingId = 1L;
		nonExixstingId = 100L;
		countTotalProducts = 25L;

	}

	@Test
	public void deleteShouldDeleteResourceWheIdExist() {

		service.deleteProduct(existingId);

		Assertions.assertEquals(countTotalProducts - 1, repository.count());

	}

	@Test
	public void deleteShouldThrowDscatalogNotfoundExceptionWheIdDoesNotExist() {

		Assertions.assertThrows(DscatalogNotfoundException.class, () -> {
			service.deleteProduct(nonExixstingId);
		});

	}

	@Test
	public void FindAllProductShouldReturnPageWhenPage0Size10() {

		PageRequest pageRequest = PageRequest.of(0, 10);

		Page<ProductDTO> pageResult = service.findAllPaged(pageRequest);

		Assertions.assertFalse(pageResult.isEmpty());
		Assertions.assertEquals(0, pageResult.getNumber());
		Assertions.assertEquals(10, pageResult.getSize());

	}

	@Test
	public void FindAllProductShouldReturnEmptyPageWhenPageDoesNotExist() {

		PageRequest pageRequest = PageRequest.of(50, 10);

		Page<ProductDTO> pageResult = service.findAllPaged(pageRequest);

		Assertions.assertTrue(pageResult.isEmpty());

	}

	@Test
	public void FindAllProductShouldReturnSortedPageWhenSorttByName() {

		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

		Page<ProductDTO> pageResult = service.findAllPaged(pageRequest);

		Assertions.assertFalse(pageResult.isEmpty());

		Assertions.assertEquals("Macbook Pro", pageResult.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", pageResult.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa", pageResult.getContent().get(2).getName());

	}

}
