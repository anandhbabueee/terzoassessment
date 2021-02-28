package com.terzo.ab.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class EducationDetails {
	
	private String graduationDegree;
	
	private String university;

}
