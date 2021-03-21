package com.terzo.ab.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.terzo.ab.repository.AssesmentEntityListener;

import lombok.Data;

@Entity
@Data
@EntityListeners(AssesmentEntityListener.class)
public class Employee {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private long salary;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "department_id")
	private Department department;
	
	private Long managerId;
	
	private Designation designation;
	
	@Embedded
	EducationDetails educationDetails;

}
