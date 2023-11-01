package com.example.demo_back.dao.house;

import com.example.demo_back.dao.enums.FurnitureType;
import com.example.demo_back.dao.enums.PostgreSQLEnumType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Entity
@Table(name = "furniture")
@TypeDef(name="pgsql_enum",typeClass = PostgreSQLEnumType.class)
public class FurnitureJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(nullable = false, name="room_id")
    private Integer roomId;

    @Column(nullable = false,name = "manufacture")
    private String manufacture;

    @Column(nullable = false,name = "available")
    private Boolean available;

    @Column(nullable = false,name="type")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private FurnitureType furnitureType;

}
