package com.example.demo_back.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class GetHousesResponse {
    private List<House> houses = new ArrayList<>();
    private boolean success = false;
    private String message = "";
    public void addHouse(House h){
        houses.add(h);
    }
}
