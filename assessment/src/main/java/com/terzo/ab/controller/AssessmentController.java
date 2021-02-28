package com.terzo.ab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public @ResponseBody Employee createEmployee(@RequestBody Employee emp)
	{
		return empRepo.save(emp);
	}
	
	@GetMapping("/employees/{id}")
	 public Employee getEmployee(@PathVariable Long id) {
	    
	    return empRepo.findById(id).get();
	  }
	
	@PostMapping("/mapEmployee")
	 public Employee mapDept(@RequestParam("empId") Long id, @RequestParam("deptId") Long deptId) {
	    
	    return empRepo.findById(id)
	    		.map(emp -> {
	    			emp.setDepartmentId(deptId);
	    			return empRepo.save(emp);
	    		}).orElseGet(() -> {
	    			return null;
	    		});
	  }
	
	@GetMapping("/deptEmployees")
	 public List<Employee> getEmployeebyDepartment(@RequestParam("deptId") Long id) {
	    
	    return empRepo.findByDepartmentId(id);
	  }
	
	@PostMapping("/employees/{id}")
	public Employee updateEmployees(@RequestBody Employee newEmployee, @PathVariable Long id) {
	    
	    return empRepo.findById(id)
	      .map(employee -> {
	        employee.setName(newEmployee.getName());
	        employee.setSalary(newEmployee.getSalary());
	        employee.setDepartmentId(newEmployee.getDepartmentId());
	        return empRepo.save(employee);
	      })
	      .orElseGet(() -> {
	        newEmployee.setId(id);
	        return empRepo.save(newEmployee);
	      });
	  }

	@PostMapping("/departments")
	public Department createDept(@RequestBody Department dep)
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
	 public List<Employee> getEmployeeBySpec() {
	    
	    return empRepo.findAll(EmployeeSpecification.getEmployeeBySalary(10000));
	  }
	
	@GetMapping("/employeesSalaryByDept")
	 public List<Employee> getEmployeeBySpec(@RequestParam("deptId") long deptId) {
	    
	    return empRepo.findAll(EmployeeSpecification.getEmployeeBySalary(10000, deptId));
	  }
	
	@GetMapping("/employeesByDesignation")
	 public List<Employee> getEmployeeByDesignation(@RequestParam("designation") String designation) {
		
		Designation d1 = Designation.ASSOCIATE;
		
		switch(designation)
		{
			case "ARCHITECT" : d1 = Designation.ARCHITECT;
			break;
			case "ASSOCIATE" : d1 = Designation.ASSOCIATE;
			break;
			case "MANAGER"   : d1 = Designation.MANAGER;
		}
	    
	    return empRepo.findAll(EmployeeSpecification.getEmployeeByDesignation(d1));
	  }

}
