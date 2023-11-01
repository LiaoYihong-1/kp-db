package com.example.demo_back.dto;

import com.example.demo_back.dao.enums.City;
import com.example.demo_back.dao.enums.Country;
import com.example.demo_back.dao.enums.HouseType;
import lombok.Data;

@Data
public class HouseReceiver {
    private City city;
    private Country country;
    private String street;
    private HouseType houseType;
    private Integer userId;
}
