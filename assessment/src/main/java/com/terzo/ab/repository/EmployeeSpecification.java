package com.terzo.ab.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.terzo.ab.model.Designation;
import com.terzo.ab.model.Employee;

public class EmployeeSpecification {

	public static Specification<Employee> getEmployeeById(Long id) {
		return new Specification<Employee>() {
			private static final long serialVersionUID = 1L;

			@Override
			public javax.persistence.criteria.Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("id"), id);
			}

		};

	}

	public static Specification<Employee> getEmployeeBySalary(long salary) {
		return new Specification<Employee>() {
			private static final long serialVersionUID = 2L;

			@Override
			public javax.persistence.criteria.Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				query.orderBy(criteriaBuilder.asc(root.get("name")));
				return criteriaBuilder.greaterThan(root.get("salary"), salary);
			}

		};
	}
	
	public static Specification<Employee> getEmployeeBySalary(long salary, long departmentId) {
		return new Specification<Employee>() {
			private static final long serialVersionUID = 2L;

			@Override
			public javax.persistence.criteria.Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				query.orderBy(criteriaBuilder.desc(root.get("salary")));
				return criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("salary"), salary), 
						criteriaBuilder.equal(root.get("departmentId"), departmentId));
			}

		};
	}
	
	public static Specification<Employee> getEmployeeByDesignation(Designation design) {
		return new Specification<Employee>() {
			private static final long serialVersionUID = 3L;

			@Override
			public javax.persistence.criteria.Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("designation"), design);
			}

		};
	}

}