package com.brunoalves.dscatalog.tests;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import com.brunoalves.dscatalog.dto.CategoryDTO;
import com.brunoalves.dscatalog.dto.ProductDTO;

public class ProductDTOMockRefatory {

	public static ProductDTO productDTORequestMock() {
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();

		list.add(new CategoryDTO(1L, "Eletronics"));
		ProductDTO product = new ProductDTO(1L, "Computador", "HP ideapad", 2500.00, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"),
						list);

		return product;
	}

}
