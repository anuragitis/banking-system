package com.eltropy.bankingsystem.service;

import java.util.List;

import com.eltropy.bankingsystem.apimodel.InterestRequest;
import com.eltropy.bankingsystem.apimodel.TransferMoneyRequest;
import com.eltropy.bankingsystem.entity.Account;
import com.eltropy.bankingsystem.entity.AccountTransaction;

public interface AccountService {
	
	Account create(Account Account);
    
    void delete(Long accountId);

	Account getAccountDetails(Long accountId);

	Account linkCustomer(Long accountId, Long customerId);

	List<Account> transferMoney(TransferMoneyRequest transferMoneyRequest);

	List<AccountTransaction> getAccountTransaction(Long accountId);

	Account addInterest(InterestRequest interestRequest);
    
}
