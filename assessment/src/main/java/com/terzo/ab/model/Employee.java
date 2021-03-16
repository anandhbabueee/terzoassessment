package com.terzo.ab.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.terzo.ab.repository.AssesmentEntityListener;

import lombok.Data;

@Entity
@Data
@EntityListeners(AssesmentEntityListener.class)
public class Employee {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank(message="Name is mandatory")
	private String name;
	
	@Positive(message="Salary must be greater than 0")
	private long salary;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "department_id")
	@NotNull
	private Department department;
	
	private Long managerId;
	
	@NotNull(message="Designation is mandatory and cannot be null")
	private Designation designation;
	
	@Embedded
	EducationDetails educationDetails;

}
