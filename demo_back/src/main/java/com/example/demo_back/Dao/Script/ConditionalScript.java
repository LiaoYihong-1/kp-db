package com.example.demo_back.dao.script;

import com.example.demo_back.dao.enums.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Condition_script")
@TypeDef(name="pgsql_enum",typeClass = PostgreSQLEnumType.class)
public class ConditionalScript {
    @Id
    private Integer id;
    @Column(nullable = false,name="condition")
    private String condition;
}
