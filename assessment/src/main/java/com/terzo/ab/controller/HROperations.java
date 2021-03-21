package com.terzo.ab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terzo.ab.dto.EmployeeDTO;
import com.terzo.ab.service.AssesmentService;

@RestController
@RequestMapping("hr/")
public class HROperations {

	@Autowired
	AssesmentService assesmentService;

	@PostMapping("updateSalary")
	public EmployeeDTO updateEmployeeSalary(@RequestParam("employeeid") Long id, @RequestParam("salary") int salary) {
		return assesmentService.updateEmployeeSalary(id, salary);
	}

	@GetMapping("viewSalaryByEmployee")
	public EmployeeDTO viewEmployeeSalary(@RequestParam("employeeid") Long id) {
		return assesmentService.viewEmployeeSalary(id);
	}

}
