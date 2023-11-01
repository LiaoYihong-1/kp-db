package com.example.demo_back.controllers;

import com.example.demo_back.dao.script.*;
import com.example.demo_back.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.sql.Time;

@RestController
public class ScriptController {
    @Autowired
    ScriptService service;
    @PostMapping("/scripts")
    public ResponseEntity<?> addScript(@RequestBody ScriptRequest script){
        return service.addScript(script);
    }

    @GetMapping("/scripts/conditional/{id}")
    public ResponseEntity<?> getConditionalScript(@PathVariable @Min(0) Integer id){
        return service.getConditional(id);
    }

    @GetMapping("/scripts/time/{id}")
    public ResponseEntity<?> getScheduleScript(@PathVariable @Min(0) Integer id){
        return service.getSchedule(id);
    }

    @GetMapping("/scripts/{id}")
    public ResponseEntity<?> getScripts(@PathVariable @Min(0) Integer id){
        return service.getScripts(id);
    }


    @PutMapping("/scripts/{script}/{from}/{to}")
    public ResponseEntity<?> share(@PathVariable @Min(0) Integer script,@PathVariable @Min(0) Integer from,@PathVariable @Min(0) Integer to){
        return service.share(script,from,to);
    }
}
