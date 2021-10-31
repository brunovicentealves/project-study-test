package com.brunoalves.dscatalog.tests;

import java.time.Instant;
import com.brunoalves.dscatalog.dto.ProductDTO;
import com.brunoalves.dscatalog.entities.Category;
import com.brunoalves.dscatalog.entities.Product;

public class ProductMockFactory {

	public static Product createMockProduct() {

		Product product = new Product(1L, "Phone", "Good Phone", 800.00, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(1L, "Eletronics", Instant.parse("2020-10-20T03:00:00Z"), null));
		return product;
	}

	public static ProductDTO createProductDTOMock() {
		Product product = createMockProduct();

		return new ProductDTO(product, product.getCategories());

	}

}
