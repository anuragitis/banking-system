package com.eltropy.bankingsystem.service;

import com.eltropy.bankingsystem.entity.Employee;

public interface EmployeeService {
	
    Employee create(Employee employee);
    
    void delete(Long employeeId);
    
}
