package com.eltropy.bankingsystem.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eltropy.bankingsystem.apimodel.InterestRequest;
import com.eltropy.bankingsystem.apimodel.TransferMoneyRequest;
import com.eltropy.bankingsystem.entity.Account;
import com.eltropy.bankingsystem.entity.AccountTransaction;
import com.eltropy.bankingsystem.entity.Customer;
import com.eltropy.bankingsystem.repository.AccountRepository;
import com.eltropy.bankingsystem.repository.AccountTransactionRepository;
import com.eltropy.bankingsystem.repository.CustomerRepository;
import com.eltropy.bankingsystem.service.AccountService;
import com.eltropy.bankingsystem.error.*;
import lombok.extern.slf4j.Slf4j;
import static com.eltropy.bankingsystem.error.ErrorCode.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {


    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    CustomerRepository customerRepository;
    
    @Autowired
    AccountTransactionRepository accountTransactionRepository;

    @Override
    public Account create(Account account) {
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            log.error("Saving the account details failed ", e);
            throw new CustomException(INTERNAL_SERVER_ERROR, "Invalid account");
        }

    }

    @Override
    public void delete(Long accountId) {

        if (accountRepository.existsById(accountId)) {
        	accountRepository.deleteById(accountId);
        } else {
        	log.info("Account does not exists : {} ", accountId);
        	throw new CustomException(ACCOUNT_NOT_FOUND, "account not found");
        }
    }

	@Override
	public Account getAccountDetails(Long accountId) {
		Optional<Account> account = accountRepository.findById(accountId);
		if (account.isPresent()) {
			return account.get();
		} else {
        	log.info("Account does not exists : {} ", accountId);
        	throw new CustomException(ACCOUNT_NOT_FOUND, "account not found");
		}
	}

	@Override
	public Account linkCustomer(Long accountId, Long customerId) {
		Optional<Account> account = accountRepository.findById(accountId);
		if (account.isPresent()) {
			Optional<Customer> customer = customerRepository.findById(customerId);
			if (customer.isPresent()) {
				account.get().setCustomerId(customerId);
				return accountRepository.save(account.get());
			} else {
	        	log.info("Customer does not exists : {} ", accountId);
	        	throw new CustomException(CUSTOMER_NOT_FOUND, "customer not found");
			}
		} else {
        	log.info("Account does not exists : {} ", accountId);
        	throw new CustomException(ACCOUNT_NOT_FOUND, "account not found");
		}
		

	}

	@Override
	@Transactional
	public List<Account> transferMoney(TransferMoneyRequest transferMoneyRequest) {
		Optional<Account> fromAccount = accountRepository.findById(transferMoneyRequest.getFromAccountId());
		if (fromAccount.isPresent()) {
			Optional<Account> toAccount = accountRepository.findById(transferMoneyRequest.getToAccountId());
			if (toAccount.isPresent()) {
				if(fromAccount.get().getAmount() >= transferMoneyRequest.getAmount()) {

					createTransaction(transferMoneyRequest.getFromAccountId(), AccountTransaction.Type.DEBIT,
							transferMoneyRequest.getAmount(), fromAccount.get().getAmount());

					createTransaction(transferMoneyRequest.getToAccountId(), AccountTransaction.Type.CREDIT,
							transferMoneyRequest.getAmount(), toAccount.get().getAmount());

					fromAccount.get().setAmount(fromAccount.get().getAmount() - transferMoneyRequest.getAmount());
					toAccount.get().setAmount(toAccount.get().getAmount() + transferMoneyRequest.getAmount());
					accountRepository.save(fromAccount.get());
					accountRepository.save(toAccount.get());
					List<Account> updatedAccounts = new ArrayList<Account>();
					updatedAccounts.add(fromAccount.get());
					updatedAccounts.add(toAccount.get());
					return updatedAccounts;
				} else {
		        	log.info("Insufficient balance : {} ", transferMoneyRequest.getFromAccountId());
		        	throw new CustomException(INSUFFICIENT_BALANCE, "Insufficient balance");
				}
			} else {
	        	log.info("Account does not exists : {} ", transferMoneyRequest.getToAccountId());
	        	throw new CustomException(ACCOUNT_NOT_FOUND, "account not found");
			}
		} else {
        	log.info("Account does not exists : {} ", transferMoneyRequest.getFromAccountId());
        	throw new CustomException(ACCOUNT_NOT_FOUND, "account not found");
		}
	}
	
	private void createTransaction(Long accountId, AccountTransaction.Type transactionType, Double amount, Double accountBalance) {
		AccountTransaction accountTransaction = new AccountTransaction();
		accountTransaction.setAccountId(accountId);
		accountTransaction.setOpenningBalance(accountBalance);
		if (AccountTransaction.Type.CREDIT.equals(transactionType)) {
			accountTransaction.setClosingBalance(accountBalance + amount);
		} else if (AccountTransaction.Type.DEBIT.equals(transactionType)) {
			accountTransaction.setClosingBalance(accountBalance - amount);
		}
		accountTransaction.setType(transactionType);
		accountTransaction.setAmount(amount);
		accountTransaction.setTransactionDate(new Date());
		accountTransactionRepository.save(accountTransaction);
	}

	@Override
	public List<AccountTransaction> getAccountTransaction(Long accountId) {
		return accountTransactionRepository.findAllByAccountId(accountId);
	}

	@Override
	public Account addInterest(InterestRequest interestRequest) {
		Optional<Account> account = accountRepository.findById(interestRequest.getAccountId());
		if (account.isPresent()) {

			Double interestAmount = interestCalculation(account.get().getAmount(), interestRequest.getTimeInYears());
			createTransaction(interestRequest.getAccountId(), AccountTransaction.Type.CREDIT, interestAmount, account.get().getAmount());
			account.get().setAmount(account.get().getAmount() + interestAmount);
			accountRepository.save(account.get());
		} else {
        	log.info("Account does not exists : {} ", interestRequest.getAccountId());
        	throw new CustomException(ACCOUNT_NOT_FOUND, "account not found");
		}
		return null;
	}
	
    private Double interestCalculation(Double balance, Integer years) {
            Double interestAmount = balance * (Math.pow((1 + 3.5 / 100), years)) - balance;
            
            return interestAmount;

    }

}
