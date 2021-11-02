package com.devsuperior.bds01.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repository.DepartmentRepository;
import com.devsuperior.bds01.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private DepartmentRepository departmentRepository;

	public Page<EmployeeDTO> findAll(Pageable pageable) {

		Page<Employee> pageFindAll = repository.findAll(pageable);

		return pageFindAll.map(x -> new EmployeeDTO(x));
	}

	public EmployeeDTO save(EmployeeDTO employeeDTO) {
		Employee employee = copyEntity(employeeDTO);
		employee = repository.save(employee);
		return new EmployeeDTO(employee);
	}

	public Employee copyEntity(EmployeeDTO dto) {

		Optional<Department> department = departmentRepository.findById(dto.getDepartmentId());

		Employee employee = new Employee(dto.getId(), dto.getName(), dto.getEmail(), department.get());

		return employee;

	}

}
