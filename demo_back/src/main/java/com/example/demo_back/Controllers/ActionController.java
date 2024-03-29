package com.example.demo_back.controllers;

import com.example.demo_back.dao.listscriptaction.ActionScriptGroup;
import com.example.demo_back.service.ActionService;
import com.example.demo_back.service.ListActionScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActionController {
    @Autowired
    ListActionScriptService listActionScriptService;
    @Autowired
    ActionService actionService;
    @PostMapping("/action")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> addAction(@RequestBody ActionScriptGroup actionScriptGroup){
        return listActionScriptService.addAction(actionScriptGroup.getScriptId(),actionScriptGroup.getTarget(),actionScriptGroup.getActionType());
    }
    @GetMapping("/actions")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> getActions(){
        return actionService.getActions();
    }
}
