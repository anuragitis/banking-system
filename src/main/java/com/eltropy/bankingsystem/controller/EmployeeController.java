package com.eltropy.bankingsystem.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.eltropy.bankingsystem.apimodel.InterestRequest;
import com.eltropy.bankingsystem.apimodel.TransferMoneyRequest;
import com.eltropy.bankingsystem.entity.Account;
import com.eltropy.bankingsystem.entity.AccountTransaction;
import com.eltropy.bankingsystem.entity.Customer;
import com.eltropy.bankingsystem.error.CustomException;
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
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "account/create", method = RequestMethod.POST)
    public ResponseEntity<Object> createEmployee(@RequestBody Account employee) throws CustomException {
        try {
        	Account account = accountService.create(employee);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("exception.getMessage() : {}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "account/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAccount(@RequestParam Long accountId) throws CustomException {
        try {
        	accountService.delete(accountId);
            return new ResponseEntity<>("Account is deleted : ", HttpStatus.OK);
        } catch (Exception exception) {
            log.error("exception.getMessage() : {}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "customer/create", method = RequestMethod.POST)
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) throws CustomException {
        try {
        	Customer customerNew = customerService.create(customer);
            return new ResponseEntity<>(customerNew, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("exception.getMessage() : {}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "customer/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCustomer(@RequestParam Long customerId) throws CustomException {
        try {
        	customerService.delete(customerId);
            return new ResponseEntity<>("Customer is deleted : ", HttpStatus.OK);
        } catch (Exception exception) {
            log.error("exception.getMessage() : {}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "customer/get", method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomerDetails(@RequestParam Long customerId) throws CustomException {
        try {
            Customer customer = customerService.getCustomerDetails(customerId);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Error in getting the customer details : {}, {}", customerId, exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "account/getBalance", method = RequestMethod.GET)
    public ResponseEntity<Object> getAccountDetails(@RequestParam Long accountId) throws CustomException {
        try {
        	Account account = accountService.getAccountDetails(accountId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Error in getting the account balance : {}, {}", accountId, exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "account/link/customer", method = RequestMethod.GET)
    public ResponseEntity<Object> getAccountDetails(@RequestParam Long accountId,
    		@RequestParam Long customerId) throws CustomException {
        try {
        	Account account = accountService.linkCustomer(accountId, customerId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Error in linking account : {}, {}", accountId, exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "customer/updateKyc", method = RequestMethod.PUT)
    public ResponseEntity<Object> getCustomerDetails(@RequestBody Customer customer) throws CustomException {
        try {
        	Customer customerUpdated =  customerService.updateKyc(customer);
            return new ResponseEntity<>(customerUpdated, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Error in updating kyc : {}, {}", customer.getId(), exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "account/transferMoney", method = RequestMethod.POST)
    public ResponseEntity<Object> transferMoney(@RequestBody TransferMoneyRequest transferMoneyRequest) throws CustomException {
        try {
        	List<Account> accounts =  accountService.transferMoney(transferMoneyRequest);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Error in transferMoney : {}, {}", transferMoneyRequest, exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
    
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "account/addInterest", method = RequestMethod.POST)
    public ResponseEntity<Object> addInterest(@RequestBody InterestRequest interestRequest) throws CustomException {
        try {
        	Account account =  accountService.addInterest(interestRequest);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Error in addInterest : {}, {}", interestRequest, exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    

}