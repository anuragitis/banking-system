package com.eltropy.bankingsystem.repository;

import com.eltropy.bankingsystem.entity.AccountTransaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
	List<AccountTransaction> findAllByAccountId(Long accountId);
}
