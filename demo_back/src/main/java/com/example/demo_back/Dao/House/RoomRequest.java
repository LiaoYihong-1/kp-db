package com.example.demo_back.dao.house;

import com.example.demo_back.dao.enums.City;
import com.example.demo_back.dao.enums.Country;
import com.example.demo_back.dao.enums.HouseType;
import com.example.demo_back.dao.enums.RoomType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomRequest {
    private Integer houseId;
    private Double square;
    private Double height;
    private Boolean isFilled;
    private RoomType roomType;
}
