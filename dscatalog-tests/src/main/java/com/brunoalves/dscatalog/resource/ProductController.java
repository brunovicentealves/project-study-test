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
import com.brunoalves.dscatalog.dto.ProductDTO;
import com.brunoalves.dscatalog.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService ProductService;

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {

		return ResponseEntity.ok(ProductService.findAllPaged(pageable));

	}

	@GetMapping("/{id}")

	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {

		return ResponseEntity.ok(ProductService.findById(id));
	}

	@PostMapping
	public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO dto) {
		dto = ProductService.saveProduct(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
		dto = ProductService.updateProduct(id, dto);

		return ResponseEntity.ok(dto);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
		ProductService.deleteProduct(id);
		return ResponseEntity.noContent().build();

	}

}
