package com.example.demo_back.dao.problem;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "support_man")
public class SupportMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,name="name")
    private String name;

    @Column(nullable = false,name="password")
    private String password;

    @Column(nullable = false,name="is_free")
    private Boolean free;
}
