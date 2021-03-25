package com.eltropy.bankingsystem.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eltropy.bankingsystem.entity.Customer;
import com.eltropy.bankingsystem.repository.CustomerRepository;
import com.eltropy.bankingsystem.service.CustomerService;
import com.eltropy.bankingsystem.error.*;
import lombok.extern.slf4j.Slf4j;
import static com.eltropy.bankingsystem.error.ErrorCode.*;

import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            log.error("Saving the employee details failed ", e);
            throw new CustomException(INTERNAL_SERVER_ERROR, "Invalid employee");
        }

    }

    @Override
    public void delete(Long customerId) {

        if (customerRepository.existsById(customerId)) {
        	customerRepository.deleteById(customerId);
        } else {
        	log.info("customer does not exists : {} ", customerId);
        	throw new CustomException(EMPLOYEE_NOT_FOUND, "customer not found");
        }
    }

	@Override
	public Customer getCustomerDetails(Long customerId) throws CustomException{
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (customer.isPresent()) {
			return customer.get();
		} else {
        	log.info("customer does not exists : {} ", customerId);
        	throw new CustomException(CUSTOMER_NOT_FOUND, "customer not found");
		}
	}

	@Override
	public Customer updateKyc(Customer customerUpdate) {
		Optional<Customer> customer = customerRepository.findById(customerUpdate.getId());
		if (customer.isPresent()) {
			customer.get().setKyc(customerUpdate.getKyc());
			return customerRepository.save(customer.get());
		} else {
        	log.info("customer does not exists : {} ", customerUpdate.getId());
        	throw new CustomException(CUSTOMER_NOT_FOUND, "customer not found");
		}
	}

}
