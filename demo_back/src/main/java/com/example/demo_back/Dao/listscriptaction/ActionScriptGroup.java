package com.example.demo_back.dao.listscriptaction;

import com.example.demo_back.dao.enums.ActionType;
import lombok.Data;

@Data
public class ActionScriptGroup {
    private Integer scriptId;
    private ActionType actionType;
    private Integer target;
}
