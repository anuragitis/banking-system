package com.eltropy.bankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping(value = "admin/v1")
@RestController
public class AdminController {

    @GetMapping(value = "/test")
    public void getValue () {
    	System.out.println("Hello World");
    }
}