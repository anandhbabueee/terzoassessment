package com.terzo.ab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terzo.ab.model.Employee;
import com.terzo.ab.repository.EmployeeRepository;

@RestController
@RequestMapping("hr/")
public class HROperations {

	@Autowired
	EmployeeRepository empRepo;

	@PostMapping("updateSalary")
	public Employee updateEmployeeSalary(@RequestParam("employeeid") Long id, @RequestParam("salary") int salary) {
		Employee emp = empRepo.findById(id).get();
		emp.setSalary(salary);
		return empRepo.save(emp);
	}

	@PostMapping("viewSalaryByEmployee")
	public Employee viewEmployeeSalary(@RequestParam("employeeid") Long id) {
		Employee emp = empRepo.findById(id).get();
		return empRepo.save(emp);
	}

}
