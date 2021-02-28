package com.terzo.ab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.terzo.ab.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>, 
JpaSpecificationExecutor<Department>{

}
