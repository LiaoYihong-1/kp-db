package com.example.demo_back.service;

import com.example.demo_back.dao.problem.Problem;
import com.example.demo_back.dao.problem.ProblemRepository;
import com.example.demo_back.dao.problem.SupportMan;
import com.example.demo_back.dao.problem.SupportmanRepository;
import com.example.demo_back.domain.MyUserDetails;
import com.example.demo_back.domain.SecurityAccount;
import com.example.demo_back.dto.LoginResponse;
import com.example.demo_back.dto.UniResponse;
import com.example.demo_back.exception.InvalidParameterException;
import com.example.demo_back.exception.ResourceNotFoundException;
import com.example.demo_back.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class SupportmanService {
    @Autowired
    SupportmanRepository supportmanRepository;
    @Autowired
    ProblemRepository problemRepository;
    public SupportMan findFreeSupport() throws ResourceNotFoundException {
        List<SupportMan> l = supportmanRepository.findbyfree();
        if (l.size()!=0) {
            return supportmanRepository.findbyfree().get(0);
        }else{
            throw new ResourceNotFoundException("No free support man now");
        }
    }
    @Autowired
    private AuthenticationManager authenticationManager;
    public ResponseEntity<?> login(String username, String password){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(token);
        if(Objects.isNull(authentication)){
            return ResponseEntity.badRequest().body("Fail login");
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
    }

    public ResponseEntity<?> signup(String name,String password) throws InvalidParameterException{
        password = new BCryptPasswordEncoder().encode(password);
        UniResponse uniResponse = new UniResponse();
        if (supportmanRepository.findbyName(name).size()!=0){
            throw new InvalidParameterException("Account existed");
        }
        SupportMan supportMan = new SupportMan();
        supportMan.setFree(true);
        supportMan.setName(name);
        supportMan.setPassword(password);
        supportmanRepository.save(supportMan);
        uniResponse.setMessage("Success");
        uniResponse.setSuccess(true);
        return ResponseEntity.ok(uniResponse);
    }
    public ResponseEntity<?> changeStatus(Integer id, Boolean status) throws ResourceNotFoundException{
        SecurityAccount account = (SecurityAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(problemRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("No this problem");
        }
        Problem problem = problemRepository.findById(id).get();
        if(!account.getId().equals(problem.getSupportManId())) {
            throw new InvalidParameterException("You are trying to change status of a problem, which is not dealing by you");
        }
        problem.setFinished(status);
        problemRepository.save(problem);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
