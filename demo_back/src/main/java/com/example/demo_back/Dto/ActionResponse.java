package com.example.demo_back.dto;

import com.example.demo_back.dao.enums.ActionType;
import com.example.demo_back.dao.enums.FurnitureType;
import lombok.Data;

@Data
public class ActionResponse {
    private Integer action_id;
    private ActionType action_type;
    private Integer target_id;
    private FurnitureType target_type;
}
