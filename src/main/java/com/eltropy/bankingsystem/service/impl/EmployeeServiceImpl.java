package com.eltropy.bankingsystem.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eltropy.bankingsystem.entity.Employee;
import com.eltropy.bankingsystem.repository.EmployeeRepository;
import com.eltropy.bankingsystem.service.EmployeeService;
import com.eltropy.bankingsystem.error.*;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import static com.eltropy.bankingsystem.error.ErrorCode.*;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Saving the employee details failed ", e);
            throw new CustomException(EMPLOYEE_ALREADY_FOUND, "employee already exists");
        }

    }

    @Override
    public void delete(Long employeeId) {

        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        } else {
        	log.info("Employee does not exists : {} ", employeeId);
        	throw new CustomException(EMPLOYEE_NOT_FOUND, "employee not found");
        }
    }

}
