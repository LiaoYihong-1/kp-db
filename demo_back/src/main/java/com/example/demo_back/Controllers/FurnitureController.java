package com.example.demo_back.controllers;

import com.example.demo_back.dao.house.FurnitureJpa;
import com.example.demo_back.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class FurnitureController {
    @Autowired
    FurnitureService service;
    @PostMapping("/homes/rooms/furniture/{roomId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> addFurniture(@PathVariable Integer roomId, @RequestBody FurnitureJpa furniture){
        return service.addFurniture(roomId,furniture);
    }

    @GetMapping("/homes/rooms/furniture/{roomId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> getFurniture(@PathVariable Integer roomId){
        return service.getFurnitureByRoom(roomId);
    }
    @DeleteMapping("/homes/rooms/furniture/{id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> removeFurniture(@PathVariable Integer id){
        return service.removeFurnitureById(id);
    }
}
