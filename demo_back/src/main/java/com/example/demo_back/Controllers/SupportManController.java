package com.example.demo_back.controllers;

import com.example.demo_back.service.SupportmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupportManController {
    @Autowired
    SupportmanService supportmanService;
    @PutMapping("/support/problem/{id}/{status}")
    public ResponseEntity<?> response(@PathVariable("id") Integer id,@PathVariable Boolean status){
        return supportmanService.changeStatus(id,status);

    }
}
