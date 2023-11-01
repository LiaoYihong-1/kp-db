package com.example.demo_back.dao.contact;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Contact")
public class ContactJpa {
    @Id
    @Column(nullable = false,name="user_id")
    private Integer userId;
    @Column(nullable = false,name="email")
    private String email;
    @Column(nullable = false,name="phone")
    private String phone;
}
