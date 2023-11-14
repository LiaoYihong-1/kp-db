package com.example.demo_back.controllers;
import com.example.demo_back.dto.SignupReceiver;
import com.example.demo_back.dto.SupportReceiver;
import com.example.demo_back.service.AccountService;
import com.example.demo_back.service.SupportmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    SupportmanService supportmanService;

    @GetMapping("/user/login")
    public ResponseEntity<?> login(@RequestParam("username") String username,@RequestParam("password") String password) {
        return accountService.login(username,password);
    }
    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReceiver receiver) {
        return accountService.signup(receiver.getUsername(),receiver.getPassword(),receiver.getPhone(), receiver.getEmail(),receiver.getGender().toString(),receiver.getAge());
    }
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> getUser(@PathVariable Integer id){
        return accountService.getUserById(id);
    }
    @GetMapping("/support/login")
    public ResponseEntity<?> supportLogin(@RequestParam("username") String username,@RequestParam("password") String password) {
        return supportmanService.login(username,password);
    }
    @PostMapping("/support/signup")
    public ResponseEntity<?> supportSignup(@RequestBody @Valid SupportReceiver supportReceiver) {
        return supportmanService.signup(supportReceiver.getUsername(),supportReceiver.getPassword());
    }
}
