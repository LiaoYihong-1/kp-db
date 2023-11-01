package com.example.demo_back.controllers;

import com.example.demo_back.dao.house.FurnitureJpa;
import com.example.demo_back.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FurnitureController {
    @Autowired
    FurnitureService service;
    @PostMapping("/homes/rooms/furniture/{roomId}")
    public ResponseEntity<?> addFurniture(@PathVariable Integer roomId, @RequestBody FurnitureJpa furniture){
        return service.addFurniture(roomId,furniture);
    }

    @GetMapping("/homes/rooms/furniture/{roomId}")
    public ResponseEntity<?> getFurniture(@PathVariable Integer roomId){
        return service.getFurnitureByRoom(roomId);
    }
}
