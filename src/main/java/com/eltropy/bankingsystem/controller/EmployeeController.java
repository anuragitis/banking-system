package com.eltropy.bankingsystem.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.eltropy.bankingsystem.apimodel.InterestRequest;
import com.eltropy.bankingsystem.apimodel.TransferMoneyRequest;
import com.eltropy.bankingsystem.entity.Account;
import com.eltropy.bankingsystem.entity.AccountTransaction;
import com.eltropy.bankingsystem.entity.Customer;
import com.eltropy.bankingsystem.service.AccountService;
import com.eltropy.bankingsystem.service.CustomerService;
import com.eltropy.bankingsystem.util.PDFExporter;
import com.lowagie.text.DocumentException;

import lombok.extern.slf4j.Slf4j;

@RequestMapping(value = "employee/v1")
@RestController
@Slf4j
public class EmployeeController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	CustomerService customerService;
    
    @RequestMapping(value = "account/create", method = RequestMethod.POST)
    public Account createEmployee(@RequestBody Account employee) throws Exception {
        return accountService.create(employee);
    }
    
    @RequestMapping(value = "account/delete", method = RequestMethod.DELETE)
    public void deleteAccount(@RequestParam Long accountId) throws Exception {
    	accountService.delete(accountId);
    }
    
    @RequestMapping(value = "customer/create", method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody Customer customer) throws Exception {
        return customerService.create(customer);
    }
    
    @RequestMapping(value = "customer/delete", method = RequestMethod.DELETE)
    public void deleteCustomer(@RequestParam Long customerId) throws Exception {
    	customerService.delete(customerId);
    }
    
    @RequestMapping(value = "customer/get", method = RequestMethod.GET)
    public Customer getCustomerDetails(@RequestParam Long customerId) throws Exception {
    	return customerService.getCustomerDetails(customerId);
    }
    
    @RequestMapping(value = "account/getBalance", method = RequestMethod.GET)
    public Account getAccountDetails(@RequestParam Long accountId) throws Exception {
    	return accountService.getAccountDetails(accountId);
    }
    
    @RequestMapping(value = "account/link/customer", method = RequestMethod.GET)
    public Account getAccountDetails(@RequestParam Long accountId,
    		@RequestParam Long customerId) throws Exception {
    	return accountService.linkCustomer(accountId, customerId);
    }
    
    @RequestMapping(value = "customer/updateKyc", method = RequestMethod.PUT)
    public Customer getCustomerDetails(@RequestBody Customer customer) throws Exception {
    	return customerService.updateKyc(customer);
    }
    
    @RequestMapping(value = "account/transferMoney", method = RequestMethod.POST)
    public List<Account> transferMoney(@RequestBody TransferMoneyRequest transferMoneyRequest) throws Exception {
    	return accountService.transferMoney(transferMoneyRequest);
    }
    
    @RequestMapping(value = "account/generateStatementPdf", method = RequestMethod.PUT)
    public void printAccountStatement(@RequestBody TransferMoneyRequest transferMoneyRequest, HttpServletResponse response) throws DocumentException, IOException {
        List<AccountTransaction> transactions = accountService.getAccountTransaction(transferMoneyRequest.getFromAccountId());
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + transactions.get(0).getAccountId() + "-transactions_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        PDFExporter exporter = new PDFExporter(transactions);
        exporter.export(response);
    }
    
    @RequestMapping(value = "account/addInterest", method = RequestMethod.POST)
    public Account addInterest(@RequestBody InterestRequest interestRequest) throws Exception {
    	return accountService.addInterest(interestRequest);
    }
    

}