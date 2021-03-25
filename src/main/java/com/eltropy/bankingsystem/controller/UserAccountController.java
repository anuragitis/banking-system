package com.eltropy.bankingsystem.controller;

import com.eltropy.bankingsystem.repository.UserAccountRepository;
import com.eltropy.bankingsystem.apimodel.LoginRequest;
import com.eltropy.bankingsystem.apimodel.UserAccountModel;
import com.eltropy.bankingsystem.apimodel.LoginResponse;
import com.eltropy.bankingsystem.service.UserAccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserAccountController {

    private static final Logger LOG = LoggerFactory.getLogger(UserAccountController.class);


    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserAccountRepository userAccountRepository;


    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserAccountModel userAccountModel) {
        try {

            userAccountService.register(userAccountModel);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception ex) {
            LOG.error("Error in registering User :  {}", userAccountModel.getUsername());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        LOG.info("sign-in for user : {}", loginRequest.getUsername());
        LoginResponse loginResponse = userAccountService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

}
