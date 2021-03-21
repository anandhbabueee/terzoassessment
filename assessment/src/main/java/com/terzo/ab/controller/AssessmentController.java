package com.terzo.ab.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.terzo.ab.dto.DepartmentDTO;
import com.terzo.ab.dto.EmployeeDTO;
import com.terzo.ab.service.AssesmentService;

@RestController
public class AssessmentController {
	
	@Autowired
	AssesmentService assessmentService;
	
	@PostMapping("/employees")
	public @ResponseBody EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO)
	{
		return assessmentService.createEmployee(employeeDTO);
	}
	
	@GetMapping("/employees/{id}")
	 public EmployeeDTO getEmployee(@PathVariable Long id) {
	    
	    return assessmentService.getEmployee(id);
	  }
	
	@PostMapping("/mapEmployee")
	 public EmployeeDTO mapDept(@RequestParam("empId") Long id, @RequestParam("deptId") Long deptId) {
	    
	    return assessmentService.mapDept(id, deptId);
	  }
	
	@GetMapping("/deptEmployees")
	 public List<EmployeeDTO> getEmployeebyDepartment(@RequestParam("deptId") Long id, 
			 @RequestParam("pageIndex") int pageIndex, @RequestParam("pageLimit") int pageSize) {		
	    return assessmentService.getEmployeebyDepartment(id, pageIndex, pageSize);
	  }
	
	@PutMapping("/employees/{id}")
	public EmployeeDTO updateEmployees(@Valid @RequestBody EmployeeDTO newEmployee, @PathVariable Long id) {
	    
	    return assessmentService.updateEmployees(newEmployee, id);
	  }

	@PostMapping("/departments")
	public DepartmentDTO createDept(@Valid @RequestBody DepartmentDTO dep)
	{
		return assessmentService.createDept(dep);
	}
	
	@GetMapping("/departments/{id}")
	 public DepartmentDTO getDept(@PathVariable Long id) {
	    
	    return assessmentService.getDept(id);
	  }
	
	@PostMapping("/departments/{id}")
	 public DepartmentDTO updateDeptName(@PathVariable Long id, 
			 @RequestParam("name") String deptname) {
	    
	   return assessmentService.updateDeptName(id, deptname);
	  }
	
	@GetMapping("/employeesBySpec/{id}")
	 public List<EmployeeDTO> getEmployeeBySpec(@PathVariable Long id) {
	    
	   return assessmentService.getEmployeeBySpec(id);
	  }
	
	@GetMapping("/employeesBySalary")
	 public Page<EmployeeDTO> getEmployeeBySpec(@RequestParam("salary") int salary, @RequestParam("operator") String operation,
			 @RequestParam("pageIndex") int pageIndex, @RequestParam("pageLimit") int pageSize) {
	    
		return assessmentService.getEmployeeBySpec(salary, operation, pageIndex, pageSize);
	  }
	
	@GetMapping("/employeesSalaryByDept")
	 public Page<EmployeeDTO> getEmployeeBySpec(@RequestParam("deptId") long deptId, @RequestParam("salary") int salary,
			 @RequestParam("pageIndex") int pageIndex, @RequestParam("pageLimit") int pageSize) {
	    
	    return assessmentService.getEmployeeBySpec(deptId, salary, pageIndex, pageSize);
	  }
	
	@GetMapping("/employeesByDesignation")
	 public Page<EmployeeDTO> getEmployeeByDesignation(@RequestParam("designation") String designation, 
			 @RequestParam("pageIndex") int pageIndex, @RequestParam("pageLimit") int pageSize) {
		
		return assessmentService.getEmployeeByDesignation(designation, pageIndex, pageSize);
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
	public Map<String, String> handleApplicationExceptions(
	 Exception ex) {
	    Map<String, String> errors = new HashMap<>();
	        errors.put("message", "Request Processing failed. please contact support team");
	        errors.put("erroDetail", ex.getLocalizedMessage());
	    return errors;
	}
}
