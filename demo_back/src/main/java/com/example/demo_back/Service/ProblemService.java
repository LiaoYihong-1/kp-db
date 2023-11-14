package com.example.demo_back.service;

import com.example.demo_back.dao.account.AccountRepository;
import com.example.demo_back.dao.problem.Problem;
import com.example.demo_back.dao.problem.ProblemRepository;
import com.example.demo_back.domain.SecurityAccount;
import com.example.demo_back.exception.InvalidParameterException;
import com.example.demo_back.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {
    @Autowired
    SupportmanService supportmanService;
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    AccountService accountService;
    public ResponseEntity<?> report(Problem p){
        p.setFinished(false);
        java.util.Date ud = new java.util.Date();
        p.setDate(new java.sql.Date(ud.getTime()));
        Integer support = supportmanService.findFreeSupport().getId();
        p.setSupportManId(support);
        problemRepository.save(p);
        return ResponseEntity.ok("Problem reported");
    }
    public ResponseEntity<?> searchProblem(Integer id) throws ResourceNotFoundException {
        accountService.findAccountById(id);
        SecurityAccount user = (SecurityAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.getId().equals(id)){
            throw new InvalidParameterException("You are trying to access to resource, which you are not allowed to accessed");
        }
        return ResponseEntity.ok(problemRepository.findAllByUserId(id));
    }
    public Problem findById(Integer id) throws ResourceNotFoundException{
        Optional<Problem> problems = problemRepository.findById(id);
        if(problems.isEmpty()){
            throw new ResourceNotFoundException("No this problem");
        }
        return problems.get();
    }
    public void updateProblem(Problem p){
        problemRepository.save(p);
    }
}
