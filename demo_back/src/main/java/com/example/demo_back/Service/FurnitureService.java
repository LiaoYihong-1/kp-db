package com.example.demo_back.service;

import com.example.demo_back.dao.house.FunitureRepository;
import com.example.demo_back.dao.house.FurnitureJpa;
import com.example.demo_back.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FurnitureService {
    @Autowired
    RoomService roomService;
    @Autowired
    FunitureRepository repository;
    public ResponseEntity<?> addFurniture(Integer roomId, FurnitureJpa furniture) throws ResourceNotFoundException {
        roomService.findRoomById(roomId);
        furniture.setRoomId(roomId);
        repository.save(furniture);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<?> getFurnitureByRoom(Integer id){
        roomService.findRoomById(id);
        return ResponseEntity.ok(repository.findAllByRoom(id));
    }
}
