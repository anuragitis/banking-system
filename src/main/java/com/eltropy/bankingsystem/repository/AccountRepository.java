package com.eltropy.bankingsystem.repository;

import com.eltropy.bankingsystem.entity.Account;
import com.eltropy.bankingsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
