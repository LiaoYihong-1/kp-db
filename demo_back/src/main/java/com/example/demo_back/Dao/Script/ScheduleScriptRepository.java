package com.example.demo_back.dao.script;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleScriptRepository extends JpaRepository<ScheduleScript,Integer> {
    @Query(value="select S from ScheduleScript S where S.id =?1 ")
    List<ScheduleScript> findbyscriptid(Integer id);
}
