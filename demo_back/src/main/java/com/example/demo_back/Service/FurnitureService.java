package com.example.demo_back.service;

import com.example.demo_back.dao.account.AccountJpa;
import com.example.demo_back.dao.house.*;
import com.example.demo_back.domain.SecurityAccount;
import com.example.demo_back.exception.InvalidParameterException;
import com.example.demo_back.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FurnitureService {
    @Autowired
    RoomService roomService;
    @Autowired
    FunitureRepository repository;
    @Autowired
    HouseService houseService;
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

    public ResponseEntity<?> removeFurnitureById(Integer id) throws ResourceNotFoundException{
        Optional<FurnitureJpa> furnitureJpaOption = repository.findById(id);
        if(furnitureJpaOption.isEmpty()){
            throw new ResourceNotFoundException("No this furniture");
        }
        SecurityAccount account = (SecurityAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RoomJpa roomJpa = roomService.findRoomById(furnitureJpaOption.get().getRoomId());
        HouseJpa houseJpa = houseService.findById(roomJpa.getHouse_id());
        Boolean mark = false;
        for(AccountJpa accountJpa : houseJpa.getUsers()){
            if(accountJpa.getId().equals(account.getId())){
                mark = true;
                break;
            }
        }
        if(!mark){
            throw new InvalidParameterException("You are trying to delete a furniture? witch doesn't belong to you");
        }
        repository.delete(furnitureJpaOption.get());
        return ResponseEntity.noContent().build();
    }
}
