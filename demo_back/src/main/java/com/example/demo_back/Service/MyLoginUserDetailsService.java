package com.example.demo_back.service;


import com.example.demo_back.dao.account.AccountJpa;
import com.example.demo_back.dao.account.AccountRepository;
import com.example.demo_back.dao.problem.SupportMan;
import com.example.demo_back.dao.problem.SupportmanRepository;
import com.example.demo_back.domain.MyUserDetails;
import com.example.demo_back.domain.SecurityAccount;
import com.example.demo_back.domain.SecurityAccountSet;
import com.example.demo_back.utils.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.List;

@Service
public class MyLoginUserDetailsService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SupportmanRepository supportmanRepository;
    @Autowired
    SecurityAccountSet securityAccountSet;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || "".equals(username)){
            return null;
        }
        SecurityAccount securityUser;
        List<String> permissions;
        try {
            if(accountRepository.findAccountJpaByEmail(username).size() == 0){
                throw new NoSuchElementException("No such user");
            }
            AccountJpa customer = accountRepository.findAccountJpaByEmail(username).get(0);
            permissions = Arrays.asList("user");
            securityUser = new SecurityAccount(customer.getName(), customer.getPassword(),customer.getId(),"user");
        }catch (NoSuchElementException e){
            try{
                if(supportmanRepository.findbyName(username).size() == 0){
                    return null;
                }
                SupportMan supportMan = supportmanRepository.findbyName(username).get(0);
                permissions = Arrays.asList("support");
                securityUser = new SecurityAccount(supportMan.getName(), supportMan.getPassword(),supportMan.getId(),"support");
            }catch (NoSuchElementException e1){
                e1.printStackTrace();
                return null;
            }
        }
        /**
         * save to xml for later token auth
         */
        try {
            securityAccountSet.getUsers().add(securityUser);
            XmlUtils.createUsers(securityAccountSet);
        }catch (JAXBException | IOException | NullPointerException e){
            e.printStackTrace();
            throw new RuntimeException("Fail to write XML\n");
        }
        return new MyUserDetails(securityUser,permissions);
    }
}