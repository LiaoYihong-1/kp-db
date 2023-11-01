package com.example.demo_back.controllers;
import com.example.demo_back.dto.SignupReceiver;
import com.example.demo_back.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username,@RequestParam("password") String password) {
        return accountService.login(username,password);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReceiver receiver) {
        return accountService.signup(receiver.getUsername(),receiver.getPassword(),receiver.getPhone(), receiver.getEmail(),receiver.getGender().toString(),receiver.getAge());
    }
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> getUser(@PathVariable Integer id){
        return accountService.getUserById(id);
    }
}
