package com.example.demo_back.dao.script;

import com.example.demo_back.dao.enums.ScriptType;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
public class ScriptRequest {
    String type;
    String condition;
    Integer userId;
    String time;
}
