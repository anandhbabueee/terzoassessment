package com.terzo.ab.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.terzo.ab.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, 
JpaSpecificationExecutor<Employee>{

List<Employee> findByDepartmentId(Long deptId, Pageable page);
	
}
