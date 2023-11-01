package com.example.demo_back.dao.script;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;

@Data
@Entity
@Table(name = "schedule_script")
public class ScheduleScript {
    @Id
    private Integer id;
    @Column(nullable = false,name="time")
    private Time time;
}
