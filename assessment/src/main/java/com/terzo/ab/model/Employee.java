package com.terzo.ab.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	
	//@ManyToOne()
	private Long departmentId;
	
	private Long managerId;
	
	private Designation designation;
	
	@Embedded
	EducationDetails educationDetails;

}
