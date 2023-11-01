package com.example.demo_back.dao.script;

import com.example.demo_back.dao.enums.PostgreSQLEnumType;
import com.example.demo_back.dao.enums.ScriptType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Entity
@Table(name = "script")
@TypeDef(name="pgsql_enum",typeClass = PostgreSQLEnumType.class)
public class Script {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,name="type")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private ScriptType scriptType;

    @Column(nullable = false,name="creator_name")
    private String creatorName;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "id",referencedColumnName = "script_id")
    @JsonIgnore
    private ConditionalScript conditionalScript;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "id",referencedColumnName = "script_id")
    @JsonIgnore
    private ScheduleScript scheduleScript;

}
