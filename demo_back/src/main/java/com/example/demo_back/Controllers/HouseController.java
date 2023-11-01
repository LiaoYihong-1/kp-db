package com.example.demo_back.controllers;

import com.example.demo_back.dto.HouseReceiver;
import com.example.demo_back.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HouseController {
    @Autowired
    HouseService houseService;

    @PostMapping("/house")
    @ResponseBody
    public ResponseEntity<?> addHouse(@RequestBody HouseReceiver houseReceiver){
        return houseService.joinHouse(houseReceiver.getHouseType().toString(),
                houseReceiver.getCity().toString(),
                houseReceiver.getStreet(),
                houseReceiver.getCountry().toString(),
                houseReceiver.getUserId());
    }
    @GetMapping("/houses/{userId}")
    public ResponseEntity<?> getHousesByUser(@PathVariable Integer userId){
        return houseService.findHousesByUser(userId);
    }
}
