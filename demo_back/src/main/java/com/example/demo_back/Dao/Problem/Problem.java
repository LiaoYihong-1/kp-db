package com.example.demo_back.dao.problem;

import com.example.demo_back.dao.enums.PostgreSQLEnumType;
import com.example.demo_back.dao.enums.ProblemType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "Problem")
@TypeDef(name="pgsql_enum",typeClass = PostgreSQLEnumType.class)
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,name="user_id")
    private Integer userId;
    @Column(nullable = false,name="support_man_id")
    private Integer supportManId;
    @Column(nullable = false,name="type")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private ProblemType problemType;
    @Column(nullable = false,name="description")
    private String description;
    @Column(nullable = false,name="is_finished")
    private boolean isFinished;
    @Column(nullable = false,name="Date")
    private Date date;
}
