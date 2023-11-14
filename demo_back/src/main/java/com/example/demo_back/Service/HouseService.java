package com.example.demo_back.service;

import com.example.demo_back.dao.account.AccountJpa;
import com.example.demo_back.dao.house.*;
import com.example.demo_back.domain.SecurityAccount;
import com.example.demo_back.dto.UniResponse;
import com.example.demo_back.dao.enums.HouseType;
import com.example.demo_back.dao.listuserhouse.ListUserHouseJpa;
import com.example.demo_back.dao.listuserhouse.ListUserHouseRepository;
import com.example.demo_back.exception.InvalidParameterException;
import com.example.demo_back.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class HouseService {
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    ListUserHouseRepository listUserHouseRepository;
    @Autowired
    AddressService addressService;
    @Autowired
    AccountService accountService;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    FunitureRepository funitureRepository;
    public boolean checkString(String s){
        if ((s == null) | (Objects.equals(s, ""))){
            return false;
        }
        return true;
    }
    public ResponseEntity<?> joinHouse(String houseType, String city, String street, String country, Integer userId){
        UniResponse uniResponse = new UniResponse();
        if(checkString(houseType)&&checkString(city)&&checkString(street)&&checkString(country)){
            addressService.addAddress(country,city,street);
            Integer id = addressService.findNewestId();
            addHouse(houseType,id,userId);
            uniResponse.setMessage("Success");
            uniResponse.setSuccess(true);
        }else {
            throw new InvalidParameterException("You sent an invalid value to server!");
        }
        if(!uniResponse.isSuccess()){
            return ResponseEntity.badRequest().body(uniResponse);
        }
        return ResponseEntity.ok(uniResponse);
    }
    public void addHouse(String houseType, Integer addressId, Integer userId) throws ResourceNotFoundException {
        accountService.findAccountById(userId);
        HouseJpa h = new HouseJpa();
        h.setHouseType(HouseType.valueOf(houseType.toUpperCase()));
        h.setAddressId(addressId);
        houseRepository.save(h);
        ListUserHouseJpa listUserHouseJpa = new ListUserHouseJpa();
        listUserHouseJpa.setHouse_id(houseRepository.findNewestid().get(0));
        listUserHouseJpa.setUser_id(userId);
        listUserHouseRepository.save(listUserHouseJpa);
    }

    public ResponseEntity<?> findHousesByUser(Integer user){
        AccountJpa accountJpa = accountService.findAccountById(user);
        SecurityAccount account = (SecurityAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!account.getId().equals(user)){
            throw new InvalidParameterException("You are trying to access to resource, which you are not allowed to accessed");
        }
        return ResponseEntity.ok(accountJpa.getHouses());
    }

    public HouseJpa findById(Integer houseId) throws ResourceNotFoundException{
        if(houseRepository.findById(houseId).isEmpty()){
            throw new ResourceNotFoundException("No this house");
        }
        return houseRepository.findById(houseId).get();
    }

    public ResponseEntity<?> deleteHouse(Integer id) throws ResourceNotFoundException{
        SecurityAccount account = (SecurityAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(houseRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("No such house");
        }
        HouseJpa houseJpa = houseRepository.findById(id).get();
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
        List<RoomJpa> roomJpaList = roomRepository.findRoomJpaByHouse_id(houseJpa.getId());
        for(RoomJpa r: roomJpaList){
            List<FurnitureJpa> furnitureJpas = funitureRepository.findAllByRoom(r.getId());
            for(FurnitureJpa f: furnitureJpas){
                funitureRepository.delete(f);
            }
            roomRepository.delete(r);
        }
        listUserHouseRepository.delete(listUserHouseRepository.findByUserAndHouse(houseJpa.getId(),account.getId()));
        return ResponseEntity.noContent().build();
    }

}
