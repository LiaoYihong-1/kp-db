package com.example.demo_back.controllers;

import com.example.demo_back.domain.SecurityAccount;
import com.example.demo_back.dto.HouseReceiver;
import com.example.demo_back.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HouseController {
    @Autowired
    HouseService houseService;

    @PostMapping("/house")
    @ResponseBody
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> addHouse(@RequestBody HouseReceiver houseReceiver){
        return houseService.joinHouse(houseReceiver.getHouseType().toString(),
                houseReceiver.getCity().toString(),
                houseReceiver.getStreet(),
                houseReceiver.getCountry().toString(),
                houseReceiver.getUserId());
    }
    @GetMapping("/houses")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> getHousesByUser(){
        SecurityAccount user = (SecurityAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return houseService.findHousesByUser(user.getId());
    }
    @DeleteMapping("/houses/{id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> deleteHouse(@PathVariable Integer id){
        return houseService.deleteHouse(id);
    }

}
