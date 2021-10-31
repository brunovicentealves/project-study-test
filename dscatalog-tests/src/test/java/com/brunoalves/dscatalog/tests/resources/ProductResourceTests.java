package com.brunoalves.dscatalog.tests.resources;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.brunoalves.dscatalog.dto.ProductDTO;
import com.brunoalves.dscatalog.exceptions.DataBaseException;
import com.brunoalves.dscatalog.exceptions.DscatalogNotfoundException;
import com.brunoalves.dscatalog.resource.ProductController;
import com.brunoalves.dscatalog.service.ProductService;
import com.brunoalves.dscatalog.tests.ProductDTOMockRefatory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductResourceTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;

	@Autowired
	private ObjectMapper objectMapper;

	private PageImpl<ProductDTO> page;
	private ProductDTO productDto;
	private long existingId;
	private long nonExistingId;
	private long dependentId;

	@BeforeEach
	public void setUp() throws Exception {

		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;

		productDto = ProductDTOMockRefatory.productDTORequestMock();
		page = new PageImpl<>(List.of());

		when(service.findAllPaged(Mockito.any())).thenReturn(page);
		when(service.saveProduct(Mockito.any())).thenReturn(productDto);
		when(service.updateProduct(Mockito.eq(existingId), Mockito.any())).thenReturn(productDto);
		when(service.updateProduct(Mockito.eq(nonExistingId), Mockito.any())).thenThrow(DscatalogNotfoundException.class);

		when(service.findById(existingId)).thenReturn(productDto);
		when(service.findById(nonExistingId)).thenThrow(DscatalogNotfoundException.class);

		doNothing().when(service).deleteProduct(existingId);
		doThrow(DscatalogNotfoundException.class).when(service).deleteProduct(nonExistingId);
		doThrow(DataBaseException.class).when(service).deleteProduct(dependentId);

	}

	@Test
	public void deleteProductShouldDeleteProductWhenIdExist() throws Exception {
		ResultActions result = mockMvc.perform(delete("/products/{id}", existingId).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNoContent());
	}

	@Test
	public void deleteProductShouldNotFoundWhenIdDoesNotExist() throws Exception {

		ResultActions result = mockMvc.perform(delete("/products/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());

	}

	@Test
	public void deleteProductShouldBadRequestWhenIdDoesNotExist() throws Exception {

		ResultActions result = mockMvc.perform(delete("/products/{id}", dependentId).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());

	}

	@Test
	public void SaveShouldSaveProduct() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(productDto);

		ResultActions result = mockMvc
						.perform(post("/products").content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());

	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(productDto);

		ResultActions result = mockMvc.perform(put("/products/{id}", existingId).content(jsonBody).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());

	}

	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(productDto);

		ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId).content(jsonBody).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());

	}

	@Test
	public void findAllShouldReturnPage1() throws Exception {
		mockMvc.perform(get("/products")).andExpect(status().isOk());
	}

	@Test
	public void findAllShouldReturnPage2() throws Exception {

		ResultActions result = mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	public void findByIdShouldReturnProductWhenIdExists() throws Exception {

		ResultActions result = mockMvc.perform(get("/products/{id}", existingId).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());

	}

	@Test
	public void findByIdShouldReturnNotFoundWhenIdExists() throws Exception {

		ResultActions result = mockMvc.perform(get("/products/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());

	}

}
