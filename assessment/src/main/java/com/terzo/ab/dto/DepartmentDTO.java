package com.terzo.ab.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DepartmentDTO {
	
	private Long id;
	
	@NotBlank(message = "Department Name is mandatory")
	private String name;	

}
