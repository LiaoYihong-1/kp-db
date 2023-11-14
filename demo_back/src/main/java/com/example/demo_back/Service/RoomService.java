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

import java.util.List;
import java.util.Optional;


@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HouseService houseService;
    @Autowired
    FunitureRepository funitureRepository;
    public ResponseEntity<?> addRoom(RoomRequest room){
        houseService.findById(room.getHouseId());
        RoomJpa r = new RoomJpa();
        r.setRoom_type(room.getRoomType());
        r.setSquare(room.getSquare());
        r.setIsFilled(room.getIsFilled());
        r.setHeight(room.getHeight());
        r.setHouse_id(room.getHouseId());
        roomRepository.save(r);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    public ResponseEntity<?> findByHose(Integer houseId) throws ResourceNotFoundException {
        houseService.findById(houseId);
        return ResponseEntity.ok(roomRepository.findRoomJpaByHouse_id(houseId));
    }
    public List<RoomJpa> findListByHose(Integer houseId) throws ResourceNotFoundException {
        houseService.findById(houseId);
        return roomRepository.findRoomJpaByHouse_id(houseId);
    }

    public RoomJpa findRoomById(Integer id) throws ResourceNotFoundException{
        if(roomRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("No such room");
        }
        return roomRepository.findById(id).get();
    }

    public ResponseEntity<?>  removeRoom(Integer id) throws ResourceNotFoundException, InvalidParameterException {
        SecurityAccount account = (SecurityAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<FurnitureJpa> funitureRepositoryList = funitureRepository.findAllByRoom(id);
        RoomJpa roomJpa = findRoomById(id);
        HouseJpa houseJpa = houseService.findById(roomJpa.getHouse_id());
        Boolean mark = false;
        for(AccountJpa accountJpa : houseJpa.getUsers()){
            if(accountJpa.getId().equals(account.getId())){
                mark = true;
                break;
            }
        }
        if(!mark){
            throw new InvalidParameterException("You are trying to delete a room, witch doesn't belong to you");
        }
        for(FurnitureJpa f: funitureRepositoryList){
            funitureRepository.delete(f);
        }
        roomRepository.delete(roomJpa);
        return ResponseEntity.noContent().build();
    }
}
