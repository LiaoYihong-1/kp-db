package com.example.demo_back.service;

import com.example.demo_back.domain.MyUserDetails;
import com.example.demo_back.domain.SecurityAccount;
import com.example.demo_back.dto.LoginResponse;
import com.example.demo_back.dto.UniResponse;
import com.example.demo_back.dao.account.AccountJpa;
import com.example.demo_back.dao.account.AccountRepository;
import com.example.demo_back.dao.enums.Gender;
import com.example.demo_back.exception.InvalidParameterException;
import com.example.demo_back.exception.ResourceNotFoundException;
import com.example.demo_back.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class AccountService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ContactService contactService;
    public ResponseEntity<?> login(String username, String password) throws InvalidParameterException, ResourceNotFoundException{
        LoginResponse uniResponse = new LoginResponse();
        if (username == null || password == null || "".equals(password) || "".equals(username)) {
            uniResponse.setSuccess(false);
            throw new InvalidParameterException("Password or account not valid");
        }
        String regexPhone = "\\+[1-9]+[0-9]*";
        String regexEmail = ".*@.+\\.com";
        if (Pattern.matches(regexPhone,username)) {
            uniResponse.setSuccess(false);
            uniResponse.setMessage("No such phone");
        }else if (Pattern.matches(regexEmail,username)){
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
            Authentication authentication = authenticationManager.authenticate(token);
            if(Objects.isNull(authentication)){
                throw new ResourceNotFoundException("Failed login");
            }
            MyUserDetails details = (MyUserDetails) authentication.getPrincipal();
            SecurityAccount securityUser = details.getUser();
            /**
             * token is made up of id + type
             */
            String userId = securityUser.getId().toString() + securityUser.getType();
            /**
             * create token
             */
            String jwtToken = JwtUtils.createToken(userId);
            Map<String,String> tokenMap = new HashMap<>();
            tokenMap.put("token",jwtToken);
            return ResponseEntity.ok(tokenMap);
        }else{
            throw new InvalidParameterException("Make sure that you input a phone or email");
        }
        return ResponseEntity.ok(uniResponse);
    }
    public boolean checkString(String s){
        if ((s == null) | (Objects.equals(s, ""))){
            return false;
        }
        return true;
    }
    public ResponseEntity<?> signup(String name,String password,String phone,String email,String gender,Integer age) throws InvalidParameterException{
        password = new BCryptPasswordEncoder().encode(password);
        UniResponse uniResponse = new UniResponse();
        if(checkString(name)&&checkString(phone)&&checkString(email)&&checkString(gender)&&checkString(password)) {
            if (accountRepository.findAccountJpaByEmail(email).size()!=0 || accountRepository.findAccountJpaByPhone(phone).size()!=0){
                throw new InvalidParameterException("Phone or email existed");
            }
            addAccount(password,name,gender,age);
            uniResponse.setMessage("Success");
            Integer id = findNewestId();
            contactService.addContact(id,phone,email);
            uniResponse.setSuccess(true);
        }else {
            throw new InvalidParameterException("You sent an invalid value");
        }
        return ResponseEntity.ok(uniResponse);
    }

    public ResponseEntity<?> getUserById(Integer id){
        return ResponseEntity.ok(findAccountById(id));
    }

    public void addAccount(String password,String name, String gender,Integer age){
        AccountJpa accountJpa = new AccountJpa();
        accountJpa.setAge(age);
        accountJpa.setGender(Gender.valueOf(gender.toUpperCase()));
        accountJpa.setName(name);
        accountJpa.setPassword(password);
        accountRepository.save(accountJpa);
    }
    public Integer findNewestId(){
        return accountRepository.findNewestAccount().get(0);
    }
    public AccountJpa findAccountByEmail(String email) throws ResourceNotFoundException{
        if (accountRepository.findAccountJpaByEmail(email).size()==0){
            throw new ResourceNotFoundException("No such user");
        }
        return accountRepository.findAccountJpaByEmail(email).get(0);
    }
    public AccountJpa findAccountByPhone(String phone) throws ResourceNotFoundException{
        if (accountRepository.findAccountJpaByPhone(phone).size()==0){
            throw new ResourceNotFoundException("No such user");
        }
        return accountRepository.findAccountJpaByPhone(phone).get(0);
    }
    public AccountJpa findAccountById(Integer id) throws ResourceNotFoundException {
        if(accountRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("No such user");
        }
        return accountRepository.findById(id).get();
    }


}
