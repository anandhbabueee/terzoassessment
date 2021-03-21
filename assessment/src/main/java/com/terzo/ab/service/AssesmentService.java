package com.terzo.ab.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.terzo.ab.dto.DepartmentDTO;
import com.terzo.ab.dto.EmployeeDTO;
import com.terzo.ab.model.Department;
import com.terzo.ab.model.Designation;
import com.terzo.ab.model.Employee;
import com.terzo.ab.repository.DepartmentRepository;
import com.terzo.ab.repository.EmployeeRepository;
import com.terzo.ab.repository.EmployeeSpecification;

@Component
public class AssesmentService {

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	DepartmentRepository depRepo;

	@Transactional
	public EmployeeDTO createEmployee(EmployeeDTO dto) {
		Employee employee = convertEmployeeDTOtoEmployeeEntity(dto);
		if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
			employee = empRepo.save(employee);
		} else if (employee.getDepartment().getName() != null) {
			Department dept = depRepo.save(employee.getDepartment());
			employee.setDepartment(dept);
			employee = empRepo.save(employee);
		}

		return convertEmployeeEntityToDTO(employee);
	}

	public EmployeeDTO getEmployee(Long id) {

		return convertEmployeeEntityToDTO(empRepo.findById(id).get());
	}

	public EmployeeDTO mapDept(Long empId, Long deptId) {

		Employee employee = empRepo.findById(empId).map(emp -> {
			emp.setDepartment(depRepo.findById(deptId).get());
			return empRepo.save(emp);
		}).orElseGet(() -> {
			return null;
		});
		return convertEmployeeEntityToDTO(employee);
	}

	public List<EmployeeDTO> getEmployeebyDepartment(Long deptId, int pageIndex, int pageSize) {

		List<EmployeeDTO> dtoList = new ArrayList<EmployeeDTO>();
		Pageable page = PageRequest.of(pageIndex, pageSize);
		List<Employee> empList = empRepo.findByDepartmentId(deptId, page);
		empList.forEach(x -> dtoList.add(convertEmployeeEntityToDTO(x)));
		return dtoList;
	}

	public EmployeeDTO updateEmployees(EmployeeDTO newEmployee, Long id) {

		Employee emp = empRepo.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setSalary(newEmployee.getSalary());
			employee.setDepartment(depRepo.findById(newEmployee.getDepartment().getId()).get());
			return empRepo.save(employee);
		}).orElseGet(() -> {
			return empRepo.save(convertEmployeeDTOtoEmployeeEntity(newEmployee));
		});
		return convertEmployeeEntityToDTO(emp);
	}

	public DepartmentDTO createDept(DepartmentDTO dep) {
		return convertDepartmentEntitytoDTO(depRepo.save(convertDepartmentDTOtoDepartmentEntity(dep)));
	}

	public DepartmentDTO getDept(Long id) {

		return convertDepartmentEntitytoDTO(depRepo.findById(id).get());
	}

	public DepartmentDTO updateDeptName(Long id, String deptname) {

		Department dep = depRepo.findById(id).map(department -> {
			department.setName(deptname);
			return depRepo.save(department);
		}).orElseGet(() -> {
			Department dept = new Department();
			dept.setId(id);
			dept.setName(deptname);
			return depRepo.save(dept);
		});
		return convertDepartmentEntitytoDTO(dep);
	}

	public List<EmployeeDTO> getEmployeeBySpec(Long id) {

		List<EmployeeDTO> dtoList = new ArrayList<>();
		List<Employee> empList = empRepo.findAll(EmployeeSpecification.getEmployeeById(id));
		empList.forEach(emp -> dtoList.add(convertEmployeeEntityToDTO(emp)));

		return dtoList;
	}
	
	public Page<EmployeeDTO> getEmployeeBySpec(int salary, String operation, int pageIndex, int pageSize) {	    
		Pageable page = PageRequest.of(pageIndex, pageSize);
		Page<Employee> empPage = empRepo.findAll(EmployeeSpecification.getEmployeeBySalary(salary, operation), page);
		return empPage.map(x -> convertEmployeeEntityToDTO(x));
	  }
	
	public Page<EmployeeDTO> getEmployeeBySpec(long deptId, int salary, int pageIndex, int pageSize) {
	    
		Pageable page = PageRequest.of(pageIndex, pageSize);
		Page<Employee> empPage = empRepo.findAll(EmployeeSpecification.getEmployeeBySalary(salary, depRepo.findById(deptId).get()), page);
		return empPage.map(x -> convertEmployeeEntityToDTO(x));
	  }

	public Page<EmployeeDTO> getEmployeeByDesignation(String designation, int pageIndex, int pageSize) {
		
		Designation d1 = Designation.ASSOCIATE;
		Pageable page = PageRequest.of(pageIndex, pageSize);
		
		switch(designation)
		{
			case "ARCHITECT" : d1 = Designation.ARCHITECT;
			break;
			case "ASSOCIATE" : d1 = Designation.ASSOCIATE;
			break;
			case "MANAGER"   : d1 = Designation.MANAGER;
		}
	    
	    return empRepo.findAll(EmployeeSpecification.getEmployeeByDesignation(d1), page).map(x -> convertEmployeeEntityToDTO(x));
	  }
	

	public EmployeeDTO updateEmployeeSalary(Long id, int salary) {
		Employee emp = empRepo.findById(id).get();
		emp.setSalary(salary);
		emp = empRepo.save(emp);
		return convertEmployeeEntityToDTO(emp);
	}
	
	public EmployeeDTO viewEmployeeSalary(Long id) {
		Employee emp = empRepo.findById(id).get();
		return convertEmployeeEntityToDTO(emp);
	}
	
	public Employee convertEmployeeDTOtoEmployeeEntity(EmployeeDTO dto) {
		Employee emp = new Employee();
		emp.setId(dto.getId());
		if (dto.getDepartment() != null)
			emp.setDepartment(convertDepartmentDTOtoDepartmentEntity(dto.getDepartment()));
		emp.setName(dto.getName());
		emp.setDesignation(dto.getDesignation());
		emp.setSalary(dto.getSalary());
		emp.setEducationDetails(dto.getEducationDetails());
		emp.setManagerId(dto.getManagerId());
		return emp;
	}

	public Department convertDepartmentDTOtoDepartmentEntity(DepartmentDTO dto) {
		Department department = new Department();
		department.setId(dto.getId());
		department.setName(dto.getName());
		return department;
	}

	public EmployeeDTO convertEmployeeEntityToDTO(Employee emp) {
		EmployeeDTO empDTO = new EmployeeDTO();
		empDTO.setId(emp.getId());
		if (emp.getDepartment() != null)
			empDTO.setDepartment(convertDepartmentEntitytoDTO(emp.getDepartment()));
		empDTO.setName(emp.getName());
		empDTO.setDesignation(emp.getDesignation());
		empDTO.setSalary(emp.getSalary());
		empDTO.setEducationDetails(emp.getEducationDetails());
		empDTO.setManagerId(emp.getManagerId());
		return empDTO;
	}

	public DepartmentDTO convertDepartmentEntitytoDTO(Department dep) {
		DepartmentDTO dto = new DepartmentDTO();
		dto.setId(dep.getId());
		dto.setName(dep.getName());
		return dto;
	}
}
