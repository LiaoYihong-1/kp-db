package com.example.demo_back.service;

import com.example.demo_back.dao.house.RoomJpa;
import com.example.demo_back.dao.house.RoomRepository;
import com.example.demo_back.dao.house.RoomRequest;
import com.example.demo_back.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HouseService houseService;
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

    public RoomJpa findRoomById(Integer id) throws ResourceNotFoundException{
        if(roomRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("No such room");
        }
        return roomRepository.findById(id).get();
    }
}
