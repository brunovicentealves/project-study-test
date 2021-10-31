package com.brunoalves.dscatalog.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.brunoalves.dscatalog.dto.CategoryDTO;
import com.brunoalves.dscatalog.dto.ProductDTO;
import com.brunoalves.dscatalog.entities.Category;
import com.brunoalves.dscatalog.entities.Product;
import com.brunoalves.dscatalog.exceptions.DataBaseException;
import com.brunoalves.dscatalog.exceptions.DscatalogNotfoundException;
import com.brunoalves.dscatalog.repositories.CategoryRepository;
import com.brunoalves.dscatalog.repositories.ProductRespository;

@Service
public class ProductService {

	@Autowired
	private ProductRespository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {

		List<Product> product = productRepository.findAll();

		List<ProductDTO> listDto = product.stream().map(objeto -> new ProductDTO(objeto)).collect(Collectors.toList());

		return listDto;
	}

	public Page<ProductDTO> findAllPaged(Pageable pageable) {

		Page<Product> product = productRepository.findAll(pageable);

		return product.map(x -> new ProductDTO(x));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {

		Optional<Product> product = productRepository.findById(id);

		if (!product.isPresent()) {

			throw new DscatalogNotfoundException("Não encontrou categoria");
		}

		return new ProductDTO(product.get(), product.get().getCategories());
	}

	@Transactional
	public ProductDTO saveProduct(ProductDTO dto) {

		Product entity = new Product();
		copyDtoToEntity(dto, entity);

		entity = productRepository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO updateProduct(Long id, ProductDTO dto) {
		try {

			Product entity = productRepository.getOne(id);

			copyDtoToEntity(dto, entity);

			entity = productRepository.save(entity);

			return new ProductDTO(entity);

		} catch (EntityNotFoundException e) {

			throw new DscatalogNotfoundException("Não conseguiu salvar a Categoria");

		}

	}

	public void deleteProduct(Long id) {
		try {
			productRepository.deleteById(id);

			// pega a exceção de quando não achar o id do produto no banco de dados
		} catch (EmptyResultDataAccessException e) {

			throw new DscatalogNotfoundException("Não conseguiu deletar ");

			// pega casos de vioção de integridade (exemplo um produto não pode ficar sem uma categoria )
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("violação de integridade");
		}

	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.setDate(dto.getDate());
		entity.getCategories().clear();
		for (CategoryDTO catDTO : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDTO.getId());
			entity.getCategories().add(category);
		}

	}

}
