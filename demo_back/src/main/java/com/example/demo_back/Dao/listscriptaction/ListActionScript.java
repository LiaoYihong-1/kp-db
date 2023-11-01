package com.example.demo_back.dao.listscriptaction;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "List_Action_Script")
public class ListActionScript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,name = "action_id")
    private Integer action_id;
    @Column(nullable = false,name = "script_id")
    private Integer script_id;
    @Column(nullable = false,name = "furniture_id")
    private Integer furniture_id;
}
