package com.terzo.ab.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Department {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@OneToMany
	@JoinColumn(name="employee_id")
	private Set<Employee> employee;
	
}
