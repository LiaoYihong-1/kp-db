package com.example.demo_back.dao.action;

import com.example.demo_back.dao.enums.ActionType;
import com.example.demo_back.dao.enums.FurnitureType;
import com.example.demo_back.dao.enums.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Action")
@TypeDef(name="pgsql_enum",typeClass = PostgreSQLEnumType.class)
public class ActionJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,name="type")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private ActionType action;

    @Column(nullable = false,name="type_furniture")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private FurnitureType furniture;

}
