package com.brunoalves.dscatalog.tests.service;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.brunoalves.dscatalog.dto.ProductDTO;
import com.brunoalves.dscatalog.entities.Category;
import com.brunoalves.dscatalog.entities.Product;
import com.brunoalves.dscatalog.exceptions.DataBaseException;
import com.brunoalves.dscatalog.exceptions.DscatalogNotfoundException;
import com.brunoalves.dscatalog.repositories.CategoryRepository;
import com.brunoalves.dscatalog.repositories.ProductRespository;
import com.brunoalves.dscatalog.service.ProductService;
import com.brunoalves.dscatalog.tests.CategoryMockFactory;
import com.brunoalves.dscatalog.tests.ProductDTOMockRefatory;
import com.brunoalves.dscatalog.tests.ProductMockFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRespository repository;

	@Mock
	private CategoryRepository categoryRepository;

	private long exintngId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;

	@BeforeEach
	public void setUp() {

		exintngId = 1L;
		nonExistingId = 1000L;
		dependentId = 120L;

		product = ProductMockFactory.createMockProduct();
		category = CategoryMockFactory.createCategoryMock();

		page = new PageImpl<>(List.of(product));

		Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(repository.save(Mockito.any())).thenReturn(product);

		Mockito.when(repository.findById(exintngId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		Mockito.when(repository.getOne(exintngId)).thenReturn(product);
		Mockito.doThrow(DscatalogNotfoundException.class).when(repository).getOne(nonExistingId);
		Mockito.when(categoryRepository.getOne(exintngId)).thenReturn(category);

		Mockito.doNothing().when(repository).deleteById(exintngId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}

	@Test
	public void updateProductShouldUpdateProductWhenIdExist() {

		ProductDTO product = service.updateProduct(exintngId, ProductDTOMockRefatory.productDTORequestMock());

		Assertions.assertNotNull(product);

		Mockito.verify(repository, Mockito.times(1)).getOne(exintngId);

		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());

		Mockito.verify(categoryRepository, Mockito.times(1)).getOne(exintngId);
	}

	@Test
	public void findbyIdShouldGetProductWhenIdExist() {

		ProductDTO product = service.findById(exintngId);

		Assertions.assertNotNull(product);

		Mockito.verify(repository, Mockito.times(1)).findById(exintngId);
	}

	@Test
	public void findbyIdShouldThrowDscatalogNotfoundExceptionWhenIdNotExist() {

		Assertions.assertThrows(DscatalogNotfoundException.class, () -> {
			ProductDTO product = service.findById(nonExistingId);
		});

		Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
	}

	@Test
	public void findAllPagedShouldReturnPage() {

		Pageable pageable = PageRequest.of(0, 10);

		Page<ProductDTO> result = service.findAllPaged(pageable);

		Assertions.assertNotNull(result);
		Mockito.verify(repository, Mockito.times(1)).findAll(pageable);

	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.deleteProduct(exintngId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(exintngId);

	}

	@Test
	public void deleteShouldThrowDscatalogNotfoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(DscatalogNotfoundException.class, () -> {
			service.deleteProduct(nonExistingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);

	}

	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(DataBaseException.class, () -> {
			service.deleteProduct(dependentId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);

	}

}
