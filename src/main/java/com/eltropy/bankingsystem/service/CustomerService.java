package com.eltropy.bankingsystem.service;

import com.eltropy.bankingsystem.entity.Customer;

public interface CustomerService {
	
	Customer create(Customer customer);
    
    void delete(Long customerId);

	Customer getCustomerDetails(Long customerId);

	Customer updateKyc(Customer customer);
    
}
