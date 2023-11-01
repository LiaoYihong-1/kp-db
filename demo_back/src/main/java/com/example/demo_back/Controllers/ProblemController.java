package com.example.demo_back.controllers;

import com.example.demo_back.dao.problem.Problem;
import com.example.demo_back.dao.problem.ProblemRepository;
import com.example.demo_back.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProblemController {
    @Autowired
    private ProblemService service;
    @PostMapping("/report")
    public ResponseEntity<?> report(@RequestBody Problem problem){
        return service.report(problem);
    }

    @Autowired
    ProblemRepository repository;
    @GetMapping("/problems/{id}")
    @ResponseBody
    public ResponseEntity<?> getProblem(@PathVariable Integer id) {
        return service.searchProblem(id);
    }
}
