package com.devsuperior.bds01.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devsuperior.bds01.dto.DepartmentDTO;
import com.devsuperior.bds01.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartamentController {

	@Autowired
	private DepartmentService service;

	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
		return ResponseEntity.ok(service.getAllDepartments());
	}

}
