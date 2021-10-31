package com.brunoalves.dscatalog.resource;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.brunoalves.dscatalog.dto.CategoryDTO;
import com.brunoalves.dscatalog.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable) {

		return ResponseEntity.ok(categoryService.findAllPaged(pageable));

	}

	@GetMapping("/{id}")

	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {

		return ResponseEntity.ok(categoryService.findById(id));
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO dto) {
		dto = categoryService.saveCategory(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);

	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto) {
		dto = categoryService.updateCategory(id, dto);

		return ResponseEntity.ok(dto);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();

	}

}
