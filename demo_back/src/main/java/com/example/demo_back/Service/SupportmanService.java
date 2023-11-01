package com.example.demo_back.service;

import com.example.demo_back.dao.problem.SupportMan;
import com.example.demo_back.dao.problem.SupportmanRepository;
import com.example.demo_back.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SupportmanService {
    @Autowired
    SupportmanRepository supportmanRepository;

    public SupportMan findFreeSupport() throws ResourceNotFoundException {
        List<SupportMan> l = supportmanRepository.findbyfree();
        if (l.size()!=0) {
            return supportmanRepository.findbyfree().get(0);
        }else{
            throw new ResourceNotFoundException("No free support man now");
        }
    }

}
