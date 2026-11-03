package com.tekmaturix.enterpriseknowledgeassistantapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tekmaturix.enterpriseknowledgeassistantapplication.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	public Employee findByName(String name);

}
