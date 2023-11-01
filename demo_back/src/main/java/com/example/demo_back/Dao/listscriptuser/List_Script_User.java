package com.example.demo_back.dao.listscriptuser;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "list_script_user")
public class List_Script_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,name = "user_id")
    private Integer userId;
    @Column(nullable = false,name = "script_id")
    private Integer scriptId;
}
