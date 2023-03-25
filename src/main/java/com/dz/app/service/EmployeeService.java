package com.dz.app.service;

import java.util.List;

import com.dz.app.model.entity.Employee;

public interface EmployeeService {


	Employee saveEmployee(Employee employee);
	Employee updateEmployee(Employee empTrn);
	void deleteEmployee(Employee empTrn);
	Employee findById(long eid);
	List<Employee> getAllEmployees();
	void deleteAll();
}
