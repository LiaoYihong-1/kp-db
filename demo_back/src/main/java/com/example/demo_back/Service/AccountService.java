package com.example.demo_back.service;

import com.example.demo_back.dto.LoginResponse;
import com.example.demo_back.dto.UniResponse;
import com.example.demo_back.dao.account.AccountJpa;
import com.example.demo_back.dao.account.AccountRepository;
import com.example.demo_back.dao.enums.Gender;
import com.example.demo_back.exception.InvalidParameterException;
import com.example.demo_back.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ContactService contactService;
    public ResponseEntity<?> login(String username, String password) throws InvalidParameterException{
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
            AccountJpa accountJpa = findAccountByEmail(username);
            if (accountJpa == null){
                throw new InvalidParameterException("Password or account not valid");
            }
            String a = accountJpa.getPassword();
            if (a==null){
                throw new InvalidParameterException("No such phone");
            }else {
                if (a.equals(password)) {
                    uniResponse.setSuccess(true);
                    uniResponse.setMessage("Success");
                    uniResponse.setId(accountJpa.getId());
                } else {
                    throw new InvalidParameterException("Phone or password wrong");
                }
            }
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
        UniResponse uniResponse = new UniResponse();
        if(checkString(name)&&checkString(phone)&&checkString(email)&&checkString(gender)&&checkString(password)) {
            if (findAccountByEmail(email)!=null || findAccountByPhone(phone)!=null){
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
    public List<String> findPasswordByEmail(String email){
        return accountRepository.findPasswordJpaByEmail(email);
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
