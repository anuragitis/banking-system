package com.eltropy.bankingsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.eltropy.bankingsystem.entity.Account;
import com.eltropy.bankingsystem.entity.Employee;
import com.eltropy.bankingsystem.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping(value = "admin/v1")
@RestController
@Slf4j
public class AdminController {
	
	@Autowired
	EmployeeService employeeService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "employee/create", method = RequestMethod.POST)
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) throws Exception {
        try {
        	Employee employeeUpdate = employeeService.create(employee);
            return new ResponseEntity<>(employeeUpdate, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("exception.getMessage() : {}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "employee/delete", method = RequestMethod.DELETE)
    public void deleteEmployee(@RequestParam Long employeeId) throws Exception {
    	employeeService.delete(employeeId);
    }
}