package com.devsuperior.bds01.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.devsuperior.bds01.dto.DepartmentDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository repository;

	public List<DepartmentDTO> getAllDepartments() {

		List<Department> listDepartment = repository.findAll(Sort.by("name"));

		List<DepartmentDTO> dto = listDepartment.stream().map(x -> new DepartmentDTO(x.getName())).collect(Collectors.toList());

		return dto;
	}

}
