package com.tekmaturix.enterpriseknowledgeassistantapplication.service;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekmaturix.enterpriseknowledgeassistantapplication.model.Employee;
import com.tekmaturix.enterpriseknowledgeassistantapplication.repository.EmployeeRepository;

import jakarta.annotation.PostConstruct;

@Service
public class DatabaseEmployeeService {
	
	private final EmployeeRepository employeeRepository;
	private final ResourceLoader resourceLoader;
	private final ObjectMapper objectMapper;
	
	public DatabaseEmployeeService(EmployeeRepository employeeRepository, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
		super();
		this.employeeRepository = employeeRepository;
		this.resourceLoader = resourceLoader;
		this.objectMapper = objectMapper;
	}
	
	
	@PostConstruct
	public void init() throws StreamReadException, DatabindException, IOException {
		Resource resource = resourceLoader.getResource("classpath:employees.json");

        List<Employee> employeeList = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<Employee>>() {}
        );
		for(Employee emp: employeeList) {
			employeeRepository.save(emp);
		}
	}
	
	@Tool(
			name="getEmployeeDetails",
			description="This function give access to internal database to finds out the employee information"
			)
	public Employee getEmployee(String name) {
		return employeeRepository.findByName(name);
	}
	
	
	@Tool(
			name="getAllEmployeesDetails",
			description = "This function give access to internal database to finds out the employee information"
			)
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

}
