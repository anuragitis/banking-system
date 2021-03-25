package com.eltropy.bankingsystem.repository;

import com.eltropy.bankingsystem.entity.Customer;
import com.eltropy.bankingsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
