package com.brunoalves.dscatalog.tests;

import java.time.Instant;
import com.brunoalves.dscatalog.entities.Category;

public class CategoryMockFactory {

	public static Category createCategoryMock() {

		Category category = new Category(1L, "ELETRONIC", Instant.parse("2020-10-20T03:00:00Z"), null);

		return category;
	}

}
