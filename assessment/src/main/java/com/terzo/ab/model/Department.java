package com.terzo.ab.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class Department {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank(message = "Department Name is mandatory")
	private String name;
	
	@OneToMany
	@JoinColumn(name="employee_id")
	private Set<Employee> employee;
	
}
