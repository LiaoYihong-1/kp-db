package com.example.demo_back.dao.script;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConditionalScriptRepository extends JpaRepository<ConditionalScript,Integer> {
    @Query(value="select C from ConditionalScript C where C.id =?1 ")
    List<ConditionalScript> findbyscriptid(Integer id);
}
