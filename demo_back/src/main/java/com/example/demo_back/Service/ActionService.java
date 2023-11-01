package com.example.demo_back.service;

import com.example.demo_back.dao.action.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ActionService {
    @Autowired
    private ActionRepository repository;
    public ResponseEntity<?> getActions(){
        return ResponseEntity.ok(repository.findAll());
    }
}
