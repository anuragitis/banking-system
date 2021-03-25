package com.eltropy.bankingsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.eltropy.bankingsystem.entity.Employee;
import com.eltropy.bankingsystem.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping(value = "admin/v1")
@RestController
@Slf4j
public class AdminController {
	
	@Autowired
	EmployeeService employeeService;
    
//    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "employee/create", method = RequestMethod.POST)
    public Employee createEmployee(@RequestBody Employee employee) throws Exception {
        return employeeService.create(employee);
    }
    
//    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "employee/delete", method = RequestMethod.DELETE)
    public void deleteEmployee(@RequestParam Long employeeId) throws Exception {
    	employeeService.delete(employeeId);
    }
    
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() throws Exception {
    	return "abc";
    }
}