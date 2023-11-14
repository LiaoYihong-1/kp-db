package com.example.demo_back.controllers;

import com.example.demo_back.dao.enums.RoomType;
import com.example.demo_back.dao.house.RoomJpa;
import com.example.demo_back.dao.house.RoomRequest;
import com.example.demo_back.dto.UniResponse;
import com.example.demo_back.service.HouseService;
import com.example.demo_back.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {
    @Autowired
    RoomService service;
    @Autowired
    HouseService houseService;
    @PostMapping("/houses/room")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> addRoom(@RequestBody RoomRequest room){
        return service.addRoom(room);
    }
    @GetMapping("/houses/room/{houseId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> getRoomByHouse(@PathVariable Integer houseId){
        return service.findByHose(houseId);
    }
    @DeleteMapping("/houses/room/{id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> deleteRoom(@PathVariable Integer id){
        return service.removeRoom(id);
    }

}
