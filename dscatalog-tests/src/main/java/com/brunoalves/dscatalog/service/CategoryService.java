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
import com.brunoalves.dscatalog.entities.Category;
import com.brunoalves.dscatalog.exceptions.DataBaseException;
import com.brunoalves.dscatalog.exceptions.DscatalogNotfoundException;
import com.brunoalves.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {

		List<Category> category = categoryRepository.findAll();

		List<CategoryDTO> listDto = category.stream().map(objeto -> new CategoryDTO(objeto)).collect(Collectors.toList());

		return listDto;
	}

	public Page<CategoryDTO> findAllPaged(Pageable pageable) {

		Page<Category> category = categoryRepository.findAll(pageable);

		return category.map(x -> new CategoryDTO(x));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {

		Optional<Category> category = categoryRepository.findById(id);

		if (!category.isPresent()) {

			throw new DscatalogNotfoundException("Não encontrou categoria");
		}

		return new CategoryDTO(category.get());
	}

	@Transactional
	public CategoryDTO saveCategory(CategoryDTO dto) {

		Category category = new Category();
		category.setName(dto.getName());

		category = categoryRepository.save(category);

		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
		try {

			Category category = categoryRepository.getOne(id);
			category.setName(dto.getName());

			category = categoryRepository.save(category);

			return new CategoryDTO(category);

		} catch (EntityNotFoundException e) {

			throw new DscatalogNotfoundException("Não conseguiu salvar a Categoria");

		}

	}

	public void deleteCategory(Long id) {
		try {
			categoryRepository.deleteById(id);

			// pega a exceção de quando não achar o id do produto no banco de dados
		} catch (EmptyResultDataAccessException e) {

			throw new DscatalogNotfoundException("Não conseguiu deletar ");

			// pega casos de vioção de integridade (exemplo um produto não pode ficar sem uma categoria )
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("violação de integridade");
		}

	}

}
