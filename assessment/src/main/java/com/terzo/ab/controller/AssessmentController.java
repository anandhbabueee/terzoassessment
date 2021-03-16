package com.terzo.ab.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.terzo.ab.model.Department;
import com.terzo.ab.model.Designation;
import com.terzo.ab.model.Employee;
import com.terzo.ab.repository.DepartmentRepository;
import com.terzo.ab.repository.EmployeeRepository;
import com.terzo.ab.repository.EmployeeSpecification;

@RestController
public class AssessmentController {
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private DepartmentRepository depRepo;
	
	@PostMapping("/employees")
	@Transactional
	public @ResponseBody Employee createEmployee(@Valid @RequestBody Employee employee)
	{
		if (employee.getDepartment() != null && 
				employee.getDepartment().getId() != null)
		{
			return empRepo.save(employee);
		}
		else if (employee.getDepartment().getName() != null)
		{
			Department dept = depRepo.save(employee.getDepartment());
			employee.setDepartment(dept);
			return empRepo.save(employee);
		}
		return null;
	}
	
	@GetMapping("/employees/{id}")
	 public Employee getEmployee(@PathVariable Long id) {
	    
	    return empRepo.findById(id).get();
	  }
	
	@PostMapping("/mapEmployee")
	 public Employee mapDept(@RequestParam("empId") Long id, @RequestParam("deptId") Long deptId) {
	    
	    return empRepo.findById(id)
	    		.map(emp -> {
	    			emp.setDepartment(depRepo.findById(deptId).get());
	    			return empRepo.save(emp);
	    		}).orElseGet(() -> {
	    			return null;
	    		});
	  }
	
	@GetMapping("/deptEmployees")
	 public List<Employee> getEmployeebyDepartment(@RequestParam("deptId") Long id, 
			 @RequestParam("pageIndex") int pageIndex, @RequestParam("pageLimit") int pageSize) {
		
		Pageable page = PageRequest.of(pageIndex, pageSize);
	    return empRepo.findByDepartmentId(id,  page);
	  }
	
	@PostMapping("/employees/{id}")
	public Employee updateEmployees(@RequestBody Employee newEmployee, @PathVariable Long id) {
	    
	    return empRepo.findById(id)
	      .map(employee -> {
	        employee.setName(newEmployee.getName());
	        employee.setSalary(newEmployee.getSalary());
	        employee.setDepartment(depRepo.findById(newEmployee.getDepartment().getId()).get());
	        return empRepo.save(employee);
	      })
	      .orElseGet(() -> {
	        newEmployee.setId(id);
	        return empRepo.save(newEmployee);
	      });
	  }

	@PostMapping("/departments")
	public Department createDept(@Valid @RequestBody Department dep)
	{
		return depRepo.save(dep);
	}
	
	@GetMapping("/departments/{id}")
	 public Department getDept(@PathVariable Long id) {
	    
	    return depRepo.findById(id).get();
	  }
	
	@PostMapping("/departments/{id}")
	 public Department updateDeptName(@PathVariable Long id, 
			 @RequestParam("name") String deptname) {
	    
	   return depRepo.findById(id)
	    		.map(department -> {
	    			department.setName(deptname);
	    			return depRepo.save(department);
	    		}).orElseGet(() -> {
	    			Department dept = new Department();
	    			dept.setId(id);
	    			dept.setName(deptname);
	    			return depRepo.save(dept);
	    		});
	  }
	
	@GetMapping("/employeesBySpec/{id}")
	 public List<Employee> getEmployeeBySpec(@PathVariable Long id) {
	    
	    return empRepo.findAll(EmployeeSpecification.getEmployeeById(id));
	  }
	
	@GetMapping("/employeesBySalary")
	 public Page<Employee> getEmployeeBySpec(@RequestParam("salary") int salary, @RequestParam("operator") String operation,
			 @RequestParam("pageIndex") int pageIndex, @RequestParam("pageLimit") int pageSize) {
	    
		Pageable page = PageRequest.of(pageIndex, pageSize);
	    return empRepo.findAll(EmployeeSpecification.getEmployeeBySalary(salary, operation), page);
	  }
	
	@GetMapping("/employeesSalaryByDept")
	 public Page<Employee> getEmployeeBySpec(@RequestParam("deptId") long deptId, @RequestParam("salary") int salary,
			 @RequestParam("pageIndex") int pageIndex, @RequestParam("pageLimit") int pageSize) {
	    
		Pageable page = PageRequest.of(pageIndex, pageSize);
	    return empRepo.findAll(EmployeeSpecification.getEmployeeBySalary(salary, depRepo.findById(deptId).get()), page);
	  }
	
	@GetMapping("/employeesByDesignation")
	 public Page<Employee> getEmployeeByDesignation(@RequestParam("designation") String designation, 
			 @RequestParam("pageIndex") int pageIndex, @RequestParam("pageLimit") int pageSize) {
		
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
	    
	    return empRepo.findAll(EmployeeSpecification.getEmployeeByDesignation(d1), page);
	  }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Map<String, String> handleValidationExceptions(
	 Exception ex) {
	    Map<String, String> errors = new HashMap<>();
	        errors.put("message", "Request Processing failed. please contact support team");
	        errors.put("erroDetail", ex.getLocalizedMessage());
	    return errors;
	}
}
