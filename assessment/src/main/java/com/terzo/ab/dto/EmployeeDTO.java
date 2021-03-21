package com.terzo.ab.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.terzo.ab.model.Designation;
import com.terzo.ab.model.EducationDetails;

import lombok.Data;

@Data
public class EmployeeDTO {
	
	private Long id;
	
	@NotBlank(message="Name is mandatory")
	private String name;
	
	@Positive(message="Salary must be greater than 0")
	private long salary;
	
	@NotNull
	private DepartmentDTO department;
	
	private Long managerId;
	
	@NotNull(message="Designation is mandatory and cannot be null")
	private Designation designation;
	
	
	EducationDetails educationDetails;

}
